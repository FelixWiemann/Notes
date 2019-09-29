package com.example.felix.notizen.Settings;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


/**
 * Class storing current settings ad works as an interface to shared preferences
 * manages setting and retrieving the settings
 * https://developer.android.com/guide/topics/ui/settings.html
 */

public class cSetting {
    private final static String aKEY_TAG = "key ";
    private final static String aVALUE_TAG = "value ";

    private final static String aSHARED_PREFS_FILE_NAME = "com.example.notes_shared_prefs";

    public final static String aLOG_LOCATION = "KEY_SETTING_LOG_LOCATION";
    public final static String aLOGS_TO_KEEP = "KEY_SETTING_LOGS_TO_KEEP";
    public final static String aLOG_ENTRIES_BEFORE_FLUSH = "KEY_SETTING_LOG_ENTRIES_BEFORE_FLUSH";
    public final static String aJSON_LOCATION = "KEY_SETTING_JSON_LOCATION";
    public final static String aAPP_DEBUG_LEVEL = "KEY_SETTING_APP_DEBUG_LEVEL";


    /**
     * need to decide whether context is used in a static way.
     * articles regarding this:
     * https://nfrolov.wordpress.com/2014/07/12/android-using-context-statically-and-in-singletons/
     * https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
     * this says, no memory leakage is done with context.getApplicationContext(); so current code should be fine
     * https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor
     */
    private static cSetting SettingsSingleton = new cSetting();
    private Context aContext;
    private SharedPreferences prefs;

    private cSetting(){}

    /**
     *
     * @return
     */
    public static cSetting getInstance(){
        return SettingsSingleton;
    }

    /**
     * inits Settings singleton.
     * after setting the context, all shared prefs get read.
     * non existent ones get initialised with default value
     * @param context
     */
    public void init (Context context)throws cSettingException{
        // save context
        aContext = context.getApplicationContext();
        // get prefs
        prefs = aContext.getSharedPreferences(aSHARED_PREFS_FILE_NAME,MODE_PRIVATE);
        // init preferences
        try {
            initPrefs(false);
        } catch (cSettingException e) {
            throw new cSettingException("cSetting.init",cSettingException.aINIT_SETTINGS_FAILED,e);
        }
    }

    /**
     * init preferences
     * if either they do not exist or bResetExistingSettings is true then they get set to a default value
     * @param bResetExistingSettings if true resets existing settings to default
     */
    private void initPrefs(boolean bResetExistingSettings) throws cSettingException{
        try {
            // check for all keys whether they exist, create them if they do not exist
            if (!prefs.contains(aLOG_LOCATION) | bResetExistingSettings) {
                setSetting(aLOG_LOCATION, aContext.getExternalFilesDir(null).getPath() + "/logs");
            }
            if (!prefs.contains(aLOGS_TO_KEEP) | bResetExistingSettings) {
                setSetting(aLOGS_TO_KEEP, 5);
            }
            if (!prefs.contains(aLOG_ENTRIES_BEFORE_FLUSH) | bResetExistingSettings) {
                setSetting(aLOG_ENTRIES_BEFORE_FLUSH, 1);
            }
            if (!prefs.contains(aJSON_LOCATION) | bResetExistingSettings) {
                setSetting(aJSON_LOCATION, aContext.getExternalFilesDir(null).getPath() + "/JSON_DATA.TXT");
            }
            if (!prefs.contains(aAPP_DEBUG_LEVEL) | bResetExistingSettings) {
                setSetting(aAPP_DEBUG_LEVEL, 1);
            }
        }catch (cSettingException ex){
            throw new cSettingException("cSetting.initPrefs",cSettingException.aINIT_PREFS_FAILED,ex);
        }
    }

    /**
     * sets the value of the setting with specified key
     * @param key to be stored in shared prefs
     * @param value to be stored in shared prefs. must be of type Integer,String,Bool,Float or Long
     * @throws cSettingException in case of incompatible type was given
     */
    public void setSetting (String key, Object value) throws cSettingException {
        // get editor to create non existent prefs
        SharedPreferences.Editor prefEditor = prefs.edit();
        if (value instanceof Integer) {
            prefEditor.putInt(key, (Integer) value);
        }else if (value instanceof String){
            prefEditor.putString(key,(String)value);
        }else if (value instanceof Boolean){
            prefEditor.putBoolean(key,(Boolean)value);
        }else if (value instanceof Float){
            prefEditor.putFloat(key,(Float) value);
        }else if (value instanceof Long) {
            prefEditor.putLong(key,(Long) value);
        }
        else{
            cSettingException ex = new cSettingException("cSetting.setSettingValue",cSettingException.aSETTING_VALUE_FAILED,null);
            ex.addAdditionalData(aKEY_TAG,key);
            ex.addAdditionalData(aVALUE_TAG,value.toString());
            throw ex;
        }
        // apply changes
        prefEditor.apply();
    }

    /*
     * gets the value of the setting with specified key
     * @param key of the value to retrieve
     * @return value stored in prefs with given key

    public Object getSetting (String key){
        return prefs.getAll().get(key);
    }*/
    public String getSettingString(String key){
        return prefs.getString(key,"");
    }
    public int getSettingInteger(String key){
        return prefs.getInt(key,-1);
    }
    public boolean getSettingBool(String key){
        return prefs.getBoolean(key,false);
    }
    public float getSettingFloat(String key){
        return prefs.getFloat(key,-1);
    }
    public long getSettingLong(String key){
        return prefs.getLong(key,-1);
    }

}
