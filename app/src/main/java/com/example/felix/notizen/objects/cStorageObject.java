package com.example.felix.notizen.objects;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.Utils.DateStrategy;
import com.example.felix.notizen.Utils.OnUpdateCallback;
import com.example.felix.notizen.objects.filtersort.SortAble;
import com.example.felix.notizen.objects.filtersort.SortCategory;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * object handling storing of objects
 * also handles state of the object (has it been saved since last data change?)
 */
public abstract class cStorageObject extends cIdObject implements DatabaseStorable {

    public cStorageObject(UUID mID, String mTitle) {
        super(mID, mTitle);
        initSortables();

        // set creation and last changed dates
        setCreationDate();
        setLastChangedDate();
    }

    public cStorageObject() {
        super();
        initSortables();

        // set creation and last changed dates
        setCreationDate();
        setLastChangedDate();
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
        this.addSortable(SortCategory.LAST_CHANGE_TIME, new SortAble<Long>() {
            @Override
            public Long getData() {
                return getLastChangedDate();
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
    public String getTitle() {
        return super.getTitle();
    }

    /**
     * to be called, if the data in your implementation have been changed.
     * e.g. due to changes by the user
     */
    protected void onDataChanged(){
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
     * @see DateStrategy#getCurrentTime()
     */
    @JsonProperty("creationDate")
    private long mCreationDate = -1;
    /**
     * date of last change, numbers of milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     */
    @JsonProperty("lastChangedDate")
    private long mLastChangedDate = -1;

    /**
     * sets last changed date to current time in milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     */
    public void setLastChangedDate(){
        // TODO do I need to onDataChanged here as well?
        mLastChangedDate = DateStrategy.getCurrentTime();
    }

    /**
     * gets last changed date in milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     * @return last changed date
     */
    public long getLastChangedDate(){
        return mLastChangedDate;
    }

    /**
     * gets creation date of the note in milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     * @return creation date
     */
    public long getCreationDate(){
        return mCreationDate;
    }

    /**
     * set creation date to current time in milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     */
    protected void setCreationDate(){
        mCreationDate = DateStrategy.getCurrentTime();
        onDataChanged();
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
        updateData();
    }
}
