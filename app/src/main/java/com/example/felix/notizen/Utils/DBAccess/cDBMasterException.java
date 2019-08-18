package com.example.felix.notizen.Utils.DBAccess;


import com.example.felix.notizen.Utils.cBaseException;

/**
 * NOT IN USE ANYMORE
 * DECIDED AGAINST DB USAGE; BUT JSON FILE
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd.DBAccess
 * by Felix "nepumuk" Wiemann on 10/06/17.
 */
@SuppressWarnings("unused")
public class cDBMasterException extends cBaseException {

    public static final String aSQL_CONNECTION_ALREADY_OPEN = "<SQL CONNECTION IS ALREADY OPEN>";


    public cDBMasterException(String location, String message, cBaseException cause) {
        super(location, message, cause);
    }

}
