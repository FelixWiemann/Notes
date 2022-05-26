package com.nepumuk.notizen.core.objects;

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
    public Note(String pID, String pTitle){
        super(pID,pTitle);
    }

    /**
     * copy constructor
     *
     * makes a deep clone of the given object
     * @param other to copy from
     */
    public Note(Note other){
        super(other);
    }

    public Note() {
        super();
    }

    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    public abstract void deleteNote();

}
