package com.kelvinhanma.todo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by kelvinhanma on 7/19/17.
 */

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String name;

    public Task(int uid, String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
