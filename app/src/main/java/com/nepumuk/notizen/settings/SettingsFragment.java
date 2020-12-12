package com.nepumuk.notizen.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;

import com.nepumuk.notizen.R;

import java.util.HashMap;
import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {

    private boolean isCreated = false;

    private HashMap<String,Preference.OnPreferenceClickListener> clickListenersToRegister = new HashMap<>();
    private HashMap<String,Preference.OnPreferenceChangeListener> changeListenersToRegister = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
        for (String key : clickListenersToRegister.keySet()){
            registerPreferenceListener(key, clickListenersToRegister.get(key), changeListenersToRegister.get(key));
        }
        clickListenersToRegister = null;
        changeListenersToRegister = null;
        initSettings();
        // TODO put in again once privacy notice is included
        //removePreference(R.string.pref_key_category_about,R.string.pref_key_privacy_notice);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // to add a new Preference / Setting follow these steps:
        // 1. create a new key in settings_keys.xml
        // 2. create a new entry in the root_preferences with app:key="@string/created_in_1"
        // 3. create translations
        // 4. refer to the new setting with the key from 1 (R.string.created_in_1)
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

    }

    private void registerPreferenceListener(String PrefId, Preference.OnPreferenceClickListener prefClickListener,Preference.OnPreferenceChangeListener prefChangeListener){
        if(!isCreated){
            // Fragment is not yet properly created, put listeners in a queue to register later on
            clickListenersToRegister.put(PrefId,prefClickListener);
            changeListenersToRegister.put(PrefId,prefChangeListener);
        }else {
            try {
                findPreference(PrefId).setOnPreferenceClickListener(prefClickListener);
                findPreference(PrefId).setOnPreferenceChangeListener(prefChangeListener);
                if (prefChangeListener != null) {
                    prefChangeListener.onPreferenceChange(findPreference(PrefId), PreferenceManager.getDefaultSharedPreferences(getContext()).getAll().get(PrefId));
                }

            } catch (NullPointerException ex) {
                Log.e("SettingsFragment", "registering touch listener failed for " + PrefId, ex);
            }
        }
    }

    /**
     *
     * @param PrefId
     * @param prefClickListener
     */
    public void registerPreferenceListener(@StringRes int PrefId, Preference.OnPreferenceClickListener prefClickListener, Preference.OnPreferenceChangeListener prefChangeListener){
        String prefName;
        try {
            prefName = Objects.requireNonNull(getString(PrefId));
            registerPreferenceListener(prefName, prefClickListener, prefChangeListener);
        }catch (NullPointerException ex){
            throw new IllegalArgumentException("trying to register unknown pref id");
        }
    }

    public Preference findPreference(@StringRes int PrefId){
        return findPreference(getContext().getString(PrefId));
    }

    public void removePreference(@StringRes int parentId, @StringRes int PrefId){
        ((PreferenceGroup)findPreference(parentId)).removePreference(findPreference(PrefId));
    }

    private void initSettings(){
        this.registerPreferenceListener(R.string.pref_key_theme, null, this::onThemeChange);
        this.registerPreferenceListener(R.string.pref_key_feedback, preference -> {
            onSendFeedBack();
            return false;
        }, null);
        this.registerPreferenceListener(R.string.pref_key_privacy_notice, preference -> {
            // TODO show privacy notice
            return false;
        }, null);
    }

    private void onSendFeedBack(){
        Intent Email = new Intent(Intent.ACTION_SENDTO);
        //Email.setType("text/plain");
        Email.setData(Uri.parse("mailto:")); // only email apps should handle this
        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "feedback@nepumuk.com" });
        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback" );
        Email.putExtra(Intent.EXTRA_TEXT, "" + "");
        if (Email.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(Email);
        }
    }

    private boolean onThemeChange(Preference preference, Object newValue){

        String value = (String) newValue;
        // set values
        if(value.equals(getString(R.string.pref_value_theme_dark))){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else if(value.equals(getString(R.string.pref_value_theme_light))){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else {
            // default should be follow system, but you can force it as well
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        return true;
    }

}