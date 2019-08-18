package com.example.felix.notizen.objects.Notes;

import com.example.felix.notizen.Utils.cBaseException;
import com.example.felix.notizen.objects.cStorageObject;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

/**
 * Created by felix on 13/04/17.
 * base class for Notes
 */
@SuppressWarnings("unused")
public abstract class cNote extends cStorageObject {

    /**
     * identifier of class
     */
    public static String aTYPE = "cNote";

    /**
     * date of creation, numbers of milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    private long mCreationDate = -1;
    /**
     * date of last change, numbers of milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    @JsonProperty("lastChangedDate")
    private long mLastChangedDate = -1;

    /**
     * create new note
     * @param pID id of note
     * @param pTitle title of note
     */
    protected cNote(UUID pID, String pTitle){
        super(pID,pTitle);
        logDebug("creating new cNote");
        // set creation and last changed dates
        setCreationDate();
        setLastChangedDate();
        logDebug("cNote created");
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
    cNote(UUID pID, String pTitle, boolean pExistingNote){
        super(pID,pTitle);
        logDebug("creating preexisting cNote");
        // only set dates, if not existing note
        // if exists (in DB or copy of other note, do NOT change
        if (!pExistingNote){
            setCreationDate();
            setLastChangedDate();
        }
        logDebug("created cNote");
    }

    public cNote(UUID pID) {
        super();

    }

    public cNote() {

    }

    /**
     * sets last changed date to current time in milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    void setLastChangedDate(){
        logDebug("changing date");
        mLastChangedDate = (new Date()).getTime();
    }

    /**
     * gets last changed date in milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     * @return last changed date
     */
    protected long getLastChangedDate(){
        logDebug("returning change date");
        return mLastChangedDate;
    }

    /**
     * gets creation date of the note in milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     * @return creation date
     */
    protected long getCreationDate(){
        logDebug("returning creation date");
        return mCreationDate;
    }

    /**
     * set creation date to current time in milliseconds after January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    private void setCreationDate(){
        logDebug("setting creation date");
        mCreationDate = (new Date()).getTime();
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
        }
        else {
            throw new cNoteException("cNote setCreationDate",cNoteException.aCREATION_DATE_ALREADY_SET,null);
        }

    }
    public void setLastChangeDate(long lastChangeDate) {
        mLastChangedDate = lastChangeDate;
    }


    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    public abstract void deleteNote();

}
