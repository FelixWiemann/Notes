package com.example.felix.notizen.FrontEnd.Task;

import com.example.felix.notizen.FrontEnd.cIdObject;

/**
 *
 *
 *
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 * TODO add ID
 */
@SuppressWarnings("unused")
public abstract class cTask extends cIdObject {

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
     * @param mTitle title of the new task
     * @param mText text of the new task
     * @param mDone flag if task is done or not
     */
    public cTask(String mTitle, String mText, boolean mDone){
        super(null,mTitle);
        this.mText = mText;
        this.mDone = mDone;
    }

    /**
     * returns the flag of task completion
     * @return true, if task is complete, false if not
     */
    public boolean isDone() {
        return mDone;
    }

    /**
     * sets the flag of task completion
     * @param mDone true if task is complete, false if not
     */
    public void setDone(boolean mDone) {
        this.mDone = mDone;
    }

    /**
     * returns the text of the task
     * @return text of the task
     */
    public String getText() {
        return mText;
    }

    /**
     * set the text of the task
     * @param mText new text of task
     */
    public void setText(String mText) {
        this.mText = mText;
    }


    /**
     * abstract method to implement in each inherited task type.
     * used for special deletion things
     * e.g. timed task: delete timer service associated with it
     */
    public abstract void deleteTask();


}
