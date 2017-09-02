package com.kelvinhanma.todo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by kelvinhanma on 7/19/17.
 */

@Entity
public class Task implements Parcelable {
    public static class Converters {
        @TypeConverter
        public Priority toPriority(String priority) {
            return priority == null ? Priority.NORMAL : Priority.valueOf(priority);
        }

        @TypeConverter
        public String fromPriority(Priority priority) {
            return priority == null ? Priority.NORMAL.toString() : priority.name();
        }
    }
    public enum Priority {
        NORMAL,
        HIGH,
        LOW;

        public static int getIndex(Priority priority) {
            switch (priority) {
                case NORMAL:
                    return 0;
                case HIGH:
                    return 1;
                case LOW:
                    return 2;
                default:
                    return -1;
            }
        }
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String name;

    private String description;

    private Priority priority;

    public Task(int uid, String name, Priority priority) {
        this.name = name;
        this.priority = priority;
    }

    protected Task(Parcel in) {
        uid = in.readInt();
        name = in.readString();
        description = in.readString();
        priority = Priority.valueOf(in.readString());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(uid);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(priority.name());
    }
}
