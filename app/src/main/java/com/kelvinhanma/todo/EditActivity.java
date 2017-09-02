package com.kelvinhanma.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kelvinhanma.todo.data.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends Activity implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.eTaskName)
    EditText myEditText;
    @BindView(R.id.btnEditItem)
    Button mySaveButton;
    @BindView(R.id.editDesc)
    EditText myDescEditText;
    @BindView(R.id.editPriority)
    Spinner myPrioritySpinner;
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
        myDescEditText.setText(myTask.getDescription());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myPrioritySpinner.setAdapter(adapter);
        myPrioritySpinner.setOnItemSelectedListener(this);
        myPrioritySpinner.setSelection(Task.Priority.getIndex(myTask.getPriority()));
    }

    public void onSaveItem(View view) {
        String name = myEditText.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Task name cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        myTask.setName(name);
        myTask.setDescription(myDescEditText.getText().toString());
        Intent i = new Intent();
        i.putExtra(TaskAdapter.TASK_KEY, myTask);
        i.putExtra(TaskAdapter.POSITION, myPosition);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        myTask.setPriority(Task.Priority.valueOf(parent.getItemAtPosition(pos).toString().toUpperCase()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
