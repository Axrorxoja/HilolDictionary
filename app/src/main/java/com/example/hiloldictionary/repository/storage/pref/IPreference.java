package com.example.hiloldictionary.repository.storage.pref;

public interface IPreference {
    long loadTimeStamp();

    void saveTimeStamp(long timeStamp);
}
