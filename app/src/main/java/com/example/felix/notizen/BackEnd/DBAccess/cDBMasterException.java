package com.example.felix.notizen.BackEnd.DBAccess;


import com.example.felix.notizen.BackEnd.cNoteException;

/**
 * Created as part of notes in package com.example.felix.notizen.BackEnd.DBAccess
 * by Felix "nepumuk" Wiemann on 10/06/17.
 */
@SuppressWarnings("unused")
public class cDBMasterException extends cNoteException {

    public static final String aSQL_CONNECTION_ALREADY_OPEN = "<SQL CONNECTION IS ALREADY OPEN>";


    public cDBMasterException(String location, String message, Exception cause) {
        super(location, message, cause);
    }

    /**
     * abstract function to be called instead of throw
     *
     * @throws cNoteException
     */
    @Override
    public void raise() throws cNoteException {

    }
}
