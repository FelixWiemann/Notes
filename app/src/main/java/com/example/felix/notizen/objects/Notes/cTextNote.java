package com.example.felix.notizen.objects.Notes;

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
    private String mMessage;

    public static final String aJSON_TEXT = "TEXT";

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

    /*
     * add additional data to this note
     * DataBlob may contain any kind of data, determined by type of note
     *
     * @param pDataBlob contains the Data to add, in case of these classes:</p>
     *                 cImageNote: string containing location of Image
     *
    @Override
    public void addAdditionalData(Object pDataBlob) {
        logDebug("adding additional data");
        try{
            String message = (String) pDataBlob;
            setMessage(message);
            logDebug("added additional data");
        }
        catch (Exception e){
            logError(e.getMessage());
            //TOD: exception handling and logging
        }
    }*/

    @Override
    public String generateJSONString(){
        String ID = getJsonID();
        String Title = getJsonTitle()+aJSON_COMMA+aJSON_NEW_LINE;
        String CreationDate = getJsonCreationDate()+aJSON_COMMA+aJSON_NEW_LINE;
        String LastChangeDate = getJsonLastChangeDate()+aJSON_COMMA+aJSON_NEW_LINE;
        String Text = getJsonText()+aJSON_NEW_LINE;
        return  aJSON_OBJ_BEGIN+ID+Title+CreationDate+LastChangeDate + Text + aJSON_OBJ_END;
    }

    private String getJsonText(){
        return aJSON_FIELD_SIGN+aJSON_TEXT+aJSON_FIELD_SIGN+aJSON_SEP+aJSON_FIELD_SIGN+getMessage()+aJSON_FIELD_SIGN;
    }
}
