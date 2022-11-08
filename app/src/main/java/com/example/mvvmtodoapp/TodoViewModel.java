package com.example.mvvmtodoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    public TodoRepository todoRepository;
    public LiveData<List<TodoModel>> getAllTodos;


    public TodoViewModel(@NonNull Application application) {
        super(application);

        todoRepository = new TodoRepository(application);
        getAllTodos = todoRepository.getAllTodo;

    }

    void insertTodos(TodoModel todo){
        todoRepository.insertTodo(todo);
    }

    void updateTodos(TodoModel todo){
        todoRepository.updateTodo(todo);
    }

    void deleteTodos(int id){
        todoRepository.deleteTodo(id);
    }


}
