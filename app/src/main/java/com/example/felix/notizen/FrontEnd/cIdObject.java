package com.example.felix.notizen.FrontEnd;

import com.example.felix.notizen.BackEnd.Logger.cNoteLogger;

import java.util.UUID;

/**
 * Created as part of notes in package com.example.felix.notizen.FrontEnd
 * by Felix "nepumuk" Wiemann on 29/04/17.
 */
@SuppressWarnings("unused")
public class cIdObject {
    /**
     * logger instance for logging everything
     */
    static cNoteLogger logger = cNoteLogger.getInstance();
    /**
     * ID string of current note, used to identify each note
     */
    private UUID mID;
    /**
     * title of the note
     */
    private String mTitle;

    /**
     * identifier of class
     */
    public String aTYPE = "cIdObject";

    /**
     * creates a new object with an id and a title
     * this constructor is only to be used for restoring objects from stored object representations
     * @param mID id of already existing, stored object
     * @param mTitle title of object
     */
    public cIdObject(UUID mID, String mTitle) {
        logDebug("creating cIdObject");
        this.mID = mID;
        this.mTitle = mTitle;
    }

    /**
     * TODO this constructor to all subclasses
     * creates a new object with an id and a title
     * a unique UUID is added automatically
     * @param mTitle title of the new object
     */
    public cIdObject(String mTitle){
        logDebug("creating cIdObject with uuid");
        this.mTitle = mTitle;
        this.mID = UUID.randomUUID();
    }

    /**
     * sets the title of the note
     * @param pTitle new title
     */
    public void setTitle(String pTitle){
        logDebug("setting title");
        mTitle = pTitle;
    }

    /**
     * sets the id of the note
     * DO NOT USE THIS FUNCTION. CREATE A NEW OBJECT FROM DB VIA CONSTRUCTOR INSTEAD
     * @param pID new id
     */
    @Deprecated
    public void setId(UUID pID) throws cIdObjectException {
        logDebug("setting id");
        if (mID == null){
            mID = pID;
        }else{
            throw new cIdObjectException(String.format("object %s %s.setId()",mID.toString(),this.getClass().getSimpleName()),cIdObjectException.aID_ALREADY_SET_EXCEPTION,null);
        }
    }

    /**
     * gets the id o the note
     * @return id
     */
    public UUID getID(){
        logDebug("returning ID");
        return mID;
    }

    /**
     * gets the title of the note
     * @return title
     */
    public String getTitle(){
        logDebug("returning title");
        return mTitle;
    }


    public String getIdString(){
        return mID.toString();
    }

    protected void logInfo(String Message){
        logger.logInfo(Message);
    }
    protected void logError(String Message){
        logger.logError(Message);
    }
    protected void logWarning(String Message){
        logger.logWarning(Message);
    }
    protected void logDebug(String Message){
        logger.logDebug(String.format("%s",Message));
    }



}
