package com.example.felix.notizen.objects.Task;

import java.util.UUID;

/**
 * Implementation class for simple tasks containing only title, text and whether it is done
 *
 * Created as part of notes in package com.example.felix.notizen.FrontEnd.Task
 * by Felix "nepumuk" Wiemann on 15/07/17.
 */
@SuppressWarnings("unused")
public class cTask extends cBaseTask {

    public static String aTYPE = "cTask";
    /**
     * constructor setting title, text and flag for task completion
     *
     * @param mTitle title of the new task
     * @param mText  text of the new task
     * @param mDone  flag if task is done or not
     */
    public cTask(UUID mId, String mTitle, String mText, boolean mDone) {
        super(mId, mTitle, mText, mDone);
    }


    public cTask(UUID pID) {
        super(pID);
    }

    public cTask() {
        super();
    }
    /**
     * abstract method to implement in each inherited task type.
     * used for special deletion things
     * e.g. timed task: delete timer service associated with it
     */
    @Override
    public void deleteTask() {

    }

    @Override
    public int getVersion() {
        return 1;
    }
}
