package com.nepumuk.notizen.Utils.DBAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.nepumuk.notizen.objects.StorableFactoy.StorableFactory;
import com.nepumuk.notizen.objects.UnpackingDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Database Data Handler for wrapping the Database
 */
public class cDBDataHandler {
    private static final String TAG = "DB DataHandler";
    private cDBHelper aHelper;

    /**
     * create a handler
     */
    public cDBDataHandler() {
        super();
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

    /**
     * creates the content values required to put the object into the database
     *
     * @param object object to convert
     * @return ContentValues displaying the object, ready to store
     */
    private ContentValues storableToContentValues(DatabaseStorable object){
        ContentValues contentValue = new ContentValues( );
        contentValue.put(cDBHelper.aDB_COLUMN_JSONDATA, object.getDataString());
        contentValue.put(cDBHelper.aDB_COLUMN_TYPE, object.getType());
        contentValue.put(cDBHelper.aDB_COLUMN_TYPEVERSION, object.getVersion());
        contentValue.put(cDBHelper.aDB_COLUMN_ID,object.getId());
        return contentValue;
    }

    /**
     * creates an object based on the given cursor
     * @param cursor object to convert
     * @return DatabaseStorable at the given cursor
     */
    protected DatabaseStorable contentValueToDatabaseStorable(Cursor cursor){
        String type = cursor.getString(cursor.getColumnIndex(cDBHelper.aDB_COLUMN_TYPE));
        String id = cursor.getString(cursor.getColumnIndex(cDBHelper.aDB_COLUMN_ID));
        String data = cursor.getString(cursor.getColumnIndex(cDBHelper.aDB_COLUMN_JSONDATA));
        int version = cursor.getInt(cursor.getColumnIndex(cDBHelper.aDB_COLUMN_TYPEVERSION));
        DatabaseStorable storable = null;
        try {
            storable = StorableFactory.createFromData(id, type, data, version);
        } catch (UnpackingDataException e) {
            Log.e(TAG, "could not create from DB of type " + type +" with version "+ version, e);
        }
        return storable;
    }

    /**
     * reads the database and returns all items in the database in a list
     * @return list of items in the database
     */
    public ArrayList<DatabaseStorable> read(){
        Cursor cursor = aHelper.getAll();
        ArrayList<DatabaseStorable> list = new ArrayList<> ();
        if(cursor.moveToFirst()){
            do{
                list.add(contentValueToDatabaseStorable(cursor));
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

    /**
     * deletes and reinits the database
     */
    public void reInitDatabase(){
        aHelper.deleteAndReinit();
    }

    /**
     * update the given object in the data base
     * @param object object to update
     */
    public void update(DatabaseStorable object){
        aHelper.update(storableToContentValues(object),cDBHelper.aDB_COLUMN_ID+"='"+object.getId()+"'",null);
    }

    /**
     * updates all objects of the given list in the database
     * @param objects to update
     */
    public void update(List<DatabaseStorable> objects){
        objects.forEach(new Consumer<DatabaseStorable>() {
            @Override
            public void accept(DatabaseStorable databaseStorable) {
                update(databaseStorable);
            }
        });
    }

    /**
     * deletes the object from the database with the given ID
     * @param objectId of object to delete
     */
    public void delete(String objectId) {
        Log.i(TAG, "deleting " + objectId);
        String selection = cDBHelper.aDB_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {objectId};
        aHelper.delete(selection,selectionArgs);
    }
}
