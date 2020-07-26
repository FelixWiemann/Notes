package com.nepumuk.notizen.objects.tasks;

import android.util.Log;

import com.nepumuk.notizen.utils.DateStrategy;
import com.nepumuk.notizen.objects.cStorageObject;
import com.nepumuk.notizen.objects.filtersort.SortAble;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

import static com.nepumuk.notizen.objects.filtersort.SortCategory.TASK_DONE_STATE;
import static com.nepumuk.notizen.objects.filtersort.SortCategory.TASK_DONE_TIME;

/**
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
public abstract class cBaseTask extends cStorageObject {

    private static final String BASE_TASK_LOG_TAG = "BaseTask";

    /**
     * text of the task
     */
    @JsonProperty("Text")
    private String mText;
    /**
     * flag whether task is marked as done
     * true -> task done
     * false -> task open
     */
    @JsonProperty("Done")
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
    public cBaseTask(UUID mId, String mTitle, String mText, final boolean mDone){
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
        this.addSortable(TASK_DONE_STATE, new SortAble<Boolean>() {
            @Override
            public Boolean getData() {
                return mDone;
            }
        });
    }

    /**
     * needed for deserialization by JACKSON
     */
    public cBaseTask() {
        super();
    }

    /**
     * returns the flag of task completion
     * @return true, if task is complete, false if not
     */
    @JsonIgnore
    public boolean isDone() {
        Log.d(BASE_TASK_LOG_TAG,"is task done?");
        return mDone;
    }

    /**
     * sets the flag of task completion
     * @param done true if task is complete, false if not
     */
    public void setDone(boolean done) {
        Log.d(BASE_TASK_LOG_TAG,"setting task done");
        setLastChangedDate();
        this.mDone = done;
        if (done){
            mTaskCompleteDate = DateStrategy.getCurrentTime();
        }else{
            mTaskCompleteDate = -1;
        }
    }

    /**
     * returns the text of the task
     * @return text of the task
     */
    @JsonIgnore
    public String getText() {
        return mText;
    }

    /**
     * set the text of the task
     * @param mText new text of task
     */
    public void setText(String mText) {
        setLastChangedDate();
        this.mText = mText;
    }

    @JsonIgnore
    public long getTaskCompleteDate(){
        return mTaskCompleteDate;
    }


    /**
     * abstract method to implement in each inherited task type.
     * used for special deletion things
     * e.g. timed task: delete timer service associated with it
     */
    public abstract void deleteTask();

}
