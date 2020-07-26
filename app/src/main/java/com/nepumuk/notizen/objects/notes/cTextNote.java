package com.nepumuk.notizen.objects.notes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * a Text Note is a traditional note containing only a title and a message
 */
public class cTextNote extends cNote {

    /**
     * message contained in the note
     */
    @JsonProperty("message")
    private String mMessage;

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
    }

    /**
     * default constructor needed for JACKSON JSON
     */
    public cTextNote() {
        super();
    }


    /**
     * gets the message of the note
     * @return message of the note
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * sets a new message to the note
     * @param mMessage new message to use
     */
    public void setMessage(String mMessage) {
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
    }

    @Override
    public int getVersion() {
        return 1;
    }
}
