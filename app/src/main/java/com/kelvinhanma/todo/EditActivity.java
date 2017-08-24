package com.kelvinhanma.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kelvinhanma.todo.data.AppDatabase;
import com.kelvinhanma.todo.data.Task;

import butterknife.BindView;

import static com.kelvinhanma.todo.MainActivity.TASK_KEY;
import static com.kelvinhanma.todo.MainActivity.getDB;

public class EditActivity extends Activity {
    @BindView(R.id.eTaskName)
    EditText myEditText;
    @BindView(R.id.btnEditItem)
    Button mySaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        if (!i.hasExtra(TASK_KEY)) {
            // TODO
        }

        String name = i.getStringExtra(TASK_KEY);

        myEditText.setText(name);
    }

    public void onSaveItem(View view) {
        // TODO handle empty
        Intent i = new Intent();
        i.putExtra(MainActivity.TASK_KEY, myEditText.getText());
        setResult(MainActivity.EDIT_TASK_REQUEST, i);
    }
}
