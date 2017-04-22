package com.example.felix.notizen.FrontEnd.Task;

/**
 *
 *
 *
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 */

public abstract class cTask {

    /**
     *
     */
    private String mTitle;
    /**
     *
     */
    private String mText;
    /**
     *
     */
    private boolean mDone;

    /**
     * @param mTitle
     * @param mText
     * @param mDone
     */
    public cTask(String mTitle, String mText, boolean mDone){
        this.mTitle = mTitle;
        this.mText = mText;
        this.mDone = mDone;
    }

    /**
     * @return
     */
    public boolean isDone() {
        return mDone;
    }

    /**
     * @param mDone
     */
    public void setDone(boolean mDone) {
        this.mDone = mDone;
    }

    /**
     * @return
     */
    public String getText() {
        return mText;
    }

    /**
     * @param mText
     */
    public void setText(String mText) {
        this.mText = mText;
    }

    /**
     * @return
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * @param mTitle
     */
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * abstract method to implement in each inherited task type.
     * used for special deletion things
     * e.g. timed task: delete timer service associated with it
     */
    public abstract void deleteTask();


}
