package com.nepumuk.notizen.core.favourites;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDAO {

    @Query("SELECT * FROM favourite")
    List<Favourite> getAll();

    @Query("SELECT * FROM favourite WHERE note_id IN (:uuid) LIMIT 1")
    Favourite findFavourite(String uuid);

    @Insert
    void insertAll(Favourite... favourites);

    @Delete
    void delete(Favourite favourite);

}
