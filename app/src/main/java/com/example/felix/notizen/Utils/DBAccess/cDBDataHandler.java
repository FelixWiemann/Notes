package com.example.felix.notizen.Utils.DBAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.felix.notizen.objects.StoragePackerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class cDBDataHandler {
    private static final String TAG = "DB DataHandler";
    private cDBHelper aHelper;

    public cDBDataHandler(){
        aHelper = cDBHelper.getInstance();
    }

    /**
     * inserts an DatabaseStorable into the DB
     * @param object to insert
     */
    public void insert(DatabaseStorable object){
        Log.d(TAG, "insert: "+ object.getId());
        aHelper.insert(storableToContentValues(object));
    }


    private ContentValues storableToContentValues(DatabaseStorable object){
        ContentValues contentValue = new ContentValues( );
        contentValue.put(cDBHelper.aDB_COLUMN_JSONDATA, object.getDataString());
        contentValue.put(cDBHelper.aDB_COLUMN_TYPE, object.getType());
        contentValue.put(cDBHelper.aDB_COLUMN_TYPEVERSION, object.getVersion());
        contentValue.put(cDBHelper.aDB_COLUMN_ID,object.getId());
        return contentValue;
    }

    public List<DatabaseStorable> read(){
        Cursor cursor = aHelper.getAll();
        ArrayList list = new ArrayList<DatabaseStorable> ();
        if(cursor.moveToFirst()){
            do{
                String type = cursor.getString(cursor.getColumnIndex(cDBHelper.aDB_COLUMN_TYPE));
                String id = cursor.getString(cursor.getColumnIndex(cDBHelper.aDB_COLUMN_ID));
                String data = cursor.getString(cursor.getColumnIndex(cDBHelper.aDB_COLUMN_JSONDATA));
                int version = cursor.getInt(cursor.getColumnIndex(cDBHelper.aDB_COLUMN_TYPEVERSION));
                DatabaseStorable storable = null;
                try {
                    storable = StoragePackerFactory.createFromData(id, type, data, version);
                    list.add(storable);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                    Log.e(TAG, "could not create from DB of type " + type +" with version "+ version, e);
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * deleteAndReinit the object out of the DB
     * @param object object to deleteAndReinit
     */
    public void delete(DatabaseStorable object){
        delete(object.getId());
    }

    public void reinitDatabase(){
        aHelper.deleteAndReinit();
    }

    public void update(DatabaseStorable object){
        aHelper.update(storableToContentValues(object),cDBHelper.aDB_COLUMN_ID+"='"+object.getId()+"'",null);
    }

    public void update(List<DatabaseStorable> objects){
        objects.forEach(new Consumer<DatabaseStorable>() {
            @Override
            public void accept(DatabaseStorable databaseStorable) {
                update(databaseStorable);
            }
        });
    }

    public void delete(String objectId) {
        Log.i(TAG, "deleting " + objectId);
        String selection = cDBHelper.aDB_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {objectId};
        aHelper.delete(selection,selectionArgs);
    }
}
