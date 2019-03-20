package com.example.hiloldictionary.repository.storage.pref;

import android.content.Context;

public final class PreferenceService {
    private static IPreference preference;

    private PreferenceService() {
    }

    public static IPreference loadPrefence(Context ctx) {
        if (preference == null) {
            preference = new PreferenceImpl(ctx);
        }
        return preference;
    }
}
