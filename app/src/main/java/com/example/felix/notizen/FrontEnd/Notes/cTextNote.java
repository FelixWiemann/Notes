package com.example.felix.notizen.FrontEnd.Notes;

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
     * message contained in the note
     */
    private String mMessage;

    /**
     * create new Note with these parameters.
     *
     * @param pID    id of Note
     * @param pTitle title of Note
     * @param pMessage message of cTextNote
     */
    public cTextNote(String pID, String pTitle, String pMessage) {
        super(pID, pTitle);
        this.mMessage = pMessage;
    }

    /**
     * create new Note
     *  @param pID           id of Note
     * @param pTitle        title of note
     * @param pExistingNote whether is existing node
     * @param pMessage message of cTextNote
     */
    public cTextNote(String pID, String pTitle, boolean pExistingNote, String pMessage) {
        super(pID, pTitle, pExistingNote);
        this.mMessage = pMessage;
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
    }

    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    @Override
    public void deleteNote() {

    }

    /**
     * add additional data to this note
     * DataBlob may contain any kind of data, determined by type of note
     *
     * @param pDataBlob contains the Data to add, in case of these classes:</p>
     *                 cImageNote: string containing location of Image
     */
    @Override
    public void addAdditionalData(Object pDataBlob) {
        try{
            String message = (String) pDataBlob;
            setMessage(message);
        }
        catch (Exception e){
            //TODO: exception handling and logging
        }
    }
}
