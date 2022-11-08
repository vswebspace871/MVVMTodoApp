package com.example.mvvmtodoapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insert(TodoModel todo);

    @Update
    void update(TodoModel todo);

    @Query("DELETE FROM todoList WHERE id=:id")
    void delete(int id);

    @Query("SELECT * FROM todoList")
    LiveData<List<TodoModel>> getTodo();

}
