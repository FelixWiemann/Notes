package com.nepumuk.notizen.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public abstract class FavouriteDAO extends BaseDAO<FavouriteEntity> {

    /**
     * gets all stored favourites
     * @return list of all favourites
     */
    @Query("SELECT * FROM favourites")
    public abstract LiveData<List<FavouriteEntity>> getAll();


    /**
     * returns a specific favourite specified by id
     * @param uuid
     * @return
     */
    @Query("SELECT * FROM favourites WHERE NoteId IN (:uuid) LIMIT 1")
    public abstract FavouriteEntity findFavourite(String uuid);

}
