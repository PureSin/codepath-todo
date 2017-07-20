package com.kelvinhanma.todo;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.kelvinhanma.todo.data.AppDatabase;
import com.kelvinhanma.todo.data.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    private List<String> items = new ArrayList<>();
    private ListAdapter itemsAdapter;
    private AppDatabase myAppDb;

    @BindView(R.id.eNewItem)
    EditText myEditText;
    @BindView(R.id.btnAddItem)
    Button myAddButton;
    @BindView(R.id.lvlItems)
    ListView myListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        myAppDb = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "todo").build();

        itemsAdapter = new ArrayAdapter<>(this, R.layout.list_item, items);
        myListView.setAdapter(itemsAdapter);
        setupListViewListener();

        loadTasks();
    }

    private void loadTasks() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Task> tasks = myAppDb.getTaskDao().getTasks();
                items.clear();
                for (Task t:tasks) {
                    items.add(t.getName());
                }
                myListView.invalidate();
            }
        }).start();
    }

    private void setupListViewListener() {
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notify();
                return true;
            }
        });
    }

    public void onAddItem(View v) {
        final String taskName = myEditText.getText().toString();
        if (taskName.isEmpty()) {
            return;
        }
        final Context context = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (myAppDb.getTaskDao().findByName(taskName) != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, taskName + " already exists.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                myAppDb.getTaskDao().insertTask(new Task(0, taskName));
                loadTasks();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myEditText.setText("");
                    }
                });
            }
        }).start();
    }
}
