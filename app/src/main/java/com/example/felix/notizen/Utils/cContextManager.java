package com.example.felix.notizen.Utils;

import android.content.Context;

/**
 * mContext manager for storing the mContext of the app and making it accessible throughout the app
 * to be set up at app start / start of first service
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 04/06/17.
 *
 * getApplicationContext instead!
 */
@Deprecated
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
     * @return instance of context manager
     */
    public static cContextManager getInstance() {
        return mContextManagerInstance;
    }

    private cContextManager() {
        super();
    }

    /**
     * sets up the context manager for storing the context
     * @param context context to be stored
     * @throws cContextManagerException if context already set
     */
    public void setUp(Context context) throws cContextManagerException {
        // only set context, if not set already
        if (mContext == null) {
            this.mContext = context;
        }
        else {
            // otherwise throw exception
            throw new cContextManagerException(cContextManagerException.aCONTEXT_ALREADY_SET,null);
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
