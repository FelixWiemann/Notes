package com.example.felix.notizen.BackEnd.Logger;

import java.util.Date;

/**
 * Class for log messages
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
    private String mMessage = "empty";
    /**
     * container for log level of entry
     */
    private int mLogLevel = 0;

    private long mTimeStamp = 0;

    /**
     * @param Message
     * @param LogLevel
     */
    public cLogEntry(String Message, int LogLevel){
        this.mLogLevel = LogLevel;
        this.mMessage = Message;
        this.mTimeStamp = (new Date()).getTime();
    }

    /**
     *
     * @return
     */
    public String getMessage(){
        return this.mMessage;
    }

    /**
     *
     * @return
     */
    public int getLogLevel(){
        return this.mLogLevel;
    }

    public long getTimeStamp(){
        return this.mTimeStamp;
    }
}
