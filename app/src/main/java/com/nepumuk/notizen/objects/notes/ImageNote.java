package com.nepumuk.notizen.objects.notes;


import androidx.annotation.Keep;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nepumuk.notizen.core.objects.Note;

/**
 *
 *
 *
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 */
@Keep
public class ImageNote extends Note {

    private static final String LOG_TAG = "ImageNote";

    /**
     * location of the image of the note
     */
    @JsonProperty("imageLocation")
    private String mImageLocation;

    /**
     * create new Note
     *
     * @param pID    id of Note
     * @param pTitle title of Note
     * @param pImageLocation location of the image
     */
    public ImageNote(String pID, String pTitle, String pImageLocation) {
        super(pID, pTitle);
        // assign image location
        setImageLocation(pImageLocation);
    }

    /**
     * copy constructor
     *
     * makes a deep clone of the given object
     * @param other to copy from
     */
    public ImageNote(ImageNote other) {
        super(other);
        // TODO impl.
    }

    /**
     * default constructor used for deserialization from JACKSON
     */
    public ImageNote() {
        super();
    }

    /**
     * handles deletion of image referenced inside instance of cImageNote
     * to have consistent data in storage as in DB referenced
     */
    @Override
    public void deleteNote() {
        // delete image file
        deleteImageAtStorageLocation();
    }

    /**
     * returns the image location of the note
     * @return image location
     */
    public String getImageLocation() {
//        logError("returning image location");
        return mImageLocation;
    }


    /**
     * sets the image location of the note
     * @param pImageLocation new location of the image
     */
    public void setImageLocation(String pImageLocation) {
        // delete current image if available
        deleteImageAtStorageLocation();
        // change image location
        this.mImageLocation = pImageLocation;
        // set last changed date
        this.setLastChangedDate();

    }

    /**
     * handles deletion of the current image
     */
    private void deleteImageAtStorageLocation(){
        //throw new Exception("not yet implemeted");
        // TODO: handle deletion
        // validate, file exists, delete if exists
    }

    @Override
    public int getVersion() {
        return 1;
    }

    /**
     * create a deep copy of itself
     *
     * @return deep copy
     */
    @Override
    public ImageNote deepCopy() {
        return new ImageNote(this);
    }
}
