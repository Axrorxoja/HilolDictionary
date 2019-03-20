package com.example.hiloldictionary.repository.storage.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

@Dao
public interface DefinitionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAll(ArrayList<Definition> definitions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addItem(Definition definition);

    @Query("select * from definition where word like :key || '%'")
    Single<List<Definition>> search(String key);

    @Query("select * from definition limit 20 offset :offset")
    Single<List<Definition>> loadDefinitionsByPage(int offset);//page =100, offset=100
}
