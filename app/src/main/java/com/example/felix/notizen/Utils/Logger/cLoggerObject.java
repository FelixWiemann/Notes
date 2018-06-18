package com.example.felix.notizen.Utils.Logger;

/**
 * Created by Felix on 18.06.2018.
 */

public class cLoggerObject {
    /**
     * logger instance for logging everything
     */
    private cNoteLogger logger;

    public cLoggerObject (){
        logger = cNoteLogger.getInstance();
    }

    public void logInfo(String Message){
        logger.logInfo(Message);
    }
    public void logError(String Message){
        logger.logError(Message);
    }
    public void logWarning(String Message){
        logger.logWarning(Message);
    }
    public void logDebug(String Message){
        logger.logDebug(String.format("%s",Message));
    }

}
