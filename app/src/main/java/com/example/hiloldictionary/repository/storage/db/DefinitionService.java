package com.example.hiloldictionary.repository.storage.db;

import android.content.Context;

import androidx.room.Room;

public final class DefinitionService {
    private static final String DB_NAME = "definition.sqlite";
    private static DefinitionDB db;
    private static DefinitionDao dao;

    private DefinitionService() {
    }

    // todo lazy init,singleton,builder
    public static DefinitionDB loadDB(Context ctx) {
        if (db == null) {
            db = Room.databaseBuilder(ctx,
                    DefinitionDB.class,
                    DB_NAME)
                    .build();
        }
        return db;
    }

    public static DefinitionDao loadDAO(Context ctx) {
        if (dao == null) {
            dao = loadDB(ctx).loadDefinitionDao();
        }
        return dao;
    }

}
