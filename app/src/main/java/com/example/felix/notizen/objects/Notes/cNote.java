package com.example.felix.notizen.objects.Notes;

import com.example.felix.notizen.objects.cStorageObject;

import java.util.UUID;

/**
 * Created by felix on 13/04/17.
 * base class for Notes
 */
@SuppressWarnings("unused")
public abstract class cNote extends cStorageObject {

    /**
     * identifier of class
     */
    public static String aTYPE = "cNote";



    /**
     * create new note
     * @param pID id of note
     * @param pTitle title of note
     */
    protected cNote(UUID pID, String pTitle){
        super(pID,pTitle);
        logDebug("creating new cNote");
        // set creation and last changed dates
        setCreationDate();
        setLastChangedDate();
        logDebug("cNote created");
    }

    /**
     * create new note
     * able to determine whether it is a previously stored note, to not change creation date and
     * last modified date
     *
     * @param pID id of Note
     * @param pTitle title of note
     * @param pExistingNote whether is existing node
     *                      true -> no new creation/last changed date
     *                      false -> new creation/last changed date
     */
    @Deprecated
    cNote(UUID pID, String pTitle, boolean pExistingNote){
        super(pID,pTitle);
        logDebug("creating preexisting cNote");
        // only set dates, if not existing note
        // if exists (in DB or copy of other note, do NOT change
        if (!pExistingNote){
            setCreationDate();
            setLastChangedDate();
        }
        logDebug("created cNote");
    }

    public cNote(UUID pID) {
        super();

    }

    public cNote() {

    }


    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    public abstract void deleteNote();

}
