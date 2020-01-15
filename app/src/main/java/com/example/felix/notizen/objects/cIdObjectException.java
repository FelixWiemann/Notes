package com.example.felix.notizen.objects;

import com.example.felix.notizen.Utils.cBaseException;

/**
 * Created as part of notes in package com.example.felix.notizen.FrontEnd
 * by Felix "nepumuk" Wiemann on 10/06/17.
 */
public class cIdObjectException extends cBaseException {

    public static final String aID_ALREADY_SET_EXCEPTION = "<ID_ALREADY_SET_EXCEPTION>";


    public cIdObjectException(String location, String message, cBaseException cause) {
        super(location, message, cause);
    }

}
