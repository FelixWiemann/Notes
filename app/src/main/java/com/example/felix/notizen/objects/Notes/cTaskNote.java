package com.example.felix.notizen.objects.Notes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.felix.notizen.objects.Task.cBaseTask;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
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

    public static final String TASK_NOTE_LOG_TAG = "TaskNote";
    /**
     * identifier of class
     */
    public static String aTYPE = "cTaskNote";


    /**
     * list of tasks stored in this note
     */
    @JsonProperty("TaskList")
    private List<cBaseTask> mTaskList = new ArrayList<>();


    /**
     * create new Note
     *
     * @param pID    id of Note
     * @param pTitle title of Note
     * @param pTaskList list of tasks in created note
     */
    public cTaskNote(UUID pID, String pTitle, List<cBaseTask>pTaskList) {
        super(pID, pTitle);
        Log.d(TASK_NOTE_LOG_TAG,"creating new cTaskNote");
        this.setTaskList(pTaskList);
    }

    /**
     * constructors needed for JACKSON JSON
     */
    public cTaskNote(){

    }

    /**
     * method from inherited class cNote
     * used for safe deleting data in this note
     */
    @Override
    public void deleteNote() {
        Log.d(TASK_NOTE_LOG_TAG,"deleting note");
        clearTaskList();
    }

    /**
     * adds a task to notes task list
     * @param taskToAdd task that shall be added
     */
    public void addTask(cBaseTask taskToAdd){
        Log.d(TASK_NOTE_LOG_TAG,"adding task to note");
        mTaskList.add(taskToAdd);
    }

    /**
     * returns a task that is located at the specified position inside the task list
     * @param pPos position of the task that shall be returned
     * @return task that is stored at position pPos
     */
    public cBaseTask getTaskAtPos(int pPos){
        Log.d(TASK_NOTE_LOG_TAG,"returning task at pos " + pPos);
        return mTaskList.get(pPos);
    }

    /**
     * clears all tasks stored in task list
     */
    private void clearTaskList(){
        Log.d(TASK_NOTE_LOG_TAG,"clearing all tasks");
        if (mTaskList.size()==0){
            Log.d(TASK_NOTE_LOG_TAG,"already clear");
            return;
        }
        // delete each task in mTaskList
        for (cBaseTask task: mTaskList
                ) {
            task.deleteTask();
        }
        // clear the list
        mTaskList.clear();
        Log.d(TASK_NOTE_LOG_TAG,"tasks cleared");
    }


    /**
     * return the entire list of tasks inside the note
     * @return list of all tasks stored in this note
     */
    @JsonIgnore
    public List<cBaseTask> getTaskList() {
        Log.d(TASK_NOTE_LOG_TAG,"returning task list");
        return mTaskList;
    }

    /**
     * set the task list to the given one
     * @param pTaskList new task list
     */
    private void setTaskList(@NonNull List<cBaseTask> pTaskList) {
        Log.d(TASK_NOTE_LOG_TAG,"setting task list, clearing current first");
        // clear all tasks currently inside list
        clearTaskList();
        // set tasklist
        this.mTaskList = pTaskList;
    }

    @Override
    public int getVersion() {
        return 1;
    }
}
