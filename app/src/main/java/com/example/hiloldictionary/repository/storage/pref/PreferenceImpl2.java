package com.example.hiloldictionary.repository.storage.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceImpl2 implements IPreference {
    private SharedPreferences preference;
    private String KEY_TIME_STAMP = "KEY_TIME_STAMP";

    public PreferenceImpl2(Context ctx) {
        this.preference = PreferenceManager
                .getDefaultSharedPreferences(ctx);
    }

    @Override
    public long loadTimeStamp() {
        return preference.getLong(KEY_TIME_STAMP, 0);
    }

    @Override
    public void saveTimeStamp(long timeStamp) {
        preference
                .edit()
                .putLong(KEY_TIME_STAMP, timeStamp)
                .apply();
    }
}
