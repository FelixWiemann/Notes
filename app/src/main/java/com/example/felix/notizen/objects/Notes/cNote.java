package com.example.felix.notizen.objects.Notes;

import android.util.Log;

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

    private static final String LOG_TAG = "NOTE_LOG_TAG";

    /**
     * create new note
     * @param pID id of note
     * @param pTitle title of note
     */
    protected cNote(UUID pID, String pTitle){
        super(pID,pTitle);
        Log.d(LOG_TAG,"cNote created");
    }


    protected cNote() {
    }

    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    public abstract void deleteNote();

}
