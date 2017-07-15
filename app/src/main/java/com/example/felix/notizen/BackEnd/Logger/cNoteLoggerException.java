package com.example.felix.notizen.BackEnd.Logger;

import com.example.felix.notizen.BackEnd.cNoteException;

/**
 * Created as part of notes in package com.example.felix.notizen.BackEnd.Logger
 * by Felix "nepumuk" Wiemann on 10/06/17.
 */
@SuppressWarnings("unused")
public class cNoteLoggerException extends cNoteException {
    public static final String aERROR_OPENING_FILE = "<ERROR OPENING FILE>";
    public static final String aCOULD_NOT_WRITE_TO_FILE = "<COULD NOT WRITE TO FILE>";
    public static final String aCOULD_NOT_LOG_MESSAGE = "<COULD NOT LOG TO MESSAGE>";


    public cNoteLoggerException(String location, String message, Exception cause) {
        super(location, message, cause);
    }

    /**
     * abstract function to be called instead of throw
     *
     * @throws cNoteException
     */
    @Override
    public void raise() throws cNoteException {
        logException();
        throw this;
    }
}
