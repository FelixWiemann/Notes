package com.nepumuk.notizen.objects.notes;

import com.nepumuk.notizen.objects.StorageObject;

import java.util.UUID;

/**
 * Created by felix on 13/04/17.
 * base class for Notes
 */
public abstract class Note extends StorageObject {

    /**
     * create new note
     * @param pID id of note
     * @param pTitle title of note
     */
    Note(UUID pID, String pTitle){
        super(pID,pTitle);
    }


    Note() {
        super();
    }

    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    public abstract void deleteNote();

}
