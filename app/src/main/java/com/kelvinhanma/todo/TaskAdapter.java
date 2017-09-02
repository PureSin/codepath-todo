package com.kelvinhanma.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelvinhanma.todo.data.AppDatabase;
import com.kelvinhanma.todo.data.Task;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by kelvinhanma on 8/15/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    public static final int EDIT_TASK_REQUEST = 1;
    public static final String TASK_KEY = "task";
    public static final String POSITION = "position";

    private final List<Task> myTasks;
    private final TaskAdapter myAdapter;
    private AppDatabase myAppDb;
    private final Context myContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(ViewGroup v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskAdapter(Context context, List<Task> tasks, AppDatabase appDb) {
        myContext = context;
        myTasks = tasks;
        myAppDb = appDb;
        myAdapter = this;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Task task = myTasks.get(position);
        holder.mTextView.setText(task.getName());
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(myContext, EditActivity.class);
                i.putExtra(TASK_KEY, task);
                i.putExtra(POSITION, position);
                ((Activity) myContext).startActivityForResult(i, EDIT_TASK_REQUEST);
            }
        });
        holder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        myAppDb.getTaskDao().deleteTaskById(task.getUid());
                    }
                }).start();
                myTasks.remove(position);
                myAdapter.notifyItemRemoved(position);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return myTasks.size();
    }
}

