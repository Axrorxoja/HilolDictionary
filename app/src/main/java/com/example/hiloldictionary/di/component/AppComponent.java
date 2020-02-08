package com.example.hiloldictionary.di.component;

import com.example.hiloldictionary.App;
import com.example.hiloldictionary.di.module.ActivityModule;
import com.example.hiloldictionary.di.module.AppModule;
import com.example.hiloldictionary.di.scope.AppScope;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AppModule.class,
        ActivityModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class
})
@AppScope
interface AppComponent extends AndroidInjector<App> {
    @Component.Factory
    interface Factory extends AndroidInjector.Factory<App> {
    }
}