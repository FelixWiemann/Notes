package com.nepumuk.notizen.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.Hashtable;

/**
 * helper class for accessing the app database
 */
public class  AppDataBaseHelper {

    public static final int DATABASE_VERSION=6;

    private static AppDataBaseHelper helper;
    protected final AppDataBase appDataBase;
    private Hashtable<Class<? extends RoomDatabase>, RoomDatabase> databaseDictionary;

    public static AppDataBaseHelper getInstance(Context context){
        if (helper ==null){
            helper = new AppDataBaseHelper(context);
        }
        return helper;
    }

    public static AppDataBaseHelper getInstance(){
        checkState();
        return helper;
    }

    private AppDataBaseHelper(Context context) {
        super();
        databaseDictionary = new Hashtable<>();
        appDataBase = Room.databaseBuilder(context,AppDataBase.class,"app_database").fallbackToDestructiveMigration().build();
        Favourites = new FavouriteRepository(appDataBase.favouriteDAO());
        // observer required for live updates
        Favourites.getLiveFavourite().observeForever(favourites -> {});
    }

    private static void checkState(){
        if (helper == null){
            throw new IllegalStateException("trying to access Database while database is not initialized");
        }
    }

    public static void deleteInstance(){
        helper = null;
    }

    private final FavouriteRepository Favourites ;

    public FavouriteRepository getFavourites(){
        return Favourites;
    }


}
