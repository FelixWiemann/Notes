package com.example.felix.notizen.BackEnd;

import android.content.Context;

/**
 * mContext manager for storing the mContext of the app and making it accessible throughout the app
 * to be set up at app start / start of first service
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 04/06/17.
 */
@SuppressWarnings("unused")
public class cContextManager {
    /**
     * mContext variable
     */
    private Context mContext;

    /**
     * mContext of application
     */
    private static final cContextManager mContextManagerInstance = new cContextManager();

    /**
     * get the instance of ContextManager
     * @return
     */
    public static cContextManager getInstance() {
        return mContextManagerInstance;
    }

    private cContextManager() {
    }

    public void setUp(Context context) throws cContextManagerException {
        if (context == null) {
            this.mContext = context;
        }
        else {
            new cContextManagerException("Context Manager setUp",cContextManagerException.aCONTEXT_ALREADY_SET,null).raise();
        }
    }

    /**
     * get the context of the application
     * @return context
     */
    public Context getContext(){
        return mContext;
    }



}
