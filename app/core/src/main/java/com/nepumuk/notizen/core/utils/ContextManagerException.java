package com.nepumuk.notizen.core.utils;


/**
 * exception thrown by cContextManager
 *
 * Created as part of notes in package com.nepumuk.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 04/06/17.
 */
public class ContextManagerException extends Exception {
    public static final String aCONTEXT_ALREADY_SET = "<CONTEXT IS ALREADY SET>";


    public ContextManagerException(String message, Throwable cause){
        super(message,cause);
    }
}
