package com.nepumuk.notizen.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.testutils.DataBaseStorableTestImpl;
import com.nepumuk.notizen.testutils.FragmentTest;
import com.nepumuk.notizen.utils.db_access.DatabaseStorable;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

public class NoteDisplayFragmentTest extends FragmentTest<NoteDisplayFragmentTest.NoteDisplayFragmentTestImpl> {

    @Test
    public void createView() {
        scenario.onFragment(fragment -> {
            // given
            LayoutInflater inflater = mock(LayoutInflater.class);
            int layout = 1;
            ViewGroup container = mock(ViewGroup.class);
            // when
            fragment.createView(inflater,container,layout);
            // then
            verify(inflater,times(1)).inflate(eq(layout),eq(container),eq(false));
        });
    }

    @Test
    public void testUpdateData(){
        scenario.onFragment(fragment -> {
            // given
            EditNoteViewModel.SaveState<DatabaseStorable> state = new EditNoteViewModel.SaveState<>(new DataBaseStorableTestImpl());
            int currentcount = fragment.updateUiCounter;
            // when
            fragment.updateData(state);
            // then
            assertTrue(fragment.updateUiCounter > currentcount);
        });
    }

    @Override
    public void setUp() throws Exception {
        scenario = FragmentScenario.launchInContainer(NoteDisplayFragmentTestImpl.class);
    }

    @Ignore("Test implementation")
    public static class NoteDisplayFragmentTestImpl extends NoteDisplayFragment<DatabaseStorable>{

        public int onCreateViewCount = 0;
        public int updateUiCounter = 0;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            onCreateViewCount++;
            return null;
        }

        @Override
        protected void updateUI(DatabaseStorable updatedData) {
            updateUiCounter++;
        }
    }

}