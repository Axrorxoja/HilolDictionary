package com.example.hiloldictionary.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hiloldictionary.common.IData;
import com.example.hiloldictionary.repository.storage.db.Definition;
import com.example.hiloldictionary.repository.storage.db.DefinitionDao;
import com.example.hiloldictionary.repository.storage.db.DefinitionService;
import com.example.hiloldictionary.repository.storage.pref.IPreference;
import com.example.hiloldictionary.repository.storage.pref.PreferenceService;
import com.example.hiloldictionary.ui.main.MainActivity;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SplashActivity extends AppCompatActivity {
    private Disposable d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveToDB();
    }

    private void saveToDB() {
        d = Single.fromCallable(this::computeAndSave)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> startNextActivity());
    }

    private void startNextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private Boolean computeAndSave() {
        IPreference preference = PreferenceService.loadPrefence(this);
        DefinitionDao dao = DefinitionService.loadDAO(this);
        long lastTimeStamp = preference.loadTimeStamp();
        Timber.d("computeAndSave %s", lastTimeStamp);
        if (lastTimeStamp > 0) return false;
        String[] words = IData.DATA.split("\n");
        ArrayList<Definition> definitionList = new ArrayList<>();
        Definition definition;
        for (String word : words) {
            definition = new Definition(word, "test");
            definitionList.add(definition);
        }
        dao.addAll(definitionList);
        preference.saveTimeStamp(System.currentTimeMillis());
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
    }
}
