package com.example.felix.notizen.objects.Task;

import android.util.Log;

import com.example.felix.notizen.Utils.DateStrategy;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

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
public class cTimedTask extends cBaseTask {


    public static String aTYPE = "cTimedTask";

    private final static String LOG_TAG = "TIMED_TASK";

    /**
     * returns the date when the task is due in ms since January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     * @return due date
     */
    public long getTaskDueDate() {
        Log.d(LOG_TAG,"getting task due date");
        return mTaskDueDate;
    }

    /**
     * sets date when the task is due in ms since January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     * @param mTaskDueDate new task due date
     */
    public void setTaskDueDate(long mTaskDueDate) {
        Log.d(LOG_TAG,"setting task due date");
        this.mTaskDueDate = mTaskDueDate;
    }

    /**
     * task due date in ms since January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     */
    @JsonProperty("taskDueDate")
    private long mTaskDueDate;

    /**
     * constructor setting title, text and flag for task completion
     * @param mTitle title of the new task
     * @param mText text of the new task
     * @param mDone flag if task is done or not
     */
    public cTimedTask(UUID mId, String mTitle, String mText, boolean mDone) {
        super(mId,mTitle, mText, mDone);
        Log.d(LOG_TAG,"creating timed task");
    }

    /**
     * needed for deserialization by JACKSON
     */
    public cTimedTask() {
        super();
    }

    /**
     * used to delete timer referenced in this task
     */
    @Override
    public void deleteTask() {
        Log.d(LOG_TAG,"deleting timed task");
        // TODO: delete timer
    }


    @Override
    public int getVersion() {
        return 1;
    }

}
