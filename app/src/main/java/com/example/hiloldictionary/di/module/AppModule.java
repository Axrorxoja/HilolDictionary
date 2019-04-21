package com.example.hiloldictionary.di.module;

import android.content.Context;

import androidx.room.Room;

import com.example.hiloldictionary.App;
import com.example.hiloldictionary.di.scope.AppScope;
import com.example.hiloldictionary.repository.storage.db.DefinitionDB;
import com.example.hiloldictionary.repository.storage.db.DefinitionDao;
import com.example.hiloldictionary.repository.storage.pref.IPreference;
import com.example.hiloldictionary.repository.storage.pref.PreferenceImpl;
import com.example.hiloldictionary.repository.storage.pref.PreferenceImpl2;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @AppScope
    @Provides
    public Context provideCtx(App app) {
        return app;
    }

    @AppScope
    @Provides
    public DefinitionDB provideDB(Context ctx) {
        return Room.databaseBuilder(ctx,
                DefinitionDB.class,
                "definition.sqlite")
                .build();
    }

    @AppScope
    @Provides
    public DefinitionDao provideDefinitionDao(DefinitionDB db) {
        return db.loadDefinitionDao();
    }

    @AppScope
    @Provides
    public IPreference providePref(Context context) {
        return new PreferenceImpl2(context);
    }
}
