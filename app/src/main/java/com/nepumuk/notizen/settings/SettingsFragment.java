package com.nepumuk.notizen.settings;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.nepumuk.notizen.R;

import java.util.HashMap;

public class SettingsFragment extends PreferenceFragmentCompat {

    private boolean isCreated = false;

    private HashMap<Integer,Preference.OnPreferenceClickListener> toRegister = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
        for (Integer key : toRegister.keySet()){
            registerPreferenceTouchListener(key, toRegister.get(key));
        }
        toRegister = null;
        Log.d("SettingsFragment","onCreate");
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // to add a new Preference / Setting follow these steps:
        // 1. create a new key in settings_keys.xml
        // 2. create a new entry in the root_preferences with app:key="@string/created_in_1"
        // 3. create translations
        // 4. refer to the new setting with the key from 1 (R.string.created_in_1)
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        Log.d("SettingsFragment","onCreatePreferences");
    }

    public void registerPreferenceTouchListener(@StringRes int PrefId, Preference.OnPreferenceClickListener listener){
        if(!isCreated){
            // Fragment is not yet properly created, put listeners in a queue to register later on
            toRegister.put(PrefId,listener);
        }else {
            try {
                findPreference(getContext().getString(PrefId)).setOnPreferenceClickListener(listener);
            } catch (NullPointerException ex) {
                Log.e("SettingsFragment", "registering touch listener failed for " + getContext().getString(PrefId), ex);
            }
        }
    }


}