package com.example.felix.notizen.BackEnd;

import android.content.Context;

/**
 * context manager for storing the context of the app and making it accessible throughout the app
 * to be set up at app start / start of first service
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 04/06/17.
 */
@SuppressWarnings("unused")
class cContextManager {

    Context context;

    private static final cContextManager ourInstance = new cContextManager();

    static cContextManager getInstance() {
        return ourInstance;
    }

    private cContextManager() {
    }

    public void setUp(Context context) throws cContextManagerException {
        if (context == null) {
            this.context = context;
        }
        else {
            new cContextManagerException("",cContextManagerException.aContextAlreadySet,null).raise();
        }
    }

    public Context getContext(){
        return context;
    }



}
