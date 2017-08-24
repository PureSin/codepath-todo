package com.kelvinhanma.todo;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kelvinhanma.todo.data.AppDatabase;
import com.kelvinhanma.todo.data.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    public static final int EDIT_TASK_REQUEST = 1;
    public static final String TASK_KEY = "task";
    private List<Task> myTasks = new ArrayList<>();
    private AppDatabase myAppDb;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.eNewItem)
    EditText myEditText;
    @BindView(R.id.btnAddItem)
    Button myAddButton;
    @BindView(R.id.lvlItems)
    RecyclerView myRecyclerView;
    private Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myContext = this;
        myAppDb = getDB(myContext);


        myRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TaskAdapter(this, myTasks);
        myRecyclerView.setAdapter(mAdapter);
        setupListViewListener();

        loadTasks();
    }

    public static AppDatabase getDB(Context c) {
        return Room.databaseBuilder(c,
                AppDatabase.class, "todo").build();
    }

    private void loadTasks() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                myTasks.clear();
                myTasks.addAll(myAppDb.getTaskDao().getTasks());
                System.out.println("Tasks size: " + myTasks.size());
            }
        }).start();
    }

    private void setupListViewListener() {
//        myRecyclerView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
//                myAppDb.getTaskDao().deleteTaskById(id);
//                myTasks.remove(pos);
//                itemsAdapter.notify();
//                return true;
//            }
//        });
//
//        myRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
//                Intent i = new Intent(myContext, EditActivity.class);
//                i.putExtra(TASK_KEY, (String) myRecyclerView.getItemAtPosition(pos));
//                startActivityForResult(i, EDIT_TASK_REQUEST);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            // TODO
        }

        // update Task

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
                Task newTask = new Task(0, taskName);
                myAppDb.getTaskDao().insertTask(newTask);
                myTasks.add(newTask);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myEditText.setText("");
                        mAdapter.notifyItemInserted(myTasks.size());
                    }
                });
            }
        }).start();
    }
}
