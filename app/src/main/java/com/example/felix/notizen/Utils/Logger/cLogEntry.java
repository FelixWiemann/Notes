package com.example.felix.notizen.Utils.Logger;

import java.util.Date;

/**
 * Class for log messages
 * it stores messages, log levels and the timestamp the message was created
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd.Logger
 * by Felix "nepumuk" Wiemann on 03/06/17.
 */
@SuppressWarnings("unused")
public class cLogEntry {
// DO NOT LOG IN THIS CLASS; AS YOU WOULD CREATE INFINITE-LOOPS!!

    /**
     * container for the message of log entry
     */
    private String mMessage = "<EMPTY>";
    /**
     * container for log level of entry
     */
    private int mLogLevel = 0;

    /**
     * timestamp for current log entry
     */
    private long mTimeStamp = 0;

    /**
     * create a new log entry from scratch by setting log level and message
     * logger decides later on log level value whether to log into file or not
     * timestamp of the log entry gets set when creating this entry
     *
     * @param Message message of the new log entry
     * @param LogLevel log level of the new entry
     */
    public cLogEntry(String Message, int LogLevel){
        this.mLogLevel = LogLevel;
        this.mMessage = Message;
        this.mTimeStamp = (new Date()).getTime();
    }

    /**
     * gets the message stored inside the entry
     * @return message of the entry
     */
    public String getMessage(){
        return this.mMessage;
    }

    /**
     * gets the log level of the entry
     * @return log level of entry
     */
    public int getLogLevel(){
        return this.mLogLevel;
    }

    /**
     * gets the time stamp of the entry in milliseconds after 1.1.1970 CET
     * @see Date
     * @return time stamp
     */
    public long getTimeStamp(){
        return this.mTimeStamp;
    }
}
