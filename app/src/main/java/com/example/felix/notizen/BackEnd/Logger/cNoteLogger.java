package com.example.felix.notizen.BackEnd.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created as part of notes in package com.example.felix.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 03/06/17.
 */
@SuppressWarnings("unused")
public class cNoteLogger {

    /**
     * current debug level of logger
     * 0: NONE
     * 1: Only Errors
     * 2: Warning
     * 3: Info
     */
    private int mCurrentDebugLevel = 0;
    /**
     * debug level const none
     */
    private final int mDebugLevelNone = 0;
    /**
     * debug level const error
     */
    private final int mDebugLevelError = 1;
    /**
     * debug level const warning
     */
    private final int mDebugLevelWarning = 2;
    /**
     * debug level const info
     */
    private final int mDebugLevelInfo = 3;
    /**
     * level debug
     */
    private final int mDebugLevelDebug = 4;

    private String[] mDebugLevelStrings = {"[NON]","[ERR]","[WAR]","[INF]","[DEB]"};

    private ArrayList<cLogEntry> logEntries;

    private int entriesBeforeLogFlush = 1;

    /**
     * TODO set location of LogFile
     */
    private String logFileLocation = "";

    File f;

    /**
     * loggs a message
     * @param message
     * @param level
     */
    private void log(String message, int level){
        if (mCurrentDebugLevel >=  level) {
            logEntries.add(new cLogEntry(message, level));
            writeMessagesToFile();
        }
    }

    public void logError(String message){
        log(message,mDebugLevelError);
    }
    public void logWarning(String message){
        log(message,mDebugLevelWarning);
    }
    public void logInfo(String message){
        log(message,mDebugLevelInfo);
    }
    public void logDebug(String message){
        log(message,mDebugLevelDebug);
    }
    public void logNone(String message){
        log(message,mDebugLevelNone);
    }


    private static final cNoteLogger ourInstance = new cNoteLogger();

    public static cNoteLogger getInstance() {
        return ourInstance;
    }

    private cNoteLogger() {
        logEntries = new ArrayList<cLogEntry>();

    }

    private void writeMessagesToFile(){
        if (entriesBeforeLogFlush <logEntries.size()){
            flush();
        }
    }

    public void init(String logLocation,int debugLevel){
        // TODO add timestamp to file name
        this.logFileLocation = logLocation;
        f = new File(logLocation);
        this.mCurrentDebugLevel = debugLevel;
        logNone("logger initialized; debug level: "+ String.valueOf(debugLevel));
    }

    /**
     * builds a string containing the info of logEntry
     * eg:
     * 2017-06-01 16:05:17:123 [INFO]: func created
     * @param logEntry log entry with all info (message, timestamp, log-level
     * @return string of log entry ready to put in log-file
     */
    private String getFormattedLogEntry(cLogEntry logEntry){
        //TODO make timestamp of format yyyy-mm-dd hh:mm:ss:ms
        String logMessage = String.format("%d - %s: %s\n",logEntry.getTimeStamp(),mDebugLevelStrings[logEntry.getLogLevel()],logEntry.getMessage());
        return logMessage;
    }

    public void flush() {
        Iterator iterator = logEntries.iterator();
        FileWriter fr = null;
        try {
            fr = new FileWriter(f,true);
            while (iterator.hasNext()){
                fr.write(getFormattedLogEntry((cLogEntry)iterator.next()));
            }
            fr.close();
            logEntries.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
