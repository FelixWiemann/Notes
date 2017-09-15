package com.example.felix.notizen.BackEnd.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * logger class for logging into a log file
 * logger decides on set log level whether to log the given message or not if level is below log level
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 03/06/17.
 */
@SuppressWarnings("unused")
public class cNoteLogger {

    /**
     * timestamp format used for logging
     */
    public static final String aDATE_FORMAT_USED_FOR_LOGGING = "yyyy-mm-dd hh:mm:ss:SSS";

    /**
     * file name to use for logging
     */
    public static final String aLOG_FILE_NAME = "NOTE_APPLICATION_LOG_";

    /**
     * file type used for logging
     */
    public static final String aLOG_FILE_TYPE = ".log";

    /**
     * current debug level of logger
     * 0: NONE
     * 1: Only Errors
     * 2: Warning
     * 3: Info
     * 4: Debug
     */
    private int mCurrentDebugLevel = 0;

    /**
     * debug level const none
     */
    public static final int mDebugLevelNone = 0;

    /**
     * debug level const error
     */
    public static final int mDebugLevelError = 1;

    /**
     * debug level const warning
     */
    public static final int mDebugLevelWarning = 2;

    /**
     * debug level const info
     */
    public static final int mDebugLevelInfo = 3;

    /**
     * level debug
     */
    public final int mDebugLevelDebug = 4;

    /**
     * available log entries
     * [NON] = none
     * [ERR] = error
     * [WAR] = warning
     * [INF] = info
     * [DEB] = debug
     */
    private static final String[] mDebugLevelStrings = {"[NON]","[ERR]","[WAR]","[INF]","[DEB]"};

    /**
     * array of log entries
     */
    private ArrayList<cLogEntry> mLogEntries;

    /**
     * TODO: make part of init
     * number of entries inside the logger until the log messages get written to file
     */
    private int mEntriesBeforeLogFlush = 10;

    /**
     * location logger logs to. To be set in init
     */
    private String mLogFileLocation;

    /**
     * log file to be logged into
     */
    private File mLogFile;

    /**
     * logs a message into the buffer, if the level of the message is to be logged
     * @param message message string of the log message
     * @param level level of the message
     */
    private void log(String message, int level){
        // if current debug level is bigger than level of message; then debug
        // e.g: current debug level = 1; levels 2 and bigger are not stored
        if (mCurrentDebugLevel >=  level) {
            // add entry to entries
            mLogEntries.add(new cLogEntry(message, level));
            // write to file
            try {
                writeMessagesToFile();
            } catch (cNoteLoggerException e) {
                cNoteLoggerException ex = new cNoteLoggerException(cNoteLoggerException.aCOULD_NOT_WRITE_TO_FILE
                        , String.format("Failed to log %s with level %i", message, level)
                        ,e);
                // TODO write not to file, as it already failed!
            }
        }
    }

    /**
     * function used for logging as error
     * error level gets set automatically
     * @param message error message
     */
    public void logError(String message){
        log(message,mDebugLevelError);
    }

    /**
     * function used for logging as warning
     * warning level gets set automatically
     * @param message warning message
     */
    public void logWarning(String message){
        log(message,mDebugLevelWarning);
    }

    /**
     * function used for logging as info
     * info level gets set automatically
     * @param message info message
     */
    public void logInfo(String message){
        log(message,mDebugLevelInfo);
    }

    /**
     * function used for logging as debug
     * debug level gets set automatically
     * @param message debug message
     */
    public void logDebug(String message){
        log(message,mDebugLevelDebug);
    }

    /**
     * function used for logging as none
     * none level gets set automatically
     * @param message none message
     */
    public void logNone(String message){
        log(message,mDebugLevelNone);
    }

    /**
     * instance of logger
     * to be accessed via getter
     * no other instances inside running application to be created!
     */
    private static final cNoteLogger mGlobalLoggerInstance = new cNoteLogger();

    /**
     * gets the global logger instance.
     * only instance to be used
     * @return global logger instance
     */
    public static cNoteLogger getInstance() {
        return mGlobalLoggerInstance;
    }

    /**
     * private constructor
     */
    private cNoteLogger() {
        mLogEntries = new ArrayList<cLogEntry>();
    }

    /**
     * writes all messages to the file, if the buffer of messages is bigger than the number of messages to flush
     */
    private void writeMessagesToFile() throws cNoteLoggerException {
        if (mEntriesBeforeLogFlush < mLogEntries.size()){
            try {
                flush();
            } catch (cNoteLoggerException e) {
                throw new cNoteLoggerException("",cNoteLoggerException.aCOULD_NOT_WRITE_TO_FILE,e);
            }
        }
    }

    /**
     * init the logger with log file location and debug level
     * @param logLocation location to store log file
     * @param debugLevel log level to use
     */
    public void init(String logLocation,int debugLevel){
        // TODO log file handling to avoid space clogging on device
        String formattedDate = (new SimpleDateFormat("yyyyMMdd_hhmmss")).format(new Date());
        this.mLogFileLocation = logLocation +"/"+ aLOG_FILE_NAME + formattedDate + aLOG_FILE_TYPE;
        mLogFile = new File(mLogFileLocation);
        this.mCurrentDebugLevel = debugLevel;
        // log init finished
        logNone("logger initialized");
        logNone("debug level: "+ String.valueOf(debugLevel));
        logNone("log-file location: "+logLocation);
    }

    /**
     * builds a string containing the info of logEntry
     * eg:
     * 2017-06-01 16:05:17:123 [INFO]: func created
     * @param logEntry log entry with all info (message, timestamp, log-level)
     * @return string of log entry ready to put in log-file
     */
    private String getFormattedLogEntry(cLogEntry logEntry){
        // get date of timestamp
        Date d = new Date(logEntry.getTimeStamp());
        // log level of entry
        int lglvl = logEntry.getLogLevel();
        // format date into given format
        String formattedDate = (new SimpleDateFormat(aDATE_FORMAT_USED_FOR_LOGGING)).format(d);
        // build log message
        String logMessage = String.format("%s - %s: %s\n",formattedDate,mDebugLevelStrings[lglvl],logEntry.getMessage());
        return logMessage;
    }

    /**
     * flush all entries into the file
     * @throws cNoteLoggerException
     */
    public void flush() throws cNoteLoggerException {
        Iterator iterator = mLogEntries.iterator();
        FileWriter fr = null;
        try {
            fr = new FileWriter(mLogFile,true);
            while (iterator.hasNext()){
                fr.write(getFormattedLogEntry((cLogEntry)iterator.next()));
            }
            fr.close();
            mLogEntries.clear();
        } catch (IOException e) {
            e.printStackTrace();
            throw new cNoteLoggerException("log flush",cNoteLoggerException.aERROR_OPENING_FILE,null);
        }
    }
}
