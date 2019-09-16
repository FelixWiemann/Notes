package com.example.felix.notizen.Utils.DBAccess;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.objects.StorageUnpackerFactory;
import com.example.felix.notizen.objects.cIdObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class cDBDataHandler {
    private cDBHelper aHelper;

    public cDBDataHandler(){
        aHelper = cDBHelper.getInstance();
    }

    /**
     * inserts an DatabaseStorable into the DB
     * @param object to insert
     */
    public void insert(DatabaseStorable object){
        cNoteLogger.getInstance().logInfo("inserting into DB: " + object.getId());
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
                    storable = StorageUnpackerFactory.getInstance().createFromData(id, type, data, version);
                    list.add(storable);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | IOException | InstantiationException e) {
                    cNoteLogger.getInstance().logError("could not create from DB of type " + type +" with version "+ version, e);
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
        cNoteLogger.getInstance().logInfo("deleting " + object.getId());
        String selection = cDBHelper.aDB_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {object.getId()};
        aHelper.delete(selection,selectionArgs);
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

}
