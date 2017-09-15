package com.example.felix.notizen.BackEnd.DBAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.felix.notizen.BackEnd.Logger.cNoteLogger;
import com.example.felix.notizen.BackEnd.cContextManager;
import com.example.felix.notizen.FrontEnd.cIdObject;

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
@SuppressWarnings("unused")
@Deprecated
public class cDBMaster extends SQLiteOpenHelper {

    //Database Version
    private static final String aLOG_TAG = "SQL_MANAGER_CONTRACT" ;

    private static final int aDB_VERSION = 1;

    private cNoteLogger mLogger = cNoteLogger.getInstance();
    private static final String aDB_Name = "Notes";
    private static final String aDB_TABLE_NAME = "entries";
    private static final String aDB_COLUMN_ID = "ID";
    private static final String aDB_COLUMN_TYPE = "Type";



    private static cDBMaster mMasterInstance = new cDBMaster(cContextManager.getInstance().getContext(), aDB_Name, null, aDB_VERSION);
    private static SQLiteDatabase mDB;// = mMasterInstance.getReadableDatabase();

    private cDBMaster(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mLogger.logInfo("creating DB Master");
//        mDB = this.getReadableDatabase();
    }

    public void init(){
        mDB = this.getReadableDatabase();
    }

    public static cDBMaster getInstance(){
        return mMasterInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * inserts an cIDObject into the DB
     * @param object to insert
     */
    public void insert(cIdObject object){
        mLogger.logInfo("inserting " + object.getIdString());
        ContentValues contentValue = new ContentValues( );
        contentValue.put(aDB_COLUMN_ID,object.getIdString());
        contentValue.put(aDB_COLUMN_TYPE,object.aTYPE);
        mDB.insert(aDB_TABLE_NAME,null,contentValue);
    }


    public void read(){

    }

    /**
     * delete the object out of the DB
     * @param object object to delete
     */
    public void delete(cIdObject object){
        mLogger.logInfo("deleting " + object.getIdString());
        String selection = aDB_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {object.getIdString()};
        mDB.delete(aDB_TABLE_NAME,selection,selectionArgs);
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
                    aDB_COLUMN_TYPE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
           "DROP TABLE IF EXISTS " + aDB_TABLE_NAME;




}
