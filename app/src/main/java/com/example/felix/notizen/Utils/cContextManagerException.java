package com.example.felix.notizen.Utils;


/**
 * exception thrown by cContextManager
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 04/06/17.
 */
public class cContextManagerException extends Exception {
    public static final String aCONTEXT_ALREADY_SET = "<CONTEXT IS ALREADY SET>";


    public cContextManagerException(String message,Throwable cause){
        super(message,cause);
    }
}
