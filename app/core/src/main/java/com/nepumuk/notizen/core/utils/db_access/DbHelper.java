package com.nepumuk.notizen.core.utils.db_access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nepumuk.notizen.core.utils.ContextManager;

/**
 *
 * SQL Manager Contract for main table
 * ID              |Type
 * UUID of Note    |type of note
 *
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int aDB_VERSION = 2;

    private static final String aDB_Name = "Notes.db";
    private static final String aDB_TABLE_NAME = "entries";
    static final String aDB_COLUMN_ID = "ID";
    static final String aDB_COLUMN_TYPE = "Type";
    static final String aDB_COLUMN_JSONDATA = "DATA";
    static final String aDB_COLUMN_TYPEVERSION = "Version";
    private static final String TAG = "DB Helper";


    private static DbHelper mMasterInstance = null;
    /**
     * this database instance will be open the entire time the application runs.<p></p>
     * it will be cleaned up by the GC of the system, the {@link SQLiteDatabase#close} will never be called by this helper.<p></p>
     * see <a href=https://stackoverflow.com/a/7739454>this answer on StackOverflow</a> for more details
     */
    private SQLiteDatabase mDB;

    DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // if from static, the context will be always set
        // otherwise we get it from the test injected
        if(context!=null) {
            mDB = this.getReadableDatabase();
        }
    }

    /**
     * gets the helper instance
     * @return helper instance
     */
    protected static DbHelper getInstance(){

        if (mMasterInstance == null){
            mMasterInstance = new DbHelper(ContextManager.getInstance().getContext(), aDB_Name, null, aDB_VERSION);
        }

        return mMasterInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
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

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + aDB_TABLE_NAME + " (" +
                    aDB_COLUMN_ID + " TEXT PRIMARY KEY," +
                    aDB_COLUMN_TYPE + " TEXT," +
                    aDB_COLUMN_JSONDATA + " TEXT," +
                    aDB_COLUMN_TYPEVERSION + " INTEGER)";

    static final String SQL_DELETE_ENTRIES =
           "DROP TABLE IF EXISTS '" + aDB_TABLE_NAME + "'";

}
