package com.example.felix.notizen.objects;

import android.util.Log;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.Utils.OnUpdateCallback;
import com.example.felix.notizen.Utils.cBaseException;
import com.example.felix.notizen.objects.Notes.cNoteException;
import com.example.felix.notizen.views.viewsort.SortAble;
import com.example.felix.notizen.views.viewsort.SortCategory;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

/**
 * object handling storing of objects
 * also handles state of the object (has it been saved since last data change?)
 */
public abstract class cStorageObject extends cIdObject implements DatabaseStorable {

    /**
     * if it has been saved since last edit
     * true if yes, false otherwise
     */
    private boolean wasSaved;


    public cStorageObject(UUID mID, String mTitle) {
        super(mID, mTitle);
        initSortables();
    }

    public cStorageObject() {
        super();
        initSortables();
        wasSaved = false;
    }

    private void initSortables(){
        this.addSortable(SortCategory.TITLE, new SortAble<String>() {
            @Override
            public String getData() {
                return getTitle();
            }
        });

        this.addSortable(SortCategory.CREATION_TIME, new SortAble<Long>() {
            @Override
            public Long getData() {
                return getCreationDate();
            }
        });
    }

    @Override
    public abstract int getVersion();

    @Override
    public String getDataString() {
        return this.toJson();
    }

    @Override
    public String getType() {
        return getClass().getCanonicalName();
    }

    @Override
    public String getId() {
        return this.getIdString();
    }

    @Override
    public boolean wasUpdatedSinceLastSave() {
        return wasSaved;
    }

    @Override
    public void onSave() {
        wasSaved = true;
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    /**
     * to be called, if the data in your implementation have been changed.
     * e.g. due to changes by the user
     */
    public void onDataChanged(){
        wasSaved = false;
        updateData();
    }

    /**
     * update all depending listeners
     */
    public void updateData(){
        if (callback != null) {
            callback.update();
        }
    }

    @Override
    public void setTitle(String pTitle){
        super.setTitle(pTitle);
        onDataChanged();
    }

    /**
     * date of creation, numbers of milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    @JsonProperty("creationDate")
    private long mCreationDate = -1;
    /**
     * date of last change, numbers of milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    @JsonProperty("lastChangedDate")
    private long mLastChangedDate = -1;

    /**
     * sets last changed date to current time in milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    public void setLastChangedDate(){
        // TODO do I need to onDataChanged here as well?
        mLastChangedDate = (new Date()).getTime();
    }

    /**
     * gets last changed date in milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     * @return last changed date
     */
    public long getLastChangedDate(){
        return mLastChangedDate;
    }

    /**
     * gets creation date of the note in milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     * @return creation date
     */
    public long getCreationDate(){
        return mCreationDate;
    }

    /**
     * set creation date to current time in milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    protected void setCreationDate(){
        mCreationDate = (new Date()).getTime();
        onDataChanged();
    }

    /**
     *
     * @param pCreationDate
     * @throws Exception
     */
    public void setCreationDate(long pCreationDate) throws cBaseException {
        // must be -1 to be set, otherwise it was created before
        if (mCreationDate == -1){
            mCreationDate = pCreationDate;
            onDataChanged();
        }
        else {
            throw new cNoteException("cNote setCreationDate",cNoteException.aCREATION_DATE_ALREADY_SET,null);
        }

    }

    /**
     * TODO what do I need this for?
     * JSON Setter?
     * @param lastChangeDate
     */
    public void setLastChangeDate(long lastChangeDate) {
        mLastChangedDate = lastChangeDate;
    }


    // TODO probably remove and put somewhere display related
    private OnUpdateCallback callback;

    /**
     * set the listener for updates and immediately informs him of the current state
     * @param onChangeListener new listener
     */
    public void setOnChangeListener(OnUpdateCallback onChangeListener) {
        callback = onChangeListener;
        // make sure callback already knows sth has changed
        callback.update();
    }
}
