package com.nepumuk.notizen.db;

import android.content.Context;

import androidx.room.Room;

/**
 * helper class for accessing the app database
 */
public class  AppDataBaseHelper {

    public static final int DATABASE_VERSION=6;

    private static AppDataBaseHelper helper;
    protected final AppDataBase appDataBase;

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
        appDataBase = Room.databaseBuilder(context,AppDataBase.class,"app_database").fallbackToDestructiveMigration().build();
    }

    private static void checkState(){
        if (helper == null){
            throw new IllegalStateException("trying to access Database while database is not initialized");
        }
    }

    public static void deleteInstance(){
        helper = null;
    }
}
