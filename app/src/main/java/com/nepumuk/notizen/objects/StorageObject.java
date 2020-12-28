package com.nepumuk.notizen.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nepumuk.notizen.objects.filtersort.SortCategory;
import com.nepumuk.notizen.utils.DateStrategy;
import com.nepumuk.notizen.utils.db_access.DatabaseStorable;

import java.util.UUID;

/**
 * object handling storing of objects
 * also handles state of the object (has it been saved since last data change?)
 */
public abstract class StorageObject extends IdObject implements DatabaseStorable {

    public StorageObject(UUID mID, String mTitle) {
        super(mID, mTitle);
        initSortables();

        // set creation and last changed dates
        setCreationDate();
        setLastChangedDate();
    }

    public StorageObject() {
        super();
        initSortables();

        // set creation and last changed dates
        setCreationDate();
        setLastChangedDate();
    }

    private void initSortables(){
        this.addSortable(SortCategory.TITLE, this::getTitle);
        this.addSortable(SortCategory.CREATION_TIME, this::getCreationDate);
        this.addSortable(SortCategory.LAST_CHANGE_TIME, this::getLastChangedDate);
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

    @Override
    public void setTitle(String pTitle){
        super.setTitle(pTitle);
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
    }

}
