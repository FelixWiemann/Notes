package com.nepumuk.notizen.core.utils.db_access;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nepumuk.notizen.core.favourites.Favourite;
import com.nepumuk.notizen.core.favourites.FavouriteDAO;

@Database(entities = {Favourite.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract FavouriteDAO favouriteDAO();
}
