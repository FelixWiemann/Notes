package com.nepumuk.notizen.views.fragments;

import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

public class SaveDataFragmentTest extends FragmentTest<SaveDataFragment> {

    SaveDataFragmentListener listener;

    @Test
    public void setListener() {
        scenario.onFragment(fragment -> {
            // given
            // when
            fragment.setListener(listener);
            // then
            assertEquals(listener,fragment.listener);
        });
    }

    @Test
    public void onCreateView() {
        // tested by FragmentTest
    }

    @Test
    public void onViewCreated_saveExit() {
        scenario.onFragment(fragment -> {
            // given
            fragment.setListener(listener);
            // when
            fragment.requireView().findViewById(R.id.bt_save_exit).performClick();
            // then
            verify(listener,times(1)).saveAndExit();
            verify(listener,times(0)).cancelExit();
        });
    }
    @Test
    public void onViewCreated_cancelExit() {
        scenario.onFragment(fragment -> {
            // given
            fragment.setListener(listener);
            // when
            fragment.requireView().findViewById(R.id.bt_cancel_exit).performClick();
            // then
            verify(listener,times(0)).saveAndExit();
            verify(listener,times(1)).cancelExit();
        });
    }

    @Override
    public void setUp() throws Exception {
        listener = mock(SaveDataFragmentListener.class);
        // is dialog fragment -> don't attach to activity
        scenario = FragmentScenario.launch(SaveDataFragment.class);
    }
}