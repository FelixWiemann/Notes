package com.example.felix.notizen.FrontEnd.Notes;


/**
 *
 *
 *
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 */
@SuppressWarnings("unused")
public class cImageNote extends cNote {

    /**
     * identifier of class
     */
    public static String aTYPE = "cImageNote";

    /**
     * location of the image of the note
     */
    private String mImageLocation;


    /**
     * create new Note
     *
     * @param pID    id of Note
     * @param pTitle title of Note
     * @param pImageLocation location of the image
     */
    public cImageNote(String pID, String pTitle, String pImageLocation) {
        super(pID, pTitle);
        logDebug("creating cImageNote");
        // assign image location
        setImageLocation(pImageLocation);
    }

    /**
     * create new Note able to contain an image
     *
     * @param pID           id of Note
     * @param pTitle        title of note
     * @param pExistingNote whether is existing node
     *                      true -> no new creation/last changed date
     *                      shall be used when loading notes from DB
     * @param pImageLocation location of the image
     */
    public cImageNote(String pID, String pTitle,String pImageLocation, boolean pExistingNote) {
        super(pID, pTitle, pExistingNote);
        logDebug("creating existing cImageNote");
        // assign image location
        setImageLocation(pImageLocation);
    }

    /**
     * handles deletion of image referenced inside instance of cImageNote
     * to have consistent data in storage as in DB referenced
     */
    @Override
    public void deleteNote() {
        logDebug("deleting image note");
        // delete image file
        deleteImageAtStorageLocation();
    }

    /**
     * add additional data to this note
     * inherited by cNote
     *
     * @param pDataBlob of type String containing location of Image
     */
    @Override
    public void addAdditionalData(Object pDataBlob){
        logDebug("adding additional data");
        try{
            String imageLocation = (String) pDataBlob;
            setImageLocation(imageLocation);
            logDebug("added additional data");
        }
        catch (Exception e){
            logError(e.getMessage());
            //TODO: exception handling and logging
        }
    }

    /**
     * returns the image location of the note
     * @return image location
     */
    public String getImageLocation() {
        logError("returning image location");
        return mImageLocation;
    }


    /**
     * sets the image location of the note
     * @param pImageLocation new location of the image
     */
    private void setImageLocation(String pImageLocation) {
        logError("setting image location");
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
        logError("deleting image at storage location");
        //throw new Exception("not yet implemeted");
        // TODO: handle deletion
        // validate, file exists, delete if exists
    }


}
