package com.nepumuk.notizen.core.settings;

import androidx.appcompat.app.AppCompatDelegate;

import com.nepumuk.notizen.core.R;

import static com.nepumuk.notizen.core.utils.ResourceManger.getString;

public class ThemeSetting extends Setting{

    private static final String THEME_DARK = getString(R.string.pref_value_theme_dark);
    private static final String THEME_LIGHT = getString(R.string.pref_value_theme_dark);
    private static final String THEME_SYSTEM = getString(R.string.pref_value_theme_dark);
    private static final String SETTING_KEY = getString(R.string.pref_key_theme);

    private static final ThemeSetting mInstance = new ThemeSetting();

    public static ThemeSetting getInstance(){
        return mInstance;
    }

    private ThemeSetting(){
        super(SETTING_KEY,THEME_SYSTEM);
    }

    @Override
    public boolean apply(String newValue) {
        // set values
        if( newValue.equals(THEME_DARK)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else if(newValue.equals(THEME_LIGHT)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else {
            // default should be follow system, but you can force it as well
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        return true;
    }
}
