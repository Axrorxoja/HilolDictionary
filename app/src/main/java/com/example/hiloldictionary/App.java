package com.example.hiloldictionary;


import com.example.hiloldictionary.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
        //todo https://git-scm.com/book/ru/v1/Основы-Git-Создание-Git-репозитория
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.factory().create(this);

    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }
}
