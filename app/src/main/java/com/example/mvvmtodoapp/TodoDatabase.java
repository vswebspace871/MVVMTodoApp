package com.example.mvvmtodoapp;

import androidx.room.Database;
import androidx.room.Room;

import android.content.Context;

import androidx.room.RoomDatabase;

@Database(entities = TodoModel.class, version = 1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase instance;

    public abstract TodoDao todoDao();

    public synchronized static TodoDatabase getINSTANCE(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TodoDatabase.class, "todo_database").fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
