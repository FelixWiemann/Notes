package com.nepumuk.notizen.Utils.DBAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.nepumuk.notizen.Utils.cContextManager;

/**
 *
 * SQL Manager Contract for main table
 * ID              |Type
 * UUID of Note    |type of note
 *
 */
public class cDBHelper extends SQLiteOpenHelper {

    private static final int aDB_VERSION = 2;

    private static final String aDB_Name = "Notes.db";
    private static final String aDB_TABLE_NAME = "entries";
    static final String aDB_COLUMN_ID = "ID";
    static final String aDB_COLUMN_TYPE = "Type";
    static final String aDB_COLUMN_JSONDATA = "DATA";
    static final String aDB_COLUMN_TYPEVERSION = "Version";
    private static final String TAG = "DB Helper" ;


    private static cDBHelper mMasterInstance = null;
    private static SQLiteDatabase mDB;

    private cDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i(TAG, "creating DB Master");
        Log.d(TAG, "cDBHelper: " + Environment.getExternalStorageDirectory().getPath());
        init();
    }

    private void init(){
        mDB = this.getReadableDatabase();
        Log.d(TAG, "init: stored at " + getDBPAth());
    }

    /**
     * gets the helper instance
     * @return helper instance
     */
    protected static cDBHelper getInstance(){

        if (mMasterInstance == null){
            mMasterInstance = new cDBHelper(cContextManager.getInstance().getContext(), aDB_Name, null, aDB_VERSION);
        }

        return mMasterInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Database Created");
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // read all entries from DB
        // deleteAndReinit table
        // create new table
        // put everything back in again
    }

    void update(ContentValues values, String whereClause, String[] whereArgs){
        mDB.update(aDB_TABLE_NAME,values,whereClause, whereArgs);
    }

    void delete(String selection, String[] selectionArgs){
        mDB.delete(aDB_TABLE_NAME, selection, selectionArgs);
    }

    private void rawSQL(String command){
        Log.w(TAG, "raw SQL on DB: <" + command + ">");
        mDB.execSQL(command);
    }

    void deleteAndReinit(){
        rawSQL(SQL_DELETE_ENTRIES);
        rawSQL(SQL_CREATE_ENTRIES);
    }

    void insert(ContentValues contentValue){
        mDB.insert(aDB_TABLE_NAME,null,contentValue);
    }

    public Cursor getAll(){
        return getCursorForQuery("SELECT * FROM " + aDB_TABLE_NAME);
    }

    private Cursor getCursorForQuery(String query){
        return mDB.rawQuery(query, null);
    }

    public String getDBPAth(){
        return  mDB.getPath();
    }

    /**
     * opens database connection, if not already open
     * @throws cDBMasterException, if connection is already open
     */
    public void openDB() throws cDBMasterException {
        if (mDB!=null){
            throw new cDBMasterException(cDBMasterException.aSQL_CONNECTION_ALREADY_OPEN,null);
        }else{
            mDB = mMasterInstance.getReadableDatabase();
        }
    }
    /**
     * closes the DB-connection
     */
    public void closeDB(){
        Log.i(TAG, "closing DB");
        mDB.close();
        mDB = null;
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + aDB_TABLE_NAME + " (" +
                    aDB_COLUMN_ID + " TEXT PRIMARY KEY," +
                    aDB_COLUMN_TYPE + " TEXT," +
                    aDB_COLUMN_JSONDATA + " TEXT," +
                    aDB_COLUMN_TYPEVERSION + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
           "DROP TABLE IF EXISTS '" + aDB_TABLE_NAME + "'";

}
