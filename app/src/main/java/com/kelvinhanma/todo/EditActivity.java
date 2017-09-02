package com.kelvinhanma.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kelvinhanma.todo.data.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends Activity {
    @BindView(R.id.eTaskName)
    EditText myEditText;
    @BindView(R.id.btnEditItem)
    Button mySaveButton;
    private Task myTask;
    private int myPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        Intent i = getIntent();
        assert i.hasExtra(TaskAdapter.TASK_KEY);
        myPosition = i.getIntExtra(TaskAdapter.POSITION, -1);
        assert myPosition != -1;
        myTask = i.getParcelableExtra(TaskAdapter.TASK_KEY);
        myEditText.setText(myTask.getName());
    }

    public void onSaveItem(View view) {
        String name = myEditText.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Task name cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        myTask.setName(name);
        Intent i = new Intent();
        i.putExtra(TaskAdapter.TASK_KEY, myTask);
        i.putExtra(TaskAdapter.POSITION, myPosition);
        setResult(RESULT_OK, i);
        finish();
    }
}
