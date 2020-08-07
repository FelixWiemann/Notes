package com.nepumuk.notizen.utils;

import java.util.Date;

/**
 * date and time strategy used in this app
 * to make it easier to swap later on, date Handling is done here
 * when time is needed, use this one here
 */
public class DateStrategy {
    /**
     * gets the current time
     * @see Date#getTime()
     * @return current time
     */
    public static long getCurrentTime(){
        return (new Date()).getTime();
    }
}
