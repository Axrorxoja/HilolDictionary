package com.example.hiloldictionary.repository.storage.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Definition.class}, version = 1)
public abstract class DefinitionDB extends RoomDatabase {
    public abstract DefinitionDao loadDefinitionDao();
}
