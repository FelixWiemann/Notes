package com.nepumuk.notizen.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StringRes;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;

/**
 * Class for accessing settings
 * https://developer.android.com/guide/topics/ui/settings.html
 */

public class Settings {

    private static final String INVALID = "INVALID_VALUE";

    private final static ArrayList<SharedPreferences.OnSharedPreferenceChangeListener> listeners = new ArrayList<>();

    public static String Get(Context context, @StringRes int resource, String def){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(resource),def);
    }
    public static Boolean Get(Context context, @StringRes int resource, boolean def){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(resource),def);
    }
    public static Integer Get(Context context, @StringRes int resource, int def){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(context.getString(resource),def);
    }

    public static boolean Is(Context context, @StringRes int prefResource, @StringRes int value){
        return Get(context,prefResource,INVALID).equalsIgnoreCase(context.getString(value));
    }

    public static void registerOnSharedPreferenceListener(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener){
        listeners.add(listener);
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterOnSharedPreferenceListeners(Context context){
        for (SharedPreferences.OnSharedPreferenceChangeListener listener: listeners) {
            PreferenceManager.getDefaultSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(listener);
        }
    }

}
