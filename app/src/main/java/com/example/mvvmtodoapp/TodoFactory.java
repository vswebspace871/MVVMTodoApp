package com.example.mvvmtodoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TodoFactory implements ViewModelProvider.Factory {

    Application application;

    public TodoFactory(Application application) {
        this.application = application;
    }
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TodoViewModel(application);
    }
}
