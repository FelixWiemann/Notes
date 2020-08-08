package com.nepumuk.notizen.objects.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nepumuk.notizen.utils.DateStrategy;

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
public class TimedTask extends BaseTask {


    public static String aTYPE = "cTimedTask";

    private final static String LOG_TAG = "TIMED_TASK";

    /**
     * returns the date when the task is due in ms since January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     * @return due date
     */
    public long getTaskDueDate() {
        return mTaskDueDate;
    }

    /**
     * sets date when the task is due in ms since January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     * @param mTaskDueDate new task due date
     */
    public void setTaskDueDate(long mTaskDueDate) {
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
    public TimedTask(UUID mId, String mTitle, String mText, boolean mDone) {
        super(mId,mTitle, mText, mDone);
    }

    /**
     * needed for deserialization by JACKSON
     */
    public TimedTask() {
        super();
    }

    /**
     * used to delete timer referenced in this task
     */
    @Override
    public void deleteTask() {
        // TODO: delete timer
    }


    @Override
    public int getVersion() {
        return 1;
    }

}
