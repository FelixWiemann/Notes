package com.example.felix.notizen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felix "nepumuk" Wiemann on 28.01.2016
 * as part of Notizen
 *
 * Contract-class for managing the SQL-Database used for storing
 * all {@link com.example.felix.notizen.Note} and
 * all {@link com.example.felix.notizen.Note_Category} categories
 */
public class SQLManagerContract extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION=14;
    private static final String LOG_TAG = "SQL_MANAGER_CONTRACT" ;
    private Context context;

    /**
     * Constructor for accessing the database in the Contract
     * @param context of application/package
     */
    public SQLManagerContract(Context context){
        super(context, DB_Names.NOTE.SQL_DataBase_Name, null, DATABASE_VERSION);
        this.context=context;
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
       // db = this.getWritableDatabase();
    }

    /**
     * Creating database tables
     * @param db database to create
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        //create notes table
        db.execSQL(SQL_Commands.SQL_Notes_Create_Entries);
        //create categories entry
        db.execSQL(SQL_Commands.SQL_Categories_Create_Entries);
        ContentValues values = Note_CategoryToContentValues(new Note_Category());
        // Inserting Row
        db.insert(DB_Names.CATEGORY.SQL_TableNameCategories, null, values);
    }

    /**
     * upgrading the db
     * @param db db to upgrade
     * @param oldVersion old version number of db
     * @param newVersion new version number of db
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        List<Note> notes = getAllNotes();
        List<Note_Category> note_categories = getAllCategories();
        // Drop older table if existed
        db.execSQL(SQL_Commands.SQL_DELETE_NOTES_DB);
        db.execSQL(SQL_Commands.SQL_DELETE_CATEGORY_DB);
        // Create tables again
        onCreate(db);
        for (Note n:notes
             ) {
            addNote(n);
        }
        for (Note_Category nc:note_categories
             ) {
            addCategory(nc);
        }

    }

    /**
     * adds a {@link com.example.felix.notizen.Note} to the database
     * @param note to add to the database
     */
    public void addNote(Note note) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        // open database of application
        SQLiteDatabase db = this.getWritableDatabase();
        // write note to contentvalues
        ContentValues values = NoteToContentValues(note);
        //insert row of data
        db.insert(DB_Names.NOTE.SQL_DataBase_Name, null, values);
        db.close(); // Closing database connection
    }

    /**
     * adds a {@link com.example.felix.notizen.Note_Category} to the database
     * @param note_category to add to the database
     */
    public void addCategory(Note_Category note_category) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        // open db
        SQLiteDatabase db = this.getWritableDatabase();
        // write {@link com.example.felix.notizen.Note_Category} to contentValues
        ContentValues values = Note_CategoryToContentValues(note_category);
        // Inserting Row
        db.insert(DB_Names.CATEGORY.SQL_TableNameCategories, null, values);

        db.close(); // Closing database connection
    }

    /** Query string for getting a single {@link com.example.felix.notizen.Note}
     * ID|Name|Content|DateCreated|DateLastChanged|NoteImportance|IsTask|Done|DateDue|NoteCategory
     */
    private String[] query_Notes_String = {DB_Names.NOTE.SQL_Table_ColumnName_ID, DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteName,
            DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteContent, DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDateCreated,
            DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDateLastChanged, DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteImportance,
            DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteIsTask, DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDone,
            DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDateDue, DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteCategory};
    /**
     * Query string for getting a single {@link com.example.felix.notizen.Note_Category}
     * ID|CatName|CatColor|CatDesq
     */
    private String[] query_Category_String={DB_Names.NOTE.SQL_Table_ColumnName_ID, DB_Names.CATEGORY.SQL_Table_Cat_ColumName_CategoryName,
            DB_Names.CATEGORY.SQL_Table_Cat_ColumName_CategoryColor, DB_Names.CATEGORY.SQL_Table_Cat_ColumName_CategoryDescription};


    /**
     * get a single {@link com.example.felix.notizen.Note} from the database
     * @param id of note to return
     * @return note with the given ID
     */
    @SuppressWarnings("unused")
    public Note getNote(int id) throws IllegalArgumentException{
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        //open db
        SQLiteDatabase db = this.getReadableDatabase();
        // Order: ID  Name  Content  DateCreated  DateLastChanged  DateImportance  NoteIsTask  NoteDone  NoteDateDue
        // TODO correct querystring to include notecategory
        Cursor cursor = db.query(DB_Names.NOTE.SQL_DataBase_Name, query_Notes_String, DB_Names.NOTE.SQL_Table_ColumnName_ID+"=?",new String[] { String.valueOf(id) },null,null,null,null);
        // if cursor == null, dataset not available
        if (cursor != null)
            cursor.moveToFirst();
        else throw new IllegalArgumentException("invalid ID");
        // create note from cursor
        Note note = CursorToNote(cursor);
        db.close();
        return note; //return Note

    }

    /**
     * get a single {@link com.example.felix.notizen.Note_Category} from the database
     * @param id of note_category to retugn
     * @return note_category with given ID
     */
    public Note_Category getNoteCategory(int id){
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        // open db
        SQLiteDatabase db = this.getReadableDatabase();
        // Order: ID  name color description
        Cursor cursor = db.query(DB_Names.CATEGORY.SQL_TableNameCategories, query_Category_String, DB_Names.NOTE.SQL_Table_ColumnName_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else throw new IllegalArgumentException("invalid ID");
        db.close();
        return CursorToCategory(cursor);
    }


    /**
     * converts a database cursor into a {@link com.example.felix.notizen.Note_Category}
     * @param cursor to convert to note_category
     * @return note_category described by given cursor
     */
    @NonNull
    private Note_Category CursorToCategory(Cursor cursor){
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        Note_Category note_category=new Note_Category();
        try{
            // write all cursor-data in category
            note_category.setM_ID(cursor.getInt(0));
            note_category.setM_CatName(cursor.getString(1));
            note_category.setM_CatDesc(cursor.getString(3));
            note_category.setM_CatColor(cursor.getInt(2));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return note_category;
    }


    /**
     * convert database cursor to {@link com.example.felix.notizen.Note}
     * @param cursor to convert
     * @return note described by given cursor
     */
    @NonNull
    private Note CursorToNote(Cursor cursor) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        Note note = new Note();
        try {
            // write all data from cursor into the note
            // since reading from cursor that comes from db, no need to update DB
            note.set_ID(cursor.getInt(0));
            note.setNoteName(cursor.getString(1), false);
            note.setNoteText(cursor.getString(2), false);
            note.setNoteCreatedDate(cursor.getInt(3), false);
            note.setNoteLastChangedDate(cursor.getInt(4), false);
            note.setNoteImportance(cursor.getInt(5), false);
            note.setNoteIsTask(SQL_Commands.IntToBool(cursor.getInt(6)), false);
            note.setTaskDone(SQL_Commands.IntToBool(cursor.getInt(7)), false);
            note.setTaskDueDate(cursor.getInt(8), false);
            //note.NoteCategory=getNoteCategory(cursor.getInt(9));
            note.setNoteCategory(new Note_Category(cursor.getString(12), cursor.getString(14), cursor.getInt(13), cursor.getInt(11)), false);
            // 10 complete data; 11 cat id; 12 cat name; 13 cat color; 14 car description
        } catch (Exception e) {
            e.printStackTrace();
        }
        return note;
    }

    /**
     * create ContentValues from a {@link com.example.felix.notizen.Note} to write it in the database
     * @param note to convert
     * @return content values describing the converted note
     */
    @NonNull
    private ContentValues NoteToContentValues(Note note){
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        // Order: ID  Name  Content  DateCreated  DateLastChanged  DateImportance  NoteIsTask  NoteDone  NoteDateDue
        // DO NOT WRITE ID, OR DATABASE WILL CORRUPT
        ContentValues values = new ContentValues();
        values.put(DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteName, note.getNoteName());
        values.put(DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteContent, note.getNoteText());
        values.put(DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDateCreated, note.getNoteCreatedDate());
        values.put(DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDateLastChanged, note.getNoteLastChangedDate());
        values.put(DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteImportance, note.getNoteImportance());
        values.put(DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteIsTask, note.isNoteIsTask());
        values.put(DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDone, note.isTaskDone());
        values.put(DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDateDue, note.getTaskDueDate());
        values.put(DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteCategory, note.getNoteCategory().getM_ID());
        return values;
    }

    /**
     * create ContentValues from a {@link com.example.felix.notizen.Note_Category} to write it in the database
     * @param note_category to convert
     * @return content values describing the converted note
     */
    @NonNull
    private ContentValues Note_CategoryToContentValues(Note_Category note_category){
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        ContentValues values = new ContentValues();
        //values.put(DB_Names.SQL_Table_ColumnName_ID,note.get_ID()); DO NOT WRITE ID, OR DATABASE WILL CORRUPT
        values.put(DB_Names.CATEGORY.SQL_Table_Cat_ColumName_CategoryName,note_category.getM_CatName());
        values.put(DB_Names.CATEGORY.SQL_Table_Cat_ColumName_CategoryDescription,note_category.getM_CatDesc());
        values.put(DB_Names.CATEGORY.SQL_Table_Cat_ColumName_CategoryColor, note_category.getM_CatColor());
        return values;
    }

    /**
     * Getting all {@link com.example.felix.notizen.Note}s stored in the database in one list
     * @return list of all notes stored in the database
     */
    public List<Note> getAllNotes(){
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        // List to store all notes in
        List<Note> noteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DB_Names.NOTE.SQL_DataBase_Name+" JOIN "+ DB_Names.CATEGORY.SQL_TableNameCategories +" ON "+ DB_Names.NOTE.SQL_DataBase_Name+"."+ DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteCategory +" = "+ DB_Names.CATEGORY.SQL_TableNameCategories+"."  + DB_Names.NOTE.SQL_Table_ColumnName_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Alarm a = new Alarm();
        Note note;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // convert cursor to note
                note=CursorToNote(cursor);
                // Adding contact to list
//d                a.SetAlarm(note);
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return noteList;
    }

    /**
     * Getting all {@link com.example.felix.notizen.Note_Category}s stored in the database in one list
     * @return List containing all categories stored in the database
     */
    public List<Note_Category> getAllCategories(){
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        List<Note_Category> noteCategoryList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DB_Names.CATEGORY.SQL_TableNameCategories;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Note_Category notecat;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                notecat=CursorToCategory(cursor);
                // Adding contact to list
                noteCategoryList.add(notecat);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return noteCategoryList;
    }

    /**
     * get the count of notes stored in the db
     * @return number of notes
     */
    @SuppressWarnings("unused")
    public int getNotesCount() {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        String countQuery = "SELECT  * FROM " + DB_Names.NOTE.SQL_DataBase_Name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    /**
     * updating a single note stored in the database
     * @param note to update
     * @return some int describing update status
     */
    public int updateNote(Note note) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName()+": "+note.get_ID());
        SQLiteDatabase db = this.getWritableDatabase();
        String Filter = DB_Names.NOTE.SQL_Table_ColumnName_ID+"="+note.get_ID();
        db.update(DB_Names.NOTE.SQL_DataBase_Name, NoteToContentValues(note), Filter, null);
        int i = db.update(DB_Names.NOTE.SQL_DataBase_Name, NoteToContentValues(note), DB_Names.NOTE.SQL_Table_ColumnName_ID + " = ?",
                new String[]{String.valueOf(note.get_ID())});
        db.close();
        return i;
    }

    /**
     * delete a single note
     * @param note to delete
     */
    public void deleteNote(Note note) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        SQLiteDatabase db = this.getWritableDatabase();
        // find note by id, delete it
        db.delete(DB_Names.NOTE.SQL_DataBase_Name, DB_Names.NOTE.SQL_Table_ColumnName_ID + " = ?",
                new String[]{String.valueOf(note.get_ID())});
        db.close();
    }

    /**
     * delete a note category
     * @param note_category to delete
     */
    public void deleteNote_Category(Note_Category note_category) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        if (note_category.getM_ID() != 1) { // Cannot Delete Default Category
        SQLiteDatabase db = this.getWritableDatabase();

        // find cat to delete by id
        db.delete(DB_Names.CATEGORY.SQL_TableNameCategories, DB_Names.NOTE.SQL_Table_ColumnName_ID + " = ?",
                new String[] { String.valueOf(note_category.getM_ID()) });
        db.close();
        }else{
            Toast.makeText(context,"",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * class containing all strings needed for interacting with the database
     */
    public static class DB_Names implements BaseColumns{
        public static class CATEGORY{

            public static final String SQL_TableNameCategories ="CATEGORY_TABLE ";
            public static final String SQL_Table_Cat_ColumName_CategoryName="CATEGORY_NAME ";
            public static final String SQL_Table_Cat_ColumName_CategoryColor="CATEGORY_COLOR ";
            public static final String SQL_Table_Cat_ColumName_CategoryDescription="CATEGORY_DESCRIPTION ";
        }
        public static class NOTE{

            public static final String SQL_DataBase_Name="NOTES_DATABASE ";
            public static final String SQL_Table_ColumnName_ID="_id ";
            public static final String SQL_Table_Notes_ColumnName_NoteName ="NOTE_NAME ";
            public static final String SQL_Table_Notes_ColumnName_NoteDateCreated ="NOTE_DATE_CREATED ";
            public static final String SQL_Table_Notes_ColumnName_NoteDateLastChanged ="NOTE_DATE_LASTCHANGED ";
            public static final String SQL_Table_Notes_ColumnName_NoteDateDue ="NOTE_DATE_DUE ";
            public static final String SQL_Table_Notes_ColumnName_NoteContent ="NOTE_CONTENT ";
            public static final String SQL_Table_Notes_ColumnName_NoteDone ="NOTE_DONE ";
            public static final String SQL_Table_Notes_ColumnName_NoteImportance ="NOTE_IMPORTANCE ";
            public static final String SQL_Table_Notes_ColumnName_NoteIsTask ="NOTE_IS_TASK ";
            public static final String SQL_Table_Notes_ColumnName_TaskCompleteDue ="Note_Complete_Date ";
            public static final String SQL_Table_Notes_ColumnName_NoteCategory="NOTE_CATEGORY_ID ";
        }

    }

    /**
     * class containing all SQL-Commands
     */
    public static class SQL_Commands{
        private static final String TextType="TEXT ";
        private static final String CommaSep=", ";
        private static final String IntegerPrimaryKey=" INTEGER PRIMARY KEY ";
        private static final String IntegerType="INTEGER ";

        private static final String SQL_Create_Table="CREATE TABLE ";
        private static final String SQL_Drop_Table_if_Exists="DROP TABLE IF EXISTS ";

        /**
         * Converts integer to boolean, sqlite does not have bool. 0 = false 1 = true
         * @param in int to convert
         * @return boolean
         * @throws Exception
         */
        public static boolean IntToBool(int in) throws Exception {
            switch (in) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    return false;
                    //throw new Exception("Value has to be 1 or 0");
            }
        }

        /**
         * Converts boolean to integer, sqlite does not have bool. 0 = false 1 = true
         * @param in boolean to convert
         * @return integer value of boolean
         */
        @SuppressWarnings("unused")
        public static int BoolToInt(boolean in){
            if (in) {
                return 1;
            }
            else {
                return 0;
            }
        }

        // Order: ID  Name  Content  DateCreated  DateLastChanged  DateImportance  NoteIsTask  NoteDone  NoteDateDue
        public static final String SQL_Notes_Create_Entries =
                SQL_Commands.SQL_Create_Table+ DB_Names.NOTE.SQL_DataBase_Name+"("+
                        DB_Names.NOTE.SQL_Table_ColumnName_ID+SQL_Commands.IntegerPrimaryKey+SQL_Commands.CommaSep+
                        DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteName +SQL_Commands.TextType+SQL_Commands.CommaSep+
                        DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteContent +SQL_Commands.TextType+SQL_Commands.CommaSep+
                        DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDateCreated +SQL_Commands.TextType+SQL_Commands.CommaSep+
                        DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDateLastChanged +SQL_Commands.TextType+SQL_Commands.CommaSep+
                        DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteImportance +SQL_Commands.IntegerType+SQL_Commands.CommaSep+
                        DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteIsTask +SQL_Commands.IntegerType+SQL_Commands.CommaSep+
                        DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDone +SQL_Commands.IntegerType+SQL_Commands.CommaSep+
                        DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteDateDue +SQL_Commands.TextType+SQL_Commands.CommaSep+
                        DB_Names.NOTE.SQL_Table_Notes_ColumnName_NoteCategory +SQL_Commands.IntegerType+SQL_Commands.CommaSep+
                        DB_Names.NOTE.SQL_Table_Notes_ColumnName_TaskCompleteDue +SQL_Commands.TextType+
                        ");";

        public static final String SQL_Categories_Create_Entries =
                SQL_Commands.SQL_Create_Table+ DB_Names.CATEGORY.SQL_TableNameCategories +"("+
                        DB_Names.NOTE.SQL_Table_ColumnName_ID+SQL_Commands.IntegerPrimaryKey+SQL_Commands.CommaSep+
                        DB_Names.CATEGORY.SQL_Table_Cat_ColumName_CategoryName+SQL_Commands.TextType+SQL_Commands.CommaSep+
                        DB_Names.CATEGORY.SQL_Table_Cat_ColumName_CategoryColor+SQL_Commands.IntegerType+SQL_Commands.CommaSep+
                        DB_Names.CATEGORY.SQL_Table_Cat_ColumName_CategoryDescription+SQL_Commands.TextType
                        +") ;";

        private static final String SQL_DELETE_NOTES_DB =
                SQL_Commands.SQL_Drop_Table_if_Exists + DB_Names.NOTE.SQL_DataBase_Name;
        private static final String SQL_DELETE_CATEGORY_DB =
                SQL_Commands.SQL_Drop_Table_if_Exists + DB_Names.CATEGORY.SQL_TableNameCategories;


    }

}
