package com.example.felix.notizen.objects.Notes;


import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 *
 *
 *
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 */
@SuppressWarnings("unused")
public class cImageNote extends cNote {

    private static final String LOG_TAG = "ImageNote";
    /**
     * identifier of class
     */
    public static String aTYPE = "cImageNote";

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
    public cImageNote(UUID pID, String pTitle, String pImageLocation) {
        super(pID, pTitle);
        Log.d(LOG_TAG,"creating cImageNote");
        // assign image location
        setImageLocation(pImageLocation);
    }

    /**
     * default constructor used for deserialization from JACKSON
     */
    public cImageNote() {}

    /**
     * handles deletion of image referenced inside instance of cImageNote
     * to have consistent data in storage as in DB referenced
     */
    @Override
    public void deleteNote() {
        Log.d(LOG_TAG,"deleting image note");
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
        Log.e(LOG_TAG, "setting image location");
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
        Log.e(LOG_TAG, "deleting image at storage location");
        //throw new Exception("not yet implemeted");
        // TODO: handle deletion
        // validate, file exists, delete if exists
    }

    @Override
    public int getVersion() {
        return 1;
    }
}
