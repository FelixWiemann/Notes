package com.example.felix.notizen.objects.Notes;


import com.example.felix.notizen.Utils.cBaseException;

/**
 * Created by DEU216269 on 15.09.2017.
 */

public class cNoteException extends cBaseException {

    public static final String aCREATION_DATE_ALREADY_SET = "aCREATION_DATE_ALREADY_SET";

    public cNoteException(String location, String message, cBaseException cause) {
        super(location, message, cause);
    }

}
