package com.example.hiloldictionary.di.sub_component;

import com.example.hiloldictionary.di.module.MainActivityModule;
import com.example.hiloldictionary.di.scope.MainActivityScope;
import com.example.hiloldictionary.ui.main.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = MainActivityModule.class)
@MainActivityScope
public interface MainActivitySubComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory
            extends AndroidInjector.Factory<MainActivity> {
    }
}
