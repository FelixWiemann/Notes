package com.example.felix.notizen.FrontEnd.Notes;

/**
 * Created by felix on 13/04/17.
 */

public class TextNote extends cNote {

    private String mMessage;

    /**
     * create new Note
     *  @param pID    id of Note
     * @param pTitle title of Note
     * @param pMessage message of TextNote
     */
    public TextNote(String pID, String pTitle, String pMessage) {
        super(pID, pTitle);
        this.mMessage = pMessage;
    }

    /**
     * create new Note
     *  @param pID           id of Note
     * @param pTitle        title of note
     * @param pExistingNote whether is existing node
     * @param pMessage message of TextNote
     */
    public TextNote(String pID, String pTitle, boolean pExistingNote, String pMessage) {
        super(pID, pTitle, pExistingNote);
        this.mMessage = pMessage;
    }

    /**
     * @return
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * @param mMessage
     */
    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
