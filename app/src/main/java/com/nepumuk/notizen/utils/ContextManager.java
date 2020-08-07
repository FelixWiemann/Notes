package com.nepumuk.notizen.utils;

import android.content.Context;

/**
 * mContext manager for storing the mContext of the app and making it accessible throughout the app
 * to be set up at app start / start of first service
 *
 * Created as part of notes in package com.nepumuk.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 04/06/17.
 *
 * getApplicationContext instead!
 */
@Deprecated
public class ContextManager {
    /**
     * mContext variable
     */
    private Context mContext;

    /**
     * mContext of application
     */
    private static final ContextManager mContextManagerInstance = new ContextManager();

    /**
     * get the instance of ContextManager
     * @return instance of context manager
     */
    public static ContextManager getInstance() {
        return mContextManagerInstance;
    }

    private ContextManager() {
        super();
    }

    /**
     * sets up the context manager for storing the context
     * @param context context to be stored
     * @throws ContextManagerException if context already set
     */
    public void setUp(Context context) throws ContextManagerException {
        // only set context, if not set already
        if (mContext == null) {
            this.mContext = context;
        }
        else {
            // otherwise throw exception
            throw new ContextManagerException(ContextManagerException.aCONTEXT_ALREADY_SET,null);
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
