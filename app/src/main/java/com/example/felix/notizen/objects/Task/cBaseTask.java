package com.example.felix.notizen.objects.Task;

import com.example.felix.notizen.objects.cIdObject;
import com.example.felix.notizen.objects.cStorageObject;

import java.util.UUID;

/**
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 */
@SuppressWarnings("unused")
public abstract class cBaseTask extends cStorageObject {

    public static String aTYPE = "cBaseTask";
    /**
     * text of the task
     */
    private String mText;
    /**
     * flag whether task is marked as done
     * true -> task done
     * false -> task open
     */
    private boolean mDone;

    /**
     * constructor setting title, text and flag for task completion
     * @param mId id of the task
     * @param mTitle title of the new task
     * @param mText text of the new task
     * @param mDone flag if task is done or not
     */
    public cBaseTask(UUID mId, String mTitle, String mText, boolean mDone){
        super(mId,mTitle);
        this.mText = mText;
        this.mDone = mDone;
        logDebug("creating cBaseTask");
    }

    public cBaseTask(UUID pID) {
        super();
    }

    public cBaseTask() {
    }

    /**
     * returns the flag of task completion
     * @return true, if task is complete, false if not
     */
    public boolean isDone() {
        logDebug("is task done?");
        return mDone;
    }

    /**
     * sets the flag of task completion
     * @param mDone true if task is complete, false if not
     */
    public void setDone(boolean mDone) {
        logDebug("setting task done");
        this.mDone = mDone;
    }

    /**
     * returns the text of the task
     * @return text of the task
     */
    public String getText() {
        logDebug("getting task text");
        return mText;
    }

    /**
     * set the text of the task
     * @param mText new text of task
     */
    public void setText(String mText) {
        logDebug("setting task text");
        this.mText = mText;
    }


    /**
     * abstract method to implement in each inherited task type.
     * used for special deletion things
     * e.g. timed task: delete timer service associated with it
     */
    public abstract void deleteTask();


}
