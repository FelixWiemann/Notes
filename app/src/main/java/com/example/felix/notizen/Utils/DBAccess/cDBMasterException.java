package com.example.felix.notizen.Utils.DBAccess;


/**
 * Created as part of notes in package com.example.felix.notizen.BackEnd.DBAccess
 * by Felix "nepumuk" Wiemann on 10/06/17.
 */
public class cDBMasterException extends Exception {

    public static final String aSQL_CONNECTION_ALREADY_OPEN = "<SQL CONNECTION IS ALREADY OPEN>";


    public cDBMasterException(String message, Throwable cause) {
        super(message, cause);
    }
}
