package com.nepumuk.notizen.core.settings;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;

import com.nepumuk.notizen.core.R;
import com.nepumuk.notizen.core.utils.BackgroundWorker;

import java.util.HashMap;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class SettingsFragment extends PreferenceFragmentCompat {

    private boolean isCreated = false;

    private HashMap<String,Preference.OnPreferenceClickListener> clickListenersToRegister = new HashMap<>();
    private HashMap<String,Preference.OnPreferenceChangeListener> changeListenersToRegister = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
        new BackgroundWorker(()-> {
            for (String key : clickListenersToRegister.keySet()) {
                registerPreferenceListener(key, clickListenersToRegister.get(key), changeListenersToRegister.get(key));
            }
            clickListenersToRegister = null;
            changeListenersToRegister = null;
            initSettings();
        }).start();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // to add a new Preference / Setting follow these steps:
        // 1. create a new key in settings_keys.xml
        // 2. create a new entry in the root_preferences with app:key="@string/created_in_1"
        // 3. create translations
        // 4. refer to the new setting with the key from 1 (R.string.created_in_1)
        setPreferencesFromResource(R.xml.preferences_core, rootKey);

    }

    protected void registerPreferenceListener(String PrefId, Preference.OnPreferenceClickListener prefClickListener,Preference.OnPreferenceChangeListener prefChangeListener){
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
        registerPreferenceListener(getString(PrefId), prefClickListener, prefChangeListener);
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
            View view  = getLayoutInflater().inflate(R.layout.privacy_notice,null);
            TextView tv = view.findViewById(R.id.tv_PrivacyNoticeText);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Spanned spanned =  Html.fromHtml(getString(R.string.privacy_notice), FROM_HTML_MODE_LEGACY);
                tv.setText(spanned);
                Linkify.addLinks(tv,Linkify.ALL);
            }else {
                tv.setText(R.string.privacy_notice);
            }
            new AlertDialog.Builder(getContext()).setView(view).create().show();
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
        return ThemeSetting.getInstance().apply((String) newValue);
    }

}