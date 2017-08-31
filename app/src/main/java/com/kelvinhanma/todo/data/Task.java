package com.kelvinhanma.todo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by kelvinhanma on 7/19/17.
 */

@Entity
public class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String name;

    public Task(int uid, String name) {
        this.name = name;
    }

    protected Task(Parcel in) {
        uid = in.readInt();
        name = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(uid);
        parcel.writeString(name);
    }
}
