package com.nepumuk.notizen.views.fragments;

import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.testutils.DataBaseStorableTestImpl;
import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.Test;

public class NotImplementedFragmentTest extends FragmentTest<NotImplementedFragment> {

    @Test
    public void onCreateView() {
        // is tested when moving through cycles in FragmentTest
    }

    @Test
    public void updateUI() {
        scenario.onFragment(fragment -> {
            // given
            // when
            fragment.updateUI(new DataBaseStorableTestImpl());
            // then
            // no exception happened
        });
    }

    @Override
    public void setUp() throws Exception {
        scenario = FragmentScenario.launchInContainer(NotImplementedFragment.class);
    }
}