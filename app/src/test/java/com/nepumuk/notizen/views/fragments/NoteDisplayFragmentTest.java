package com.nepumuk.notizen.views.fragments;

import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;


@Ignore("cannot instantiate abstract class - TODO fix")
public class NoteDisplayFragmentTest extends FragmentTest<NoteDisplayFragment> {

    @Test
    public void onCreateView() {
    }

    @Test
    public void createView() {
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void updateUI() {
    }

    @Override
    public void setUp() throws Exception {
        scenario = FragmentScenario.launchInContainer(NoteDisplayFragment.class);
    }
}