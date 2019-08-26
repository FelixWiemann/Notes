package com.example.felix.notizen.objects.Notes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 *
 *
 *
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 13/04/17.
 */
@SuppressWarnings("unused")
public class cTextNote extends cNote {

    /**
     * identifier of class
     */
    public static String aTYPE = "cTextNote";

    /**
     * message contained in the note
     */
    @JsonProperty("message")
    private String mMessage;


    public cTextNote(UUID pID){
        super(pID);

    }

    public cTextNote(){
        super();

    }

    /**
     * create new Note with these parameters.
     *
     * @param pID    id of Note
     * @param pTitle title of Note
     * @param pMessage message of cTextNote
     */
    public cTextNote(UUID pID, String pTitle, String pMessage) {
        super(pID, pTitle);
        this.mMessage = pMessage;
        logDebug("cTextNote created");
    }

    /**
     * create new Note
     *  @param pID           id of Note
     * @param pTitle        title of note
     * @param pExistingNote whether is existing node
     * @param pMessage message of cTextNote
     */
    @Deprecated
    public cTextNote(UUID pID, String pTitle, boolean pExistingNote, String pMessage) {
        super(pID, pTitle, pExistingNote);
        this.mMessage = pMessage;
        logDebug("cTextNote created");
    }

    /**
     * gets the message of the note
     * @return message of the note
     */
    public String getMessage() {
        logDebug("message returned");
        return mMessage;
    }

    /**
     * sets a new message to the note
     * @param mMessage new message to use
     */
    public void setMessage(String mMessage) {
        logDebug("message set");
        this.mMessage = mMessage;
    }

    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    @Override
    public void deleteNote() {
        logDebug("note deleted");
    }

    @Override
    public int getVersion() {
        return 1;
    }
}
