package com.kelvinhanma.todo.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by kelvinhanma on 7/19/17.
 */

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getTasks();

    @Query("SELECT * FROM task WHERE name=:taskName")
    Task findByName(String taskName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTask(Task task);

    @Query("DELETE FROM task WHERE uid=:id")
    void deleteTaskById(long id);
}
