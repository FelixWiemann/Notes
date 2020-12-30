package com.nepumuk.notizen.views.fragments;

import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.Test;

public class MainFragmentTest extends FragmentTest<MainFragment> {

    @Test
    public void onCreateView() {
    }

    @Override
    public void setUp() throws Exception {
        scenario = FragmentScenario.launchInContainer(MainFragment.class,null, R.style.Theme_AppCompat_Light_NoActionBar,null);
    }
}