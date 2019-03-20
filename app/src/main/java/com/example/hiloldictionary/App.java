package com.example.hiloldictionary;

import android.app.Application;

import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
        //todo https://git-scm.com/book/ru/v1/Основы-Git-Создание-Git-репозитория
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }
}
