package com.example.felix.notizen.Utils.JsonManager;

import com.example.felix.notizen.Utils.cBaseException;

/**
 * Exception used in JsonManager
 */

public class cJsonManagerException extends cBaseException {

    /**
     * reading the file failed
     */
    public static final String aREAD_FAILED = "aREAD_FAILED";
    public static final String aWRITING_OBJECTS_OF_TYPE_FAILED = "aWRITING_OBJECTS_OF_TYPE_FAILED";
    public static final String aWRITING_TASKS_FAILED = "aWRITING_TASKS_FAILED";
    public static final String aWRITE_NOTES_FAILED = "aWRITE_NOTES_FAILED";
    public static final String aWRITE_JSON_FAILED = "aWRITE_JSON_FAILED";
    public static final String aREAD_TEXT_NOTE_FAILED = "aREAD_TEXT_NOTE_FAILED";
    public static final String aREAD_NOTES_FAILED = "aREAD_NOTES_FAILED";
    public static final String aREAD_TASKS_FAILED = "aREAD_TASKS_FAILED";


    /**
     * constructor for exceptions
     * @param location
     * @param message
     * @param cause
     */
    public cJsonManagerException(String location, String message, Exception cause) {
        super(location, message, cause);
    }

}
