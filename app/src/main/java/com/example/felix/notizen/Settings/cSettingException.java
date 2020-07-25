package com.example.felix.notizen.Settings;


/**
 * Exception containing the constants used at exceptions thrown in cSetting
 * derived from cBaseException
 * @see com.example.felix.notizen.Settings.cSetting
 */
public class cSettingException extends Exception {

    final static String aSETTING_VALUE_FAILED = "aSETTING_VALUE_FAILED";
    final static String aINIT_PREFS_FAILED = "aINIT_PREFS_FAILED";
    final static String aINIT_SETTINGS_FAILED = "aINIT_SETTINGS_FAILED";

    public cSettingException(String message, Throwable e) {
        super(message, e);
    }
}
