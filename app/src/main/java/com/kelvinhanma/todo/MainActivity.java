package com.kelvinhanma.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private List<String> items;
    private ListAdapter itemsAdapter;

    private EditText myEditText;
    private Button myAddButton;
    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myEditText = findViewById(R.id.eNewItem);
        myAddButton = findViewById(R.id.btnAddItem);
        myListView = findViewById(R.id.lvlItems);

        initItems();
        itemsAdapter = new ArrayAdapter<>(this, R.layout.list_item, items);
        myListView.setAdapter(itemsAdapter);
        setupListViewListener();
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

    private void initItems() {
        items = new ArrayList<>();
        items.add("First");
        items.add("Second");
    }

    public void onAddItem(View v) {
        items.add(myEditText.getText().toString());
        myEditText.setText("");
    }
}
