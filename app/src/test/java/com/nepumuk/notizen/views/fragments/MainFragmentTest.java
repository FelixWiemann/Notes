package com.nepumuk.notizen.views.fragments;

import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore("FAB needs: The style on this component requires your app theme to be Theme.AppCompat (or a descendant).")
public class MainFragmentTest extends FragmentTest<MainFragment> {

    @Test
    public void onCreateView() {
    }

    @Test
    public void onAttach() {
    }

    @Test
    public void onStart() {
    }

    @Test
    public void callEditNote() {
    }

    @Test
    public void onCreateOptionsMenu() {
    }

    @Override
    public void setUp() throws Exception {
        scenario = FragmentScenario.launchInContainer(MainFragment.class);
    }
}