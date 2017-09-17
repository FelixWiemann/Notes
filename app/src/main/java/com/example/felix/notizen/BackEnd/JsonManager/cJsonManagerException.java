package com.example.felix.notizen.BackEnd.JsonManager;

import com.example.felix.notizen.BackEnd.cBaseException;

/**
 * Exception used in JsonManager
 */

public class cJsonManagerException extends cBaseException {

    /**
     * file not found exception
     */
    public static final String aFILE_NOT_FOUND_EXCEPTION = "aFILE_NOT_FOUND_EXCEPTION";
    /**
     * unsupported encoding
     */
    public static final String aUNSUPPORTED_ENCODING = "aUNSUPPORTED_ENCODING";
    /**
     * reading the file failed
     */
    public static final String aREAD_FAILED = "aREAD_FAILED";

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
