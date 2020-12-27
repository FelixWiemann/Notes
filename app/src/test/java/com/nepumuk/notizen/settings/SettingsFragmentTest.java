package com.nepumuk.notizen.settings;

import android.content.res.Resources;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

public class SettingsFragmentTest extends FragmentTest<SettingsFragment> {

    public void setUp() throws Exception {
        // create fragment and move it to created state
        scenario = FragmentScenario.launchInContainer(SettingsFragment.class);
        scenario.moveToState(Lifecycle.State.CREATED);
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onCreatePreferences() {
    }

    @Test
    public void protectedRegisterPreferenceListener() {
        // given
        Preference.OnPreferenceChangeListener listener = mock(Preference.OnPreferenceChangeListener.class);
        Preference.OnPreferenceClickListener prefClickListener = mock(Preference.OnPreferenceClickListener.class);
        String pref_key = "asdf";
        // when
        scenario.onFragment(fragment -> {
            SettingsFragment settings = (SettingsFragment) fragment;
            settings.registerPreferenceListener(pref_key,prefClickListener,listener);
        });
        // then
        verify(listener,never()).onPreferenceChange(any(Preference.class),any(Object.class));
    }

    @Test
    public void registerPreferenceListener() {
        // given
        Preference.OnPreferenceChangeListener listener = mock(Preference.OnPreferenceChangeListener.class);
        Preference.OnPreferenceClickListener prefClickListener = mock(Preference.OnPreferenceClickListener.class);
        int pref_key = R.string.pref_key_theme;
        // when
        scenario.onFragment(fragment -> {
            SettingsFragment settings = (SettingsFragment) fragment;
            settings.registerPreferenceListener(pref_key,prefClickListener,listener);
        });
        // then
        verify(listener,atLeastOnce()).onPreferenceChange(any(Preference.class),any(Object.class));
    }
    @Test
    public void registerNullPreferenceListener() {
        // given
        int pref_key = R.string.pref_key_theme;
        // when
        scenario.onFragment(fragment -> {
            SettingsFragment settings = (SettingsFragment) fragment;
            settings.registerPreferenceListener(pref_key,null,null);
        });
        // then
    }

    @Test(expected = Resources.NotFoundException.class)
    public void registerInvalidPrefIdPreferenceListener() {
        // given
        // when
        scenario.onFragment(fragment -> {
            SettingsFragment settings = (SettingsFragment) fragment;
            settings.registerPreferenceListener(1,null,null);
        });
        // then
    }

    @Test
    public void findPreference() {
    }

    @Test
    public void removePreference() {
    }
}