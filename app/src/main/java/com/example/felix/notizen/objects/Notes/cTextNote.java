package com.example.felix.notizen.objects.Notes;

import android.util.Log;

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

    private static final String TEXT_NOTE_LOG_TAG = "cTextNote";
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
        Log.d(TEXT_NOTE_LOG_TAG,"cTextNote created");
    }

    /**
     * gets the message of the note
     * @return message of the note
     */
    public String getMessage() {
        Log.d(TEXT_NOTE_LOG_TAG,"message returned");
        return mMessage;
    }

    /**
     * sets a new message to the note
     * @param mMessage new message to use
     */
    public void setMessage(String mMessage) {
        Log.d(TEXT_NOTE_LOG_TAG,"message set");
        this.mMessage = mMessage;
        onDataChanged();
    }

    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    @Override
    public void deleteNote() {
        // doesn't need to do anything, the only data is stored as text in DB
        Log.d(TEXT_NOTE_LOG_TAG,"note deleted");
    }

    @Override
    public int getVersion() {
        return 1;
    }
}
