package com.example.felix.notizen.Utils;

import android.util.Log;

/**
 * Created by Felix on 18.06.2018.
 */
@Deprecated
public class cLoggerObject {

    private static final String TAG = "LoggerObject" ;
    /**
     * TODO remove this object, as it clutters the hierarchy and makes it more complex, is unnecessary
     */
    @Deprecated
    public void logDebug(String Message){
        Log.d(TAG, "logDebug: " + Message);
    }

}
