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

public class cDBDataHandler {
    private cDBHelper aHelper;

    public cDBDataHandler(){
        aHelper = cDBHelper.getInstance();
    }

    /**
     * inserts an cIDObject into the DB
     * @param object to insert
     */
    public void insert(DatabaseStorable object){
        cNoteLogger.getInstance().logInfo("inserting into DB: " + object.getId());
        ContentValues contentValue = new ContentValues( );
        contentValue.put(cDBHelper.aDB_COLUMN_JSONDATA, object.getDataString());
        contentValue.put(cDBHelper.aDB_COLUMN_TYPE, object.getType());
        contentValue.put(cDBHelper.aDB_COLUMN_TYPEVERSION, object.getVersion());
        contentValue.put(cDBHelper.aDB_COLUMN_ID,object.getId());
        aHelper.insert(contentValue);
    }


    public List<DatabaseStorable> read(){
        Cursor cursor = aHelper.getAll();
        List<DatabaseStorable> list = new ArrayList<DatabaseStorable>();
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
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * delete the object out of the DB
     * @param object object to delete
     */
    public void delete(cIdObject object){
        cNoteLogger.getInstance().logInfo("deleting " + object.getIdString());
        String selection = cDBHelper.aDB_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {object.getIdString()};
        aHelper.delete(selection,selectionArgs);
    }

    public void reinitDatabase(){
        aHelper.delete();

    }

}
