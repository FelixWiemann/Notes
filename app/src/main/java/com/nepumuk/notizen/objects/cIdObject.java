package com.nepumuk.notizen.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.UUID;

/**
 * Id object handles identities of all data objects.
 * it contains a @see UUID as a unique identifier.
 *
 * It also handles the title of each object.
 * -> TODO maybe move to TitledObject?
 *
 */
public class cIdObject extends cSortableObject {

    /**
     * ID string of current note, used to identify each note
     */
    private UUID mID;
    /**
     * title of the note
     */
    @JsonProperty("title")
    private String mTitle;

    private static final String TAG = cIdObject.class.getSimpleName();

    /**
     * creates a new object with an id and a title
     * this constructor is only to be used for restoring objects from stored object representations
     * @param mID id of already existing, stored object
     * @param mTitle title of object
     */
    cIdObject(UUID mID, String mTitle) {
        super();
        this.mID = mID;
        this.mTitle = mTitle;
    }

    /**
     * Default Constructor for deserialization witch JACKSON
     */
    cIdObject() {
        super();
    }

    /**
     * sets the title of the note
     * @param pTitle new title
     */
    public void setTitle(String pTitle){
        mTitle = pTitle;
    }

    /**
     * sets the id of the note
     * DO NOT USE THIS FUNCTION. CREATE A NEW OBJECT FROM DB VIA CONSTRUCTOR INSTEAD
     * @param pID new id
     */
    protected void setId(UUID pID) {
        if (mID == null){
            mID = pID;
        }else{
            throw new IllegalStateException("ID was already set to " + mID.toString() + " when setting ID " + pID.toString());
        }
    }

    /**
     * setter for JSON of ID String
     * @param id representation of UUID
     */
    @JsonSetter("idString")
    private void setIdString(String id) {
        setId(UUID.fromString(id));
    }
    /**
     * gets the id o the note
     * @return id
     */
    UUID getID(){
        return mID;
    }

    /**
     * gets the title of the note
     * @return title
     */
    public String getTitle(){
        return mTitle;
    }

    public String getIdString(){
        return mID.toString();
    }

}
