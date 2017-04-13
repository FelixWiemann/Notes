package com.example.felix.notizen.old_files.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.felix.notizen.old_files.SQLManagerContract;

import java.util.Date;

/**
 * Created by Felix "nepumuk" Wiemann on 26.01.2016
 * as part of Notizen
 *
 */
public class Note extends AppCompatActivity implements Parcelable {
    private static final String LOG_TAG = "Note";
    private boolean noteContentChanged = false;

    /**
     * returns the SQLManager of the note
     *
     * @return SQL Manager
     */
    public SQLManagerContract getSqlManagerContract() {
        return sqlManagerContract;
    }

    /**
     * sets the SQLManager for the note
     * @param sqlManagerContract new SQLManager
     */
    public void setSqlManagerContract(SQLManagerContract sqlManagerContract) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        this.sqlManagerContract = sqlManagerContract;
    }

    /**
     * returns the NoteCategory of the note
     * @return Category of note
     */
    public Note_Category getNoteCategory() {
        return NoteCategory;
    }

    /**
     * sets the Note Category
     *
     * @param noteCategory new Category
     * @param updateDB     whether to update DB (if changed by user) or not (if changed to match the db)
     */
    public void setNoteCategory(Note_Category noteCategory, boolean updateDB) {
        if (!firstCreated && !updateDB) {
            noteContentChanged = true;
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteCategory = noteCategory;
    }

    /**
     *
     * @return whether the ID of the Note is the asigned the first time
     */
    public boolean isFirstTimeIdAsigned() {
        return firstTimeIdAsigned;
    }

    /**
     * this should NEVER be changed
     * @param //firstTimeIdAsigned

    public void setFirstTimeIdAsigned(boolean firstTimeIdAsigned) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        this.firstTimeIdAsigned = firstTimeIdAsigned;
    }*/

    /**
     *
     * @return date when Note was marked done
     */
    public long getTaskCompletedDate() {
        return TaskCompletedDate;
    }

    /**
     * @param taskCompletedDate set date of completing the note
     * @param updateDB          whether to update DB (if changed by user) or not (if changed to match the db)
     */
    public void setTaskCompletedDate(long taskCompletedDate, boolean updateDB) {
        if (!firstCreated&&!updateDB) {
            noteContentChanged = true;
            NoteLastChangedDate = (new Date()).getTime();
        }
        TaskCompletedDate = taskCompletedDate;
    }

    /**
     *
     * @return date of last change
     */
    public long getNoteLastChangedDate() {
        return NoteLastChangedDate;
    }

    /**
     * set the last changeDate
     *
     * @param noteLastChangedDate date of last change
     * @param updateDB            whether to update DB (if changed by user) or not (if changed to match the db)
     */
    public void setNoteLastChangedDate(long noteLastChangedDate,boolean updateDB) {
        if (!firstCreated) {
            noteContentChanged = true;
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteLastChangedDate = noteLastChangedDate;
    }

    /**
     *
     * @return date of creation of the note
     */
    public long getNoteCreatedDate() {
        return NoteCreatedDate;
    }

    /**
     * @param noteCreatedDate date the note was created
     * @param updateDB        whether to update DB (if changed by user) or not (if changed to match the db)
     */
    public void setNoteCreatedDate(long noteCreatedDate, boolean updateDB) {
        if (!firstCreated) {
            noteContentChanged = true;
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteCreatedDate = noteCreatedDate;
    }

    public long getTaskDueDate() {
        return TaskDueDate;
    }

    /**
     * @param taskDueDate date the note is due
     * @param updateDB    whether to update DB (if changed by user) or not (if changed to match the db)
     */
    public void setTaskDueDate(long taskDueDate, boolean updateDB) {
        if (!firstCreated) {
            noteContentChanged = true;
            NoteLastChangedDate = (new Date()).getTime();
        }
        TaskDueDate = taskDueDate;
    }

    public String getNoteName() {
        return NoteName;
    }

    /**
     * @param noteName name of the note
     * @param updateDB whether to update DB (if changed by user) or not (if changed to match the db)
     */
    public void setNoteName(String noteName, boolean updateDB) {
        if (!firstCreated) {
            noteContentChanged = true;
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteName = noteName;
    }

    public boolean isTaskDone() {
        return TaskDone;
    }

    /**
     * @param taskDone task done?
     * @param updateDB whether to update DB (if changed by user) or not (if changed to match the db)
     */
    public void setTaskDone(boolean taskDone, boolean updateDB) {
        if (!firstCreated) {
            noteContentChanged = true;
            NoteLastChangedDate = (new Date()).getTime();
        }
        setTaskCompletedDate((new Date()).getTime(), updateDB);
        TaskDone = taskDone;
    }

    public boolean isNoteIsTask() {
        return NoteIsTask;
    }

    /**
     * @param noteIsTask whether the note is a task
     * @param updateDB   whether to update DB (if changed by user) or not (if changed to match the db)
     */
    public void setNoteIsTask(boolean noteIsTask, boolean updateDB) {
        if (!firstCreated) {
            noteContentChanged = true;
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteIsTask = noteIsTask;
    }

    public int getNoteImportance() {
        return NoteImportance;
    }

    /**
     * @param noteImportance importance of the note
     * @param updateDB       whether to update DB (if changed by user) or not (if changed to match the db)
     */
    public void setNoteImportance(int noteImportance, boolean updateDB) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
            noteContentChanged = true;
        }
        NoteImportance = noteImportance;
    }

    public String getNoteText() {
        return NoteText;
    }

    /**
     * @param noteText text of the note
     * @param updateDB whether to update DB (if changed by user) or not (if changed to match the db)
     */
    public void setNoteText(String noteText, boolean updateDB) {
        if (!firstCreated) {
            noteContentChanged = true;
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteText = noteText;
    }

    private String NoteText;
    private int NoteImportance;
    private boolean NoteIsTask;
    private boolean TaskDone;
    private String NoteName;
    private long TaskDueDate;
    private long NoteCreatedDate;
    private long NoteLastChangedDate;
    private int _ID;
    private long TaskCompletedDate;
    private boolean firstTimeIdAsigned = true;
    private Note_Category NoteCategory;
    private SQLManagerContract sqlManagerContract;
    private boolean firstCreated = true;

    @SuppressWarnings("unused")
    public Note(int _id, String noteName, String noteText, int noteImportance, boolean taskDone, boolean noteIsTask, int taskDueDate, long noteCreatedDate, int noteLastChangedDate, int taskCompletedDate, Note_Category noteCategory) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        NoteName=noteName;
        NoteText=noteText;
        NoteImportance=noteImportance;
        TaskDone=taskDone;
        NoteIsTask=noteIsTask;
        TaskDueDate=taskDueDate;
        NoteCreatedDate=noteCreatedDate;
        NoteLastChangedDate=noteLastChangedDate;
        _ID=_id;
        firstTimeIdAsigned=false;
        TaskCompletedDate=taskCompletedDate;
        NoteCategory=noteCategory;
        firstCreated = false;
    }
    public Note(){
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        NoteCategory = new Note_Category();
        sqlManagerContract = new SQLManagerContract(getBaseContext());
        NoteName="";
        NoteText="";
        NoteImportance=0;
        TaskDone=false;
        NoteIsTask=false;
        TaskDueDate=0;
        NoteCreatedDate = (new Date()).getTime();
        NoteLastChangedDate=0;
        firstTimeIdAsigned=true;
        TaskCompletedDate=0;
        firstCreated = false;
    }

    protected Note(Parcel in) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        NoteText = in.readString();
        NoteImportance = in.readInt();
        NoteIsTask = in.readByte() != 0;
        TaskDone = in.readByte() != 0;
        NoteName = in.readString();
        TaskDueDate = in.readLong();
        NoteCreatedDate = in.readLong();
        NoteLastChangedDate = in.readLong();
        _ID = in.readInt();
        TaskCompletedDate = in.readLong();
        firstTimeIdAsigned = in.readByte() != 0;
        NoteCategory = in.readParcelable(Note_Category.class.getClassLoader());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            return new Note[size];
        }
    };

    public int get_ID() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return _ID;
    }

    public void set_ID(int _ID) throws Exception {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        if(firstTimeIdAsigned){
            this._ID = _ID;
            firstTimeIdAsigned=false;
        }
        else throw new Exception("Can't change ID, Database could corrupt");
    }

    public void updateDB(SQLManagerContract sql)
    {
        // only update, if things changed
        if (noteContentChanged) {
        sqlManagerContract = sql;
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            sqlManagerContract.updateNote(this);
        }
        noteContentChanged = false;
    }

    @Override
    public int describeContents() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        dest.writeString(NoteText);
        dest.writeInt(NoteImportance);
        dest.writeByte((byte) (NoteIsTask ? 1 : 0));
        dest.writeByte((byte) (TaskDone ? 1 : 0));
        dest.writeString(NoteName);
        dest.writeLong(TaskDueDate);
        dest.writeLong(NoteCreatedDate);
        dest.writeLong(NoteLastChangedDate);
        dest.writeInt(_ID);
        dest.writeLong(TaskCompletedDate);
        dest.writeByte((byte) (firstTimeIdAsigned ? 1 : 0));
        if(NoteCategory == null)
            NoteCategory=new Note_Category();
        dest.writeParcelable(NoteCategory, PARCELABLE_WRITE_RETURN_VALUE);
    }
}
