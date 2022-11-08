package com.example.mvvmtodoapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {

    public TodoDao todoDao;
    public LiveData<List<TodoModel>> getAllTodo;

    public TodoRepository(Application application) {
        TodoDatabase db = TodoDatabase.getINSTANCE(application);
        todoDao = db.todoDao();
        getAllTodo = db.todoDao().getTodo();
    }

    //yaha saare Dao k method banenge

    public void insertTodo(TodoModel todo){
        todoDao.insert(todo);
    }

    public void updateTodo(TodoModel todo){
        todoDao.update(todo);
    }

    public void deleteTodo(int id){
        todoDao.delete(id);
    }

}
