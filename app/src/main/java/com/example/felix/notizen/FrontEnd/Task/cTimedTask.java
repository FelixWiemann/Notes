package com.example.felix.notizen.FrontEnd.Task;

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
     *
     */
    private long mTaskDueDate;

    /**
     * @param mTitle
     * @param mText
     * @param mDone
     */
    public cTimedTask(String mTitle, String mText, boolean mDone) {
        super(mTitle, mText, mDone);
    }

    /**
     * used to delete timer referenced in this task
     */
    @Override
    public void deleteTask() {
        // TODO: delete timer
    }


}
