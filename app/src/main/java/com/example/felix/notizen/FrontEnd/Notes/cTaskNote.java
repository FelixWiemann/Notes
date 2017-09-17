package com.example.felix.notizen.FrontEnd.Notes;

import com.example.felix.notizen.FrontEnd.Task.cBaseTask;

import java.util.List;
import java.util.UUID;

/**
 *
 *
 *
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 */
@SuppressWarnings("unused")
public class cTaskNote extends cNote {

    /**
     * identifier of class
     */
    public String aTYPE = "cTaskNote";


    /**
     * list of tasks stored in this note
     */
    private List<cBaseTask> mTaskList;


    /**
     * create new Note
     *
     * @param pID    id of Note
     * @param pTitle title of Note
     * @param pTaskList list of tasks in created note
     */
    public cTaskNote(UUID pID, String pTitle, List<cBaseTask>pTaskList) {
        super(pID, pTitle);
        logDebug("creating new cTaskNote");
        this.setTaskList(pTaskList);
    }

    /**
     * create new note
     * able to determine whether it is a previously stored note, to not change creation date and
     * last modified date
     *
     * @param pID           id of Note
     * @param pTitle        title of note
     * @param pExistingNote whether is existing node
     *                      true -> no new creation/last changed date
     * @param pTaskList list of tasks in created note
     */
    public cTaskNote(UUID pID, String pTitle, List<cBaseTask>pTaskList, boolean pExistingNote) {
        super(pID, pTitle, pExistingNote);
        logDebug("creating new cTaskNote");
        this.setTaskList(pTaskList);
    }

    /**
     * method from inherited class cNote
     * used for safe deleting data in this note
     */
    @Override
    public void deleteNote() {
        logDebug("deleting note");
        clearTaskList();
    }

    /*
     * add additional data to this note
     * inherited by cNote
     *
     * @param pDataBlob of type List<cBaseTask> containing task list
     *
    @Override
    public void addAdditionalData(Object pDataBlob) {
        logDebug("adding additional data");
        // tod handle adding data
    }*/

    /**
     * adds a task to notes task list
     * @param taskToAdd task that shall be added
     */
    public void addTask(cBaseTask taskToAdd){
        logDebug("adding task to note");
        mTaskList.add(taskToAdd);
    }

    /**
     * returns a task that is located at the specified position inside the task list
     * @param pPos position of the task that shall be returned
     * @return task that is stored at position pPos
     */
    public cBaseTask getTaskAtPos(int pPos){
        logDebug("returning task at pos " +String.valueOf(pPos));
        return mTaskList.get(pPos);
    }

    /**
     * clears all tasks stored in task list
     */
    private void clearTaskList(){
        logDebug("clearing all tasks");
        // delete each task in mTaskList
        for (cBaseTask task: mTaskList
                ) {
            task.deleteTask();
        }
        // clear the list
        mTaskList.clear();
        logDebug("tasks cleared");
    }


    /**
     * return the entire list of tasks inside the note
     * @return list of all tasks stored in this note
     */
    public List<cBaseTask> getTaskList() {
        logDebug("returning task list");
        return mTaskList;
    }

    /**
     * set the task list to the given one
     * @param pTaskList new task list
     */
    private void setTaskList(List<cBaseTask> pTaskList) {
        logDebug("setting task list, clearing current first");
        // clear all tasks currently inside list
        clearTaskList();
        // set tasklist
        this.mTaskList = pTaskList;
    }

    @Override
    public String generateJSONString() {
        return null;
    }
}
