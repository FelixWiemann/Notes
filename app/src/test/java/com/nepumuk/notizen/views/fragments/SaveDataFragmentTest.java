package com.nepumuk.notizen.views.fragments;

import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.After;
import org.junit.Test;

import static android.os.Looper.getMainLooper;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

public class SaveDataFragmentTest extends FragmentTest<SaveDataFragment> {



    @Test
    public void setListener() {
    }

    @Test
    public void onCreateView() {
    }

    @Test
    public void onViewCreated() {
    }

    @Override
    public void setUp() throws Exception {
        // is dialog fragment -> don't attach to activity
        scenario = FragmentScenario.launch(SaveDataFragment.class);
    }
}