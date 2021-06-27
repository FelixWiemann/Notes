package com.nepumuk.notizen.core.utils.db_access;

import android.content.Context;

import androidx.room.Room;

public class AppDataBaseHelper {

    private static AppDataBaseHelper helper;
    public AppDataBase appDataBase;

    public static AppDataBaseHelper getInstance(Context context){
        if (helper ==null){
            helper = new AppDataBaseHelper(context);
        }
        return helper;
    }

    public static AppDataBaseHelper getInstance(){
        if (helper == null){
            throw new IllegalStateException("trying to access Database while database is not initialized");
        }
        return helper;
    }

    private AppDataBaseHelper(Context context) {
        super();
        appDataBase = Room.databaseBuilder(context,AppDataBase.class,"app_database").build();
    }



}
