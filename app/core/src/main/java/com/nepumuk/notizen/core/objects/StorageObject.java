package com.nepumuk.notizen.core.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nepumuk.notizen.core.filtersort.SortCategory;
import com.nepumuk.notizen.core.utils.DateStrategy;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;

/**
 * object handling storing of objects
 * also handles state of the object (has it been saved since last data change?)
 */
public abstract class StorageObject extends IdObject implements DatabaseStorable {

    public StorageObject(String mID, String mTitle) {
        super(mID, mTitle);
        initSortables();

        // set creation and last changed dates
        setCreationDate();
        setLastChangedDate();
    }
    /**
     * copy constructor
     *
     * makes a deep clone of the given object
     * @param other to copy from
     */
    public StorageObject(StorageObject other) {
        super(other);
        this.creationDate = other.creationDate;
        this.lastChangedDate = other.lastChangedDate;
        initSortables();
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
        return this.getID();
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
    private long creationDate = -1;
    /**
     * date of last change, numbers of milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     */
    @JsonProperty("lastChangedDate")
    private long lastChangedDate = -1;

    /**
     * sets last changed date to current time in milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     */
    public void setLastChangedDate(){
        lastChangedDate = DateStrategy.getCurrentTime();
    }

    /**
     * setter for room DB
     */
    public void setLastChangedDate(long lastChangedDate){
        this.lastChangedDate = lastChangedDate;
    }

    /**
     * setter for room DB
     */
    public void setCreationDate(long creationDate){
        this.creationDate = creationDate;
    }

    /**
     * gets last changed date in milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     * @return last changed date
     */
    public long getLastChangedDate(){
        return lastChangedDate;
    }

    /**
     * gets creation date of the note in milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     * @return creation date
     */
    public long getCreationDate(){
        return creationDate;
    }

    /**
     * set creation date to current time in milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     */
    protected void setCreationDate(){
        creationDate = DateStrategy.getCurrentTime();
    }

}
