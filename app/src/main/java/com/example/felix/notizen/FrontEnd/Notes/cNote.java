package com.example.felix.notizen.FrontEnd.Notes;

import java.util.Date;

/**
 * Created by felix on 13/04/17.
 * base class for Notes
 */
public class cNote {

    private String mID;
    private String mTitle;
    private long mCreationDate;
    private long mLastChangedDate;


    /**
     * create new Note
     * @param pID id of Note
     * @param pTitle title of Note
     */
    public cNote(String pID, String pTitle){
        //  create a note, with given ID and Title
        setId(pID);
        setTitle(pTitle);
        // set creation and last changed dates
        setCreationDate();
        setLastChangedDate();
    }

    /**
     * create new Note
     * @param pID id of Note
     * @param pTitle title of note
     * @param pExistingNote whether is existing node
     *                      true -> no new creation/last changed date
     *                      false -> new creation/lastchanged date
     */
    public cNote(String pID,String pTitle, boolean pExistingNote){
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
     * sets the title
     * @param pTitle new title
     */
    private void setTitle(String pTitle){
        mTitle = pTitle;
    }

    /**
     * sets the id
     * @param pID new id
     */
    private void setId(String pID){
        mID = pID;
    }

    /**
     *  sets last changed date to current time
     */
    private void setLastChangedDate(){
        mLastChangedDate = (new Date()).getTime();
    }

    /**
     * gets last changed date
     * @return last changed date
     */
    public long getLastChangedDate(){
        return mLastChangedDate;
    }

    /**
     * gets the id
     * @return id
     */
    public String getID(){
        return mID;
    }

    /**
     * gets the title
     * @return title
     */
    public String getTitle(){
        return mTitle;
    }

    /**
     * gets creation date
     * @return creation date
     */
    public long getCreationDate(){
        return mCreationDate;
    }

    /**
     * set creation date to current time
     */
    public void setCreationDate(){
        mCreationDate = (new Date()).getTime();
    }



}
