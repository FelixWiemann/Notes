package com.nepumuk.notizen.utils.db_access;


/**
 * Created as part of notes in package com.example.felix.notizen.BackEnd.DBAccess
 * by Felix "nepumuk" Wiemann on 10/06/17.
 */
public class DbMasterException extends Exception {

    public static final String aSQL_CONNECTION_ALREADY_OPEN = "<SQL CONNECTION IS ALREADY OPEN>";


    public DbMasterException(String message, Throwable cause) {
        super(message, cause);
    }
}
