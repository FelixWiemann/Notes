package com.example.felix.notizen.BackEnd;

import java.util.Date;
import com.example.felix.notizen.BackEnd.Logger.cNoteLogger;

/**
 * basic exception for exception handling inside note - application
 *
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 03/06/17.
 */
@SuppressWarnings("unused")
public abstract class cBaseException extends Exception {

    // constants used in exceptions
    private final String aIndent = "    ";
    private final String aBlank = " ";
    private final String aLOCATION_TAG = "Location:";
    private final String aMESSAGE_TAG = "Message:";
    private final String aCLASS_TAG = "Class:";
    private final String aTIME_TAG = "Time:";
    private final String aSEPARATOR = "------------------------------";
    private final String aADDITIONAL_DATA_TAG = "--Begin Additional Data--";
    private final String aEND_ADDITIONAL_DATA_TAG = "--End Additional Data--";

    // local vars for excepts
    private String mLocation;
    private Exception mCause;
    private long mTimeStamp;
    // logger instnace
    cNoteLogger log;

    // constructor
    public cBaseException(String location, String message, Exception cause){
        super(message);
        log = cNoteLogger.getInstance();
        this.mCause = cause;
        this.mTimeStamp= (new Date()).getTime();
        this.mLocation = location;
    }

    // get the output of all exceptions
    private String exceptionOutput(String indent){
        // set default to no cause known
        // TODO set constant
        String causeOut = indent + "<NO CAUSE KNOWN>";
        // if not null
        if (mCause != null){
            // in case of cBaseException get output of cause
            if (mCause instanceof cBaseException) {
                causeOut = ((cBaseException)mCause).exceptionOutput(indent + aIndent);
            }
            else {
                // else get the message
                causeOut  = mCause.getMessage();
            }
        }
        // built the output
        String output = String.format(
                        "\n%s%s%s%s\n" +
                        "%s%s%s%d\n"+
                        "%s%s%s%s\n"+
                        "%s%s\n" +
                        "%s%s\n" +
                        "%s%s\n",
                indent,aLOCATION_TAG,aBlank,mLocation,
                indent,aTIME_TAG,aBlank,mTimeStamp,
                indent,aMESSAGE_TAG,aBlank,getMessage(),
                indent,aADDITIONAL_DATA_TAG,
                indent,causeOut,
                indent,aEND_ADDITIONAL_DATA_TAG
                );
        // return output
        return output;
    }

    /**
     * log the exception
     */
    public void logException() {
        // log by logging the output
        log.logError(exceptionOutput(aIndent));
    }

    /**
     * TODO remove
     * abstract function to be called instead of throw
     * @throws cBaseException
     */
    public abstract void raise() throws cBaseException;
}
