package com.example.felix.notizen.Utils.Logger;

import com.example.felix.notizen.Utils.cBaseException;

/**
 * Note Logger Exceptions used in Note Logger.
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd.Logger
 * by Felix "nepumuk" Wiemann on 10/06/17.
 */
@SuppressWarnings("unused")
public class cNoteLoggerException extends cBaseException {

    /**
     * error opening the file
     */
    public static final String aERROR_OPENING_FILE = "<ERROR OPENING FILE>";

    /**
     * could not write the file
     */
    public static final String aCOULD_NOT_WRITE_TO_FILE = "<COULD NOT WRITE TO FILE>";

    /**
     * could not log a message
     */
    public static final String aCOULD_NOT_LOG_MESSAGE = "<COULD NOT LOG TO MESSAGE>";

    /**
     * constructor
     * @param location
     * @param message
     * @param cause
     */
    public cNoteLoggerException(String location, String message, cBaseException cause) {
        super(location, message, cause);
    }

}
