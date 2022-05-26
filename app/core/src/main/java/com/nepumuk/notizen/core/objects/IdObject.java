package com.nepumuk.notizen.core.objects;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.jetbrains.annotations.NotNull;

/**
 * Id object handles identities of all data objects.
 * it contains a @see UUID as a unique identifier.
 *
 * It also handles the title of each object.
 * -> TODO maybe move to TitledObject?
 *
 */
public class IdObject extends SortableObject {

    /**
     * ID string of current note, used to identify each note
     */
    @PrimaryKey
    @NonNull
    private String ID;
    /**
     * title of the note
     */
    @JsonProperty("title")
    private String mTitle;

    private static final String LOG_TAG = "IdObject";

    /**
     * creates a new object with an id and a title
     * this constructor is only to be used for restoring objects from stored object representations
     * @param mID id of already existing, stored object
     * @param mTitle title of object
     */
    IdObject(String mID, String mTitle) {
        super();
        this.ID = mID;
        this.mTitle = mTitle;
    }

    /**
     * copy constructor
     *
     * makes a deep clone of the given object
     * @param other to copy from
     */
    IdObject(@NotNull IdObject other) {
        super();
        this.ID = other.ID;
        this.mTitle = other.mTitle;
    }

    /**
     * Default Constructor for deserialization witch JACKSON
     */
    IdObject() {
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
    protected void setID(String pID) {
        if (ID == null){
            ID = pID;
        }else{
            throw new IllegalStateException("ID was already set to " + ID.toString() + " when setting ID " + pID.toString());
        }
    }

    /**
     * setter for JSON of ID String
     * @param id representation of UUID
     */
    @JsonSetter("idString")
    private void setIdString(String id) {
        setID(id);
    }
    /**
     * gets the id o the note
     * @return id
     */
    @JsonGetter("idString")
    public String getID(){
        return ID;
    }

    /**
     * gets the title of the note
     * @return title
     */
    public String getTitle(){
        return mTitle;
    }

}
