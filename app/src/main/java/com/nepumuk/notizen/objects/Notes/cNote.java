package com.nepumuk.notizen.objects.Notes;

import com.nepumuk.notizen.objects.cStorageObject;

import java.util.UUID;

/**
 * Created by felix on 13/04/17.
 * base class for Notes
 */
public abstract class cNote extends cStorageObject {

    /**
     * create new note
     * @param pID id of note
     * @param pTitle title of note
     */
    cNote(UUID pID, String pTitle){
        super(pID,pTitle);
    }


    cNote() {
        super();
    }

    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    public abstract void deleteNote();

}
