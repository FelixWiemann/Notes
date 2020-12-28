package com.nepumuk.notizen.utils.db_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.nepumuk.notizen.objects.UnpackingDataException;
import com.nepumuk.notizen.objects.storable_factory.StorableFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Database Data Handler for wrapping the Database
 */
public class DbDataHandler {
    private static final String TAG = "DB DataHandler";
    private final DbHelper aHelper;

    /**
     * create a handler
     */
    public DbDataHandler() {
        super();
        aHelper = DbHelper.getInstance();
    }

    /**
     * inserts an DatabaseStorable into the DB
     * @param object to insert
     */
    public void insert(DatabaseStorable object){
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
        contentValue.put(DbHelper.aDB_COLUMN_JSONDATA, object.getDataString());
        contentValue.put(DbHelper.aDB_COLUMN_TYPE, object.getType());
        contentValue.put(DbHelper.aDB_COLUMN_TYPEVERSION, object.getVersion());
        contentValue.put(DbHelper.aDB_COLUMN_ID,object.getId());
        return contentValue;
    }

    /**
     * creates an object based on the given cursor
     * @param cursor object to convert
     * @return DatabaseStorable at the given cursor
     */
    protected DatabaseStorable contentValueToDatabaseStorable(Cursor cursor){
        String type = cursor.getString(cursor.getColumnIndex(DbHelper.aDB_COLUMN_TYPE));
        String id = cursor.getString(cursor.getColumnIndex(DbHelper.aDB_COLUMN_ID));
        String data = cursor.getString(cursor.getColumnIndex(DbHelper.aDB_COLUMN_JSONDATA));
        int version = cursor.getInt(cursor.getColumnIndex(DbHelper.aDB_COLUMN_TYPEVERSION));
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
        aHelper.update(storableToContentValues(object), DbHelper.aDB_COLUMN_ID+"='"+object.getId()+"'",null);
    }

    /**
     * updates all objects of the given list in the database
     * @param objects to update
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(List<DatabaseStorable> objects){
        objects.forEach(this::update);
    }

    /**
     * deletes the object from the database with the given ID
     * @param objectId of object to delete
     */
    public void delete(String objectId) {
        String selection = DbHelper.aDB_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {objectId};
        aHelper.delete(selection,selectionArgs);
    }
}
