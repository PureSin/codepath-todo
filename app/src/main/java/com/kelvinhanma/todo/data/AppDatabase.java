package com.kelvinhanma.todo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by kelvinhanma on 7/19/17.
 */

@Database(entities = {Task.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao getTaskDao();
}
