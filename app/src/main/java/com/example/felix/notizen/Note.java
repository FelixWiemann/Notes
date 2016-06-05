package com.example.felix.notizen;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Date;

/**
 * Created by Felix "nepumuk" Wiemann on 26.01.2016
 * as part of Notizen
 *
 */
public class Note extends AppCompatActivity implements Parcelable {
    private static final String LOG_TAG = "Note";

    public SQLManagerContract getSqlManagerContract() {
        return sqlManagerContract;
    }

    public void setSqlManagerContract(SQLManagerContract sqlManagerContract) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        this.sqlManagerContract = sqlManagerContract;
    }

    public Note_Category getNoteCategory() {
        return NoteCategory;
    }

    public void setNoteCategory(Note_Category noteCategory) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteCategory = noteCategory;
    }

    public boolean isFirstTimeIdAsigned() {
        return firstTimeIdAsigned;
    }

    public void setFirstTimeIdAsigned(boolean firstTimeIdAsigned) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        this.firstTimeIdAsigned = firstTimeIdAsigned;
    }

    public long getTaskCompletedDate() {
        return TaskCompletedDate;
    }

    public void setTaskCompletedDate(long taskCompletedDate) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        TaskCompletedDate = taskCompletedDate;
    }

    public long getNoteLastChangedDate() {
        return NoteLastChangedDate;
    }

    public void setNoteLastChangedDate(long noteLastChangedDate) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteLastChangedDate = noteLastChangedDate;
    }

    public long getNoteCreatedDate() {
        return NoteCreatedDate;
    }

    public void setNoteCreatedDate(long noteCreatedDate) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteCreatedDate = noteCreatedDate;
    }

    public long getTaskDueDate() {
        return TaskDueDate;
    }

    public void setTaskDueDate(long taskDueDate) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        TaskDueDate = taskDueDate;
    }

    public String getNoteName() {
        return NoteName;
    }

    public void setNoteName(String noteName) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteName = noteName;
    }

    public boolean isTaskDone() {
        return TaskDone;
    }

    public void setTaskDone(boolean taskDone) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        setTaskCompletedDate((new Date()).getTime());
        TaskDone = taskDone;
    }

    public boolean isNoteIsTask() {
        return NoteIsTask;
    }

    public void setNoteIsTask(boolean noteIsTask) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteIsTask = noteIsTask;
    }

    public int getNoteImportance() {
        return NoteImportance;
    }

    public void setNoteImportance(int noteImportance) {
        if (!firstCreated) {
            NoteLastChangedDate = (new Date()).getTime();
        }
        NoteImportance = noteImportance;
    }

    public String getNoteText() {
        return NoteText;
    }

    public void setNoteText(String noteText) {
        if (!firstCreated) {
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
        sqlManagerContract = sql;
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        sqlManagerContract.updateNote(this);
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
