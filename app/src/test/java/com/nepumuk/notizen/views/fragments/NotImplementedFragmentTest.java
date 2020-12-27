package com.nepumuk.notizen.views.fragments;

import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.Test;

import static org.junit.Assert.*;

public class NotImplementedFragmentTest extends FragmentTest<NotImplementedFragment> {

    @Test
    public void onCreateView() {
    }

    @Test
    public void updateUI() {
    }

    @Override
    public void setUp() throws Exception {
        scenario = FragmentScenario.launchInContainer(NotImplementedFragment.class);
    }
}