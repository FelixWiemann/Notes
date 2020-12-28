package com.nepumuk.notizen.objects.notes;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nepumuk.notizen.objects.tasks.BaseTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 *
 *
 *
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 */
public class TaskNote extends Note {

    private static final String LOG_TAG = "TaskNote";

    /**
     * list of tasks stored in this note
     */
    // TODO should be a List of UUIDs, if one specific task is needed,
    //  an appropriate call to a factory of sorts should be done
    //  new JSON will be needed
    //  in current state more complex interactions (delete, update, etc.) are only in a ugly way possible

    @JsonProperty("TaskList")
    private List<BaseTask> mTaskList = new ArrayList<>();


    /**
     * create new Note
     *
     * @param pID    id of Note
     * @param pTitle title of Note
     * @param pTaskList list of tasks in created note
     */
    public TaskNote(UUID pID, String pTitle, List<BaseTask>pTaskList) {
        super(pID, pTitle);
        this.setTaskList(pTaskList);
    }

    /**
     * constructors needed for JACKSON JSON
     */
    public TaskNote() {
        super();
    }

    /**
     * method from inherited class cNote
     * used for safe deleting data in this note
     */
    @Override
    public void deleteNote() {
        clearTaskList();
    }

    /**
     * adds a task to notes task list
     * @param taskToAdd task that shall be added
     */
    public void addTask(BaseTask taskToAdd){
        mTaskList.add(taskToAdd);
    }

    /**
     * returns a task that is located at the specified position inside the task list
     * @param pPos position of the task that shall be returned
     * @return task that is stored at position pPos
     */
    BaseTask getTaskAtPos(int pPos){
        return mTaskList.get(pPos);
    }

    /**
     * clears all tasks stored in task list
     */
    private void clearTaskList(){
        if (mTaskList.size()==0){
            return;
        }
        // delete each task in mTaskList
        for (BaseTask task: mTaskList
                ) {
            task.deleteTask();
        }
        // clear the list
        mTaskList.clear();
    }


    /**
     * return the entire list of tasks inside the note
     * @return list of all tasks stored in this note
     */
    @JsonIgnore
    public List<BaseTask> getTaskList() {
        return mTaskList;
    }

    /**
     * set the task list to the given one
     * @param pTaskList new task list
     */
    private void setTaskList(@NonNull List<BaseTask> pTaskList) {
        // clear all tasks currently inside list
        clearTaskList();
        // set tasklist
        this.mTaskList = pTaskList;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    /**
     * updates the given task
     * @param updatedData date to be updated
     */
    // TODO
    //  Make clean; don't loop over everything
    //  incorporate into moving from TaskList -> TaskMap or some other representation?
    public void updateTask(BaseTask updatedData) {
        HashMap<String, BaseTask> taskMap = new HashMap<>();
        for (BaseTask task:mTaskList){
            taskMap.put(task.getIdString(), task);
        }
        taskMap.put(updatedData.getIdString(),updatedData);
        this.setTaskList(new ArrayList<>(taskMap.values()));
    }
    /**
     * deletes the given task from this note
     * @param toDelete date to be deleted
     */
    // TODO
    //  Make clean; don't loop over everything
    //  incorporate into moving from TaskList -> TaskMap or some other representation?
    public void deleteTask(BaseTask toDelete) {
        HashMap<String, BaseTask> taskMap = new HashMap<>();
        for (BaseTask task:mTaskList){
            taskMap.put(task.getIdString(), task);
        }
        taskMap.remove(toDelete.getIdString());
        toDelete.deleteTask();
        this.setTaskList(new ArrayList<>(taskMap.values()));
    }

}
