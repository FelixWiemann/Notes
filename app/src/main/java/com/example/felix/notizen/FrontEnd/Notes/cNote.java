package com.example.felix.notizen.FrontEnd.Notes;

import java.util.Date;

/**
 * Created by felix on 13/04/17.
 * base class for Notes
 */
@SuppressWarnings("unused")
public abstract class cNote {

    /**
     * ID string of current note, used to identify each note
     */
    private String mID;
    /**
     * title of the note
     */
    private String mTitle;
    /**
     * date of creation, numbers of milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    private long mCreationDate;
    /**
     * date of last change, numbers of milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    private long mLastChangedDate;


    /**
     * create new note
     * @param pID id of note
     * @param pTitle title of note
     */
    cNote(String pID, String pTitle){
        //  create a note, with given ID and Title
        setId(pID);
        setTitle(pTitle);
        // set creation and last changed dates
        setCreationDate();
        setLastChangedDate();
    }

    /**
     * create new note
     * able to determine whether it is a previously stored note, to not change creation date and
     * last modified date
     *
     * @param pID id of Note
     * @param pTitle title of note
     * @param pExistingNote whether is existing node
     *                      true -> no new creation/last changed date
     *                      false -> new creation/last changed date
     */
    cNote(String pID, String pTitle, boolean pExistingNote){
        setId(pID);
        setTitle(pTitle);
        // only set dates, if not existing note
        // if exists (in DB or copy of other note, do NOT change
        if (!pExistingNote){
            setCreationDate();
            setLastChangedDate();
        }
    }

    /**
     * sets the title of the note
     * @param pTitle new title
     */
    private void setTitle(String pTitle){
        mTitle = pTitle;
    }

    /**
     * sets the id of the note
     * TODO prevent illegal manipulation on runtime on existing notes!
     * @param pID new id
     */
    private void setId(String pID){
        mID = pID;
    }

    /**
     * sets last changed date to current time in milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    void setLastChangedDate(){
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
     * gets the id o the note
     * @return id
     */
    public String getID(){
        return mID;
    }

    /**
     * gets the title of the note
     * @return title
     */
    public String getTitle(){
        return mTitle;
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
    private void setCreationDate(){
        mCreationDate = (new Date()).getTime();
    }


    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    public abstract void deleteNote();


    /**
     * add additional data to this note
     * pDataBlob may contain any kind of data, determined by type of note
     * @param pDataBlob contains the Data to add, in case of these classes:</p>
     *                  cImageNote: string containing location of Image
     *                  cTextNote: string containing the message of the note
     *                  cTaskNote: List containing all tasks of the note
     */
    public abstract void addAdditionalData(Object pDataBlob);



}
