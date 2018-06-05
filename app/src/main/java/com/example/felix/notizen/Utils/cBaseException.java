package com.example.felix.notizen.Utils;

import java.util.Date;
import java.util.HashMap;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;

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

    private final String AdditionalDataSeparator = ":";
    private static final String aNo_Cause_Known = "<NO CAUSE KNOWN>";

    private static final String StackTrace_Key = "STACKTRACE";
    private HashMap<String,String> mAdditionalData = new HashMap<>();

    // local vars for excepts
    private String mLocation;
    private Exception mCause;
    private long mTimeStamp;
    // logger instance
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
        String causeOut = indent + aNo_Cause_Known;
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
        // build the output
        String output = String.format(
                        "\n%s%s%s%s\n" +
                        "%s%s%s%d\n"+
                        "%s%s%s%s\n"+
                        "%s%s\n",
                indent,aLOCATION_TAG,aBlank,mLocation,
                indent,aTIME_TAG,aBlank,mTimeStamp,
                indent,aMESSAGE_TAG,aBlank,getMessage(),
                indent,aADDITIONAL_DATA_TAG
                );
        // add additional data
        for (String key:mAdditionalData.keySet()
             ) {
           output = output + indent + aIndent +  key + AdditionalDataSeparator + mAdditionalData.get(key) + "\n";
        }
        // add output of origin exception
        output += indent + causeOut + "\n";
        output += indent + aEND_ADDITIONAL_DATA_TAG;
        // return output
        return output;
    }

    /**
     * log the exception
     */
    public void logException() {
        // generate StackTrace
        String trace = this.StackTraceToString(aIndent+aIndent + aIndent);
        // add to additional data
        this.addAdditionalData(StackTrace_Key,trace);
        // log by logging the output
        log.logError(exceptionOutput(aIndent));
    }

    /**
     * adds additional data to this exception
     * @param key
     * @param Value
     */
    public void addAdditionalData(String key, String Value){
        mAdditionalData.put(key,Value);
    }

    private String StackTraceToString(String indent){
        String stackTrace = "\n";
        for ( StackTraceElement trace : Thread.currentThread().getStackTrace() )
            stackTrace += indent + trace.toString()+"\n";
        return stackTrace;
    }

}
