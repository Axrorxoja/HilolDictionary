package com.example.hiloldictionary.di.module;

import android.content.Context;

import com.example.hiloldictionary.di.scope.MainActivityScope;
import com.example.hiloldictionary.ui.main.MainActivity;
import com.example.hiloldictionary.ui.main.adapter.ItemClickListener;
import com.example.hiloldictionary.ui.main.adapter.WordAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @MainActivityScope
    @Provides
    public WordAdapter provideAdapter(Context ctx,
                                      ItemClickListener listener) {
        return new WordAdapter(ctx, new ArrayList<>(), listener);
    }
    @MainActivityScope
    @Provides
    public ItemClickListener provideListener(MainActivity activity) {
        return activity;
    }
}
