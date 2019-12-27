package com.example.felix.notizen.objects;

import android.util.Log;

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
@SuppressWarnings("unused")
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
        Log.d(TAG, "creating cIdObject");
        this.mID = mID;
        this.mTitle = mTitle;
    }

    /**
     * Default Constructor for deserialization witch JACKSON
     */
    cIdObject() {}

    /**
     * sets the title of the note
     * @param pTitle new title
     */
    public void setTitle(String pTitle){
        Log.d(TAG, "setting title");
        mTitle = pTitle;
    }

    /**
     * sets the id of the note
     * DO NOT USE THIS FUNCTION. CREATE A NEW OBJECT FROM DB VIA CONSTRUCTOR INSTEAD
     * @param pID new id
     */
    protected void setId(UUID pID) throws cIdObjectException {
        Log.d(TAG, "setting id");
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
        Log.d(TAG, "returning ID");
        return mID;
    }

    /**
     * gets the title of the note
     * @return title
     */
    public String getTitle(){
        Log.d(TAG, "returning title");
        return mTitle;
    }

    public String getIdString(){
        return mID.toString();
    }

}
