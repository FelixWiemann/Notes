package com.nepumuk.notizen.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import static com.nepumuk.notizen.db.AppDataBaseHelper.DATABASE_VERSION;

@Database(entities = {FavouriteEntity.class}, version = DATABASE_VERSION)
public abstract class AppDataBase extends RoomDatabase {
    abstract FavouriteDAO favouriteDAO();
}
