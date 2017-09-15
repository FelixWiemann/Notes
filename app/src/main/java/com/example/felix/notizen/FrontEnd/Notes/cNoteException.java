package com.example.felix.notizen.FrontEnd.Notes;


import com.example.felix.notizen.BackEnd.cBaseException;

/**
 * Created by DEU216269 on 15.09.2017.
 */

public class cNoteException extends cBaseException {

    public static final String aCREATION_DATE_ALREDY_SET = "aCREATION_DATE_ALREDY_SET";

    public cNoteException(String location, String message, cBaseException cause) {
        super(location, message, cause);
    }

    @Override
    public void raise() throws cBaseException {

    }
}
