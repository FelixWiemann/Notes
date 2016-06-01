package com.example.felix.notizen;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Felix "nepumuk" Wiemann on 26.01.2016
 * as part of Notizen
 *
 */
public class Note extends AppCompatActivity implements Parcelable {
    private static final String LOG_TAG = "Note";
    public String NoteText;
    public int NoteImportance;
    public boolean NoteIsTask;
    public boolean TaskDone;
    public String NoteName;
    public int TaskDueDate;
    public int NoteCreatedDate;
    public int NoteLastChangedDate;
    private int _ID;
    public int TaskCompletedDate;
    public boolean firstTimeIdAsigned=true;
    public Note_Category NoteCategory;
    private SQLManagerContract sqlManagerContract;

    @SuppressWarnings("unused")
    public Note(int _id,String noteName, String noteText,int noteImportance, boolean taskDone, boolean noteIsTask, int taskDueDate, int noteCreatedDate, int noteLastChangedDate, int taskCompletedDate, Note_Category noteCategory){
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
        NoteCreatedDate=0;
        NoteLastChangedDate=0;
        firstTimeIdAsigned=true;
        TaskCompletedDate=0;
    }

    protected Note(Parcel in) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        NoteText = in.readString();
        NoteImportance = in.readInt();
        NoteIsTask = in.readByte() != 0;
        TaskDone = in.readByte() != 0;
        NoteName = in.readString();
        TaskDueDate = in.readInt();
        NoteCreatedDate = in.readInt();
        NoteLastChangedDate = in.readInt();
        _ID = in.readInt();
        TaskCompletedDate = in.readInt();
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
        dest.writeInt(TaskDueDate);
        dest.writeInt(NoteCreatedDate);
        dest.writeInt(NoteLastChangedDate);
        dest.writeInt(_ID);
        dest.writeInt(TaskCompletedDate);
        dest.writeByte((byte) (firstTimeIdAsigned ? 1 : 0));
        if(NoteCategory == null)
            NoteCategory=new Note_Category();
        dest.writeParcelable(NoteCategory, PARCELABLE_WRITE_RETURN_VALUE);
    }
}
