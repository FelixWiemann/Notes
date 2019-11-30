package com.example.felix.notizen.objects.Task;

import android.util.Log;

import com.example.felix.notizen.Utils.DateStrategy;
import com.example.felix.notizen.objects.cStorageObject;
import com.example.felix.notizen.views.viewsort.SortAble;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import static com.example.felix.notizen.views.viewsort.SortCategory.TASK_DONE_TIME;

/**
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 */
@SuppressWarnings("unused")
public abstract class cBaseTask extends cStorageObject {

    public static final String BASE_TASK_LOG_TAG = "BaseTask";
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
     * date of creation, numbers of milliseconds after January 1, 1970 00:00:00 GMT
     * @see DateStrategy#getCurrentTime()
     */
    @JsonProperty("taskCompleteDate")
    private long mTaskCompleteDate = -1;

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
        setCreationDate();
        setLastChangedDate();
        this.addSortable(TASK_DONE_TIME, new SortAble<Long>() {
            @Override
            public Long getData() {
                return mTaskCompleteDate;
            }
        });
        Log.d(BASE_TASK_LOG_TAG,"creating cBaseTask");
    }

    public cBaseTask(UUID pID) {
        super();
        // TODO does nothing with the UUID?
    }

    public cBaseTask() {
    }

    /**
     * returns the flag of task completion
     * @return true, if task is complete, false if not
     */
    public boolean isDone() {
        Log.d(BASE_TASK_LOG_TAG,"is task done?");
        return mDone;
    }

    /**
     * sets the flag of task completion
     * @param mDone true if task is complete, false if not
     */
    public void setDone(boolean mDone) {
        Log.d(BASE_TASK_LOG_TAG,"setting task done");
        setLastChangedDate();
        this.mDone = mDone;
        if (mDone){
            mTaskCompleteDate = DateStrategy.getCurrentTime();
        }else{
            mTaskCompleteDate = -1;
        }
    }

    /**
     * returns the text of the task
     * @return text of the task
     */
    public String getText() {
        Log.d(BASE_TASK_LOG_TAG,"getting task text");
        return mText;
    }

    /**
     * set the text of the task
     * @param mText new text of task
     */
    public void setText(String mText) {
        Log.d(BASE_TASK_LOG_TAG,"setting task text");
        setLastChangedDate();
        this.mText = mText;
    }

    public long getTaskCOmpleteDate(){
        return mTaskCompleteDate;
    }


    /**
     * abstract method to implement in each inherited task type.
     * used for special deletion things
     * e.g. timed task: delete timer service associated with it
     */
    public abstract void deleteTask();

}
