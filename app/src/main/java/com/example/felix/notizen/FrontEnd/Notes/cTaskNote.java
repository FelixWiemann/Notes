package com.example.felix.notizen.FrontEnd.Notes;

import com.example.felix.notizen.FrontEnd.Task.cTask;

import java.util.List;

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
    public static String aTYPE = "cTaskNote";


    /**
     * list of tasks stored in this note
     */
    private List<cTask> mTaskList;


    /**
     * create new Note
     *
     * @param pID    id of Note
     * @param pTitle title of Note
     * @param pTaskList list of tasks in created note
     */
    public cTaskNote(String pID, String pTitle, List<cTask>pTaskList) {
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
    public cTaskNote(String pID, String pTitle, List<cTask>pTaskList, boolean pExistingNote) {
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

    /**
     * add additional data to this note
     * inherited by cNote
     *
     * @param pDataBlob of type List<cTask> containing task list
     */
    @Override
    public void addAdditionalData(Object pDataBlob) {
        logDebug("adding additional data");
        // todo handle adding data
    }

    /**
     * adds a task to notes task list
     * @param taskToAdd task that shall be added
     */
    public void addTask(cTask taskToAdd){
        logDebug("adding task to note");
        mTaskList.add(taskToAdd);
    }

    /**
     * returns a task that is located at the specified position inside the task list
     * @param pPos position of the task that shall be returned
     * @return task that is stored at position pPos
     */
    public cTask getTaskAtPos(int pPos){
        logDebug("returning task at pos " +String.valueOf(pPos));
        return mTaskList.get(pPos);
    }

    /**
     * clears all tasks stored in task list
     */
    private void clearTaskList(){
        logDebug("clearing all tasks");
        // delete each task in mTaskList
        for (cTask task: mTaskList
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
    public List<cTask> getTaskList() {
        logDebug("returning task list");
        return mTaskList;
    }

    /**
     * set the task list to the given one
     * @param pTaskList new task list
     */
    private void setTaskList(List<cTask> pTaskList) {
        logDebug("setting task list, clearing current first");
        // clear all tasks currently inside list
        clearTaskList();
        // set tasklist
        this.mTaskList = pTaskList;
    }
}
