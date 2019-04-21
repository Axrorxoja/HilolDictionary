package com.example.hiloldictionary.di.module;

import com.example.hiloldictionary.di.sub_component.MainActivitySubComponent;
import com.example.hiloldictionary.ui.main.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = MainActivitySubComponent.class)
public abstract class ActivityModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity.class)
    abstract AndroidInjector.Factory<?>
    bindYourActivityInjectorFactory(MainActivitySubComponent.Factory factory);



}
