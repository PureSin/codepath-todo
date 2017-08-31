package com.kelvinhanma.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kelvinhanma.todo.data.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends Activity {
    @BindView(R.id.eTaskName)
    EditText myEditText;
    @BindView(R.id.btnEditItem)
    Button mySaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        Intent i = getIntent();
        if (!i.hasExtra(TaskAdapter.TASK_KEY)) {
            // TODO
        }

        Task task = i.getParcelableExtra(TaskAdapter.TASK_KEY);

        myEditText.setText(task.getName());
    }

    public void onSaveItem(View view) {
        // TODO handle empty
        Intent i = new Intent();
        i.putExtra(TaskAdapter.TASK_KEY, myEditText.getText());
        setResult(TaskAdapter.EDIT_TASK_REQUEST, i);
    }
}
