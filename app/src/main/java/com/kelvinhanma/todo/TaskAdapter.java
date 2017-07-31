package com.kelvinhanma.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kelvinhanma.todo.data.Task;

import java.util.List;

/**
 * Created by kelvinhanma on 7/20/17.
 */

class TaskAdapter extends BaseAdapter {
    private final Context myContext;
    private List<Task> myTasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        myContext = context;
        myTasks = tasks;
    }

    @Override
    public int getCount() {
        return myTasks.size();
    }

    @Override
    public Task getItem(int i) {
        return myTasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return myTasks.get(i).getUid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) myContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row, null);
        } else {
            v = convertView;
        }

        TextView textView = v.findViewById(R.id.textView);
        textView.setText(myTasks.get(position).getName());
        return v;

    }

    public void setTasks(List tasks) {
        myTasks = tasks;
    }
}
