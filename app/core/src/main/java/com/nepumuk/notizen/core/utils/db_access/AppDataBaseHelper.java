package com.nepumuk.notizen.core.utils.db_access;

import android.content.Context;

import androidx.room.Room;

import com.nepumuk.notizen.core.favourites.FavouriteDAO;

/**
 * helper class for accessing the app database
 */
public class AppDataBaseHelper {

    private static AppDataBaseHelper helper;
    private final AppDataBase appDataBase;

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
        appDataBase = Room.databaseBuilder(context,AppDataBase.class,"app_database").build();
    }

    /**
     * get the favourite DAO
     * @return fav dao
     */
    public static FavouriteDAO getFavouriteDao(){
        checkState();
        return helper.appDataBase.favouriteDAO();
    }

    private static void checkState(){
        if (helper == null){
            throw new IllegalStateException("trying to access Database while database is not initialized");
        }
    }

}
