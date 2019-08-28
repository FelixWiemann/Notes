package com.example.felix.notizen.Utils.DBAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.Utils.cContextManager;

/**
 * NOT IN USE ANYMORE
 * DECIDED AGAINST DB USAGE; BUT JSON FILE
 *
 * SQL Manager Contract for main table
 * ID              |Type
 * UUID of Note    |type of note
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd.DBAccess
 * by Felix "nepumuk" Wiemann on 10/06/17.
 *
 */
public class cDBHelper extends SQLiteOpenHelper {

    //Database Version
    private static final String aLOG_TAG = "SQL_MANAGER_CONTRACT" ;

    private static final int aDB_VERSION = 2;

    private cNoteLogger mLogger = cNoteLogger.getInstance();
    private static final String aDB_Name = "Notes.db";
    private static final String aDB_TABLE_NAME = "entries";
    static final String aDB_COLUMN_ID = "ID";
    static final String aDB_COLUMN_TYPE = "Type";
    static final String aDB_COLUMN_JSONDATA = "DATA";
    static final String aDB_COLUMN_TYPEVERSION = "Version";



    private static cDBHelper mMasterInstance = new cDBHelper(cContextManager.getInstance().getContext(), aDB_Name, null, aDB_VERSION);
    private static SQLiteDatabase mDB;

    private cDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mLogger.logInfo("creating DB Master");
        mLogger.logDebug(Environment.getExternalStorageDirectory().getPath());
        init();
    }

    private void init(){
        mDB = this.getReadableDatabase();

    }

    /**
     * TODO make protected, after removing the moving of the debug coping of DB
     * @return
     */
    public static cDBHelper getInstance(){
        return mMasterInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cNoteLogger.getInstance().logDebug("Database Created");
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

    private void rawSQL(String command, String[] selectionArgs){
        cNoteLogger.getInstance().logWarning("raw SQL on DB: <" + command + ">");
        mDB.execSQL(command);
    }

    void deleteAndReinit(){
        rawSQL(SQL_DELETE_ENTRIES, null);
        rawSQL(SQL_CREATE_ENTRIES, null);
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
            throw new cDBMasterException("DB Master open",cDBMasterException.aSQL_CONNECTION_ALREADY_OPEN,null);
        }else{
            mDB = mMasterInstance.getReadableDatabase();
        }
    }
    /**
     * closes the DB-connection
     */
    public void closeDB(){
        mLogger.logInfo("closing DB");
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
