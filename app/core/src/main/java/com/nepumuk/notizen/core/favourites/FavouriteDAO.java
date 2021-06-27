package com.nepumuk.notizen.core.favourites;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class FavouriteDAO {

    /**
     * gets all stored favourites
     * @return list of all favourites
     */
    @Query("SELECT * FROM favourite")
    public abstract List<Favourite> getAll();

    /**
     * returns a specific favourite specified by id
     * @param uuid
     * @return
     */
    @Query("SELECT * FROM favourite WHERE note_id IN (:uuid) LIMIT 1")
    public abstract Favourite findFavourite(String uuid);


    /**
     * inserts all given favourites
     * @param favourites
     */
    @Insert
    public abstract void insertAll(Favourite... favourites);

    /**
     * favourites to delete
     * @param favourites to delete
     */
    @Delete
    public abstract void delete(Favourite... favourites);

    /**
     * update the given favourites
     * @param favourites to update
     * @return number of favourites changed
     */
    @Update
    public abstract int update(Favourite... favourites);

    /**
     * creates or updates a favourite in the database depending of whether it exists or not
     * @param favourite to update or create
     */
    @Transaction()
    public void createOrUpdate(Favourite favourite){
        if (update(favourite)==0){
            insertAll(favourite);
        }
    }

}
