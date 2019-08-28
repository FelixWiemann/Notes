package com.example.felix.notizen.objects;

import com.example.felix.notizen.Utils.Logger.cLoggerObject;
import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

/**
 * Created as part of notes in package com.example.felix.notizen.FrontEnd
 * by Felix "nepumuk" Wiemann on 29/04/17.
 */
@SuppressWarnings("unused")
public class cIdObject extends cLoggerObject {

    /**
     * ID string of current note, used to identify each note
     */
    private UUID mID;
    /**
     * title of the note
     */
    @JsonProperty("title")
    private String mTitle;

    /**
     * creates a new object with an id and a title
     * this constructor is only to be used for restoring objects from stored object representations
     * @param mID id of already existing, stored object
     * @param mTitle title of object
     */
    cIdObject(UUID mID, String mTitle) {
        super();
        logDebug("creating cIdObject");
        this.mID = mID;
        this.mTitle = mTitle;
    }

    public cIdObject(UUID mID){
        super();
    }

    /**
     * TODO this constructor to all subclasses
     * creates a new object with an id and a title
     * a unique UUID is added automatically
     * @param mTitle title of the new object
     */
    cIdObject(String mTitle){
        logDebug("creating cIdObject with uuid");
        this.mTitle = mTitle;
        this.mID = UUID.randomUUID();
    }

    cIdObject() {

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
    private void setId(UUID pID) throws cIdObjectException {
        logDebug("setting id");
        if (mID == null){
            mID = pID;
        }else{
            throw new cIdObjectException(String.format("object %s %s.setId()",mID.toString(),this.getClass().getSimpleName()),cIdObjectException.aID_ALREADY_SET_EXCEPTION,null);
        }
    }

    @JsonSetter("idString")
    private void setIdString(String id) throws cIdObjectException {
        setId(UUID.fromString(id));
    }
    /**
     * gets the id o the note
     * @return id
     */
    UUID getID(){
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

}
