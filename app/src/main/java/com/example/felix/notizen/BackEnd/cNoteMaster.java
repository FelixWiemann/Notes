package com.example.felix.notizen.BackEnd;

import com.example.felix.notizen.FrontEnd.Notes.cImageNote;
import com.example.felix.notizen.FrontEnd.Notes.cNote;
import com.example.felix.notizen.FrontEnd.Notes.cTaskNote;
import com.example.felix.notizen.FrontEnd.Notes.cTextNote;
import com.example.felix.notizen.FrontEnd.Task.cTask;
import com.example.felix.notizen.FrontEnd.Task.cTimedTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton as master entry point in the database saving all notes
 * database structure:
 * table master:
 * UUID of note as key, type of note as value.
 * table of each note-type according to fields inside the notes
 *
 * cNoteMaster manages master table
 * provides interface to different sql-table managers for each note
 *
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 03/06/17.
 *
 */
@SuppressWarnings("unused")
public class cNoteMaster {


    // dictionary containing arrays of different Note Types
    private Map<String, ArrayList> notes;

    /**
     * singleton instance made available for use everywhere
     */
    private static cNoteMaster aNoteMaster = new cNoteMaster();

    /**
     * get the singleton instance of the note master
     * @return note master instance
     */
    public static cNoteMaster getInstance() {
        return aNoteMaster;
    }

    /**
     * private constructor
     */
    private cNoteMaster() {
        notes = new HashMap<String, ArrayList>();
        notes.put(new cTextNote(null,null,null).aTYPE,new ArrayList<cTextNote>());
        notes.put(new cImageNote(null,null,null).aTYPE,new ArrayList<cImageNote>());
        notes.put(new cTaskNote(null,null,null).aTYPE,new ArrayList<cTaskNote>());
        notes.put(new cTask(null,null,false).aTYPE,new ArrayList<cTask>());
        notes.put(new cTimedTask(null,null,false).aTYPE,new ArrayList<cTimedTask>());
    }

    /**
     * add a text node to the list
     * @param pNoteToAdd
     */
    public void addNote(cTextNote pNoteToAdd){
        String type = pNoteToAdd.aTYPE;
        notes.get(type).add(pNoteToAdd);
    }

    /**
     * add a image note to the list
     * @param pNoteToAdd
     */
    public void addNote(cImageNote pNoteToAdd){
        String type = pNoteToAdd.aTYPE;
        notes.get(type).add(pNoteToAdd);
    }

    /**
     * add a task node to the list
     * @param pNoteToAdd
     */
    public void addNote(cTaskNote pNoteToAdd){
        String type = pNoteToAdd.aTYPE;
        notes.get(type).add(pNoteToAdd);
    }

    /**
     * add a task node to the list
     * @param pTaskToAdd
     */
    public void addTask(cTask pTaskToAdd){
        String type = pTaskToAdd.aTYPE;
        notes.get(type).add(pTaskToAdd);
    }

    /**
     * add a task node to the list
     * @param pTaskToAdd
     */
    public void addTask(cTimedTask pTaskToAdd){
        String type = pTaskToAdd.aTYPE;
        notes.get(type).add(pTaskToAdd);
    }

    /**
     * return an arraylist of all nodes
     * @param pType
     * @return
     */
    public ArrayList getNotesOfType(String pType){
        return notes.get(pType);
    }

}
