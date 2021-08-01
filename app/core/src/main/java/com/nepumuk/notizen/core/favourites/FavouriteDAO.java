package com.nepumuk.notizen.core.favourites;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.nepumuk.notizen.core.utils.db_access.BaseDAO;

import java.util.List;

@Dao
public abstract class FavouriteDAO extends BaseDAO<Favourite> {

    /**
     * gets all stored favourites
     * @return list of all favourites
     */
    @Query("SELECT * FROM favourite")
    public abstract LiveData<List<Favourite>> getAll();

    /**
     * returns a specific favourite specified by id
     * @param uuid
     * @return
     */
    @Query("SELECT * FROM favourite WHERE note_id IN (:uuid) LIMIT 1")
    public abstract Favourite findFavourite(String uuid);

}
