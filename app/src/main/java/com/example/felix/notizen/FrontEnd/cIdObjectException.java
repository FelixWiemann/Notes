package com.example.felix.notizen.FrontEnd;

import com.example.felix.notizen.BackEnd.cNoteException;

/**
 * Created as part of notes in package com.example.felix.notizen.FrontEnd
 * by Felix "nepumuk" Wiemann on 10/06/17.
 */
@SuppressWarnings("unused")
public class cIdObjectException extends cNoteException {

    public static final String aID_ALREADY_SET_EXCEPTION = "<ID_ALREADY_SET_EXCEPTION>";


    public cIdObjectException(String location, String message, Exception cause) {
        super(location, message, cause);
    }

    /**
     * abstract function to be called instead of throw
     *
     * @throws cNoteException
     */
    @Override
    public void raise() throws cIdObjectException {
        logException();
        throw this;
    }
}
