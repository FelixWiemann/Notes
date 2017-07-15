package com.example.felix.notizen.BackEnd;

import android.util.Log;

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
public abstract class cNoteException extends Exception {

    private String aIndent = "    ";
    private String aBlank = " ";
    private String aLOCATION_TAG = "Location:";
    private String aMESSAGE_TAG = "Message:";
    private String aCLASS_TAG = "Class:";
    private String aTIME_TAG = "Time:";
    private String aSEPARATOR = "------------------------------";
    private String aADDITIONAL_DATA_TAG = "--Begin Additional Data--";
    private String aEND_ADDITIONAL_DATA_TAG = "--End Additional Data--";

    private String mLocation;
    private Exception mCause;
    private long mTimeStamp;
    cNoteLogger log;

    public cNoteException(String location, String message, Exception cause){
        super(message);
        log = cNoteLogger.getInstance();
        this.mCause = cause;
        this.mTimeStamp= (new Date()).getTime();
        this.mLocation = location;
    }

    private String exceptionOutput(String indent){
        String causeOut = indent + "<NO CAUSE KNOWN>";
        if (mCause != null){
            if (mCause instanceof cNoteException) {
                causeOut = ((cNoteException)mCause).exceptionOutput(indent + aIndent);
            }
            else {
                causeOut  = mCause.getMessage();
            }

        }
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
        return output;
    }


    public void logException() {
        log.logError(exceptionOutput(aIndent));
    }

    /**
     * abstract function to be called instead of throw
     * @throws cNoteException
     */
    public abstract void raise() throws cNoteException;
}
