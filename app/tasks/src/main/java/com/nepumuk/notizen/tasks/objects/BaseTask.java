package com.nepumuk.notizen.tasks.objects;

import androidx.annotation.Keep;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nepumuk.notizen.core.filtersort.SortCategory;
import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.utils.DateStrategy;

/**
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 14/04/17.
 */
@Keep
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
public abstract class BaseTask extends StorageObject {

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
    public BaseTask(String mId, String mTitle, String mText, final boolean mDone){
        super(mId,mTitle);
        this.mText = mText;
        this.mDone = mDone;
        initSortables();
    }
    /**
     * copy constructor
     *
     * makes a deep clone of the given object
     * @param other to copy from
     */
    public BaseTask(BaseTask other){
        super(other);
        this.mText = other.mText;
        this.mDone = other.mDone;
        this.mTaskCompleteDate = other.mTaskCompleteDate;
        initSortables();
    }

    private void initSortables(){
        this.addSortable(SortCategory.TASK_DONE_TIME, () -> mTaskCompleteDate);
        this.addSortable(SortCategory.TASK_DONE_STATE, () -> mDone);
    }

    /**
     * needed for deserialization by JACKSON
     */
    public BaseTask() {
        super();
    }

    /**
     * returns the flag of task completion
     * @return true, if task is complete, false if not
     */
    @JsonIgnore
    public boolean isDone() {
        return mDone;
    }

    /**
     * sets the flag of task completion
     * @param done true if task is complete, false if not
     */
    public void setDone(boolean done) {
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

    /**
     * create a deep copy of itself
     * @return deep copy
     */
    public abstract BaseTask deepCopy();


}
