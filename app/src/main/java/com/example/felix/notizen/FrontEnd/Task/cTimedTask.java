package com.example.felix.notizen.FrontEnd.Task;

import java.util.Date;

/**
 * ${PACKAGE_NAME}
 * notes
 *
 *
 * Created by felix on 14/04/17.
 *
 * TODO create timer, handle it (start/stop/alarm if over/etc.)
 */
@SuppressWarnings("unused")
public class cTimedTask extends cTask{

    /**
     * returns the date when the task is due in ms since January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     * @return due date
     */
    public long getTaskDueDate() {
        logDebug("getting task due date");
        return mTaskDueDate;
    }

    /**
     * sets date when the task is due in ms since January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     * @param mTaskDueDate new task due date
     */
    public void setTaskDueDate(long mTaskDueDate) {
        logDebug("setting task due date");
        this.mTaskDueDate = mTaskDueDate;
    }

    /**
     * task due date in ms since January 1, 1970 00:00:00 GMT
     * @see Date#getTime()
     */
    private long mTaskDueDate;

    /**
     * constructor setting title, text and flag for task completion
     * @param mTitle title of the new task
     * @param mText text of the new task
     * @param mDone flag if task is done or not
     */
    public cTimedTask(String mTitle, String mText, boolean mDone) {
        super(mTitle, mText, mDone);
        logDebug("creating timed task");
    }

    /**
     * used to delete timer referenced in this task
     */
    @Override
    public void deleteTask() {
        logDebug("deleting timed task");
        // TODO: delete timer
    }


}
