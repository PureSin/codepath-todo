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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kelvinhanma.todo.data.AppDatabase;
import com.kelvinhanma.todo.data.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private List<Task> myTasks = new ArrayList<>();
    private AppDatabase myAppDb;

    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.eNewItem)
    EditText myEditText;
    @BindView(R.id.btnAddItem)
    Button myAddButton;
    @BindView(R.id.lvlItems)
    RecyclerView myRecyclerView;
    @BindView(R.id.prioritySpinner)
    Spinner myPrioritySpinner;

    private Context myContext;
    private Task.Priority mySelectedPriority = Task.Priority.NORMAL;

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

        myAdapter = new TaskAdapter(this, myTasks, myAppDb);
        myRecyclerView.setAdapter(myAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myPrioritySpinner.setAdapter(adapter);
        myPrioritySpinner.setOnItemSelectedListener(this);
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
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if (requestCode == TaskAdapter.EDIT_TASK_REQUEST) {
            Task task = data.getParcelableExtra(TaskAdapter.TASK_KEY);
            int position = data.getIntExtra(TaskAdapter.POSITION, -1);
            assert (position != -1);

            myTasks.set(position, task);
            myAdapter.notifyItemChanged(position);
        }
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
                Task newTask = new Task(0, taskName, mySelectedPriority);
                myAppDb.getTaskDao().insertTask(newTask);
                myTasks.add(newTask);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myEditText.setText("");
                        myPrioritySpinner.setSelection(0);
                        myAdapter.notifyItemInserted(myTasks.size());
                    }
                });
            }
        }).start();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        mySelectedPriority = Task.Priority.valueOf(parent.getItemAtPosition(pos).toString().toUpperCase());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
