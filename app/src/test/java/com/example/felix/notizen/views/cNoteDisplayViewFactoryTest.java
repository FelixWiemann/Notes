package com.example.felix.notizen.views;

import android.content.Context;

import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.cStorageObject;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PrepareForTest({cNoteDisplayViewFactory.class})
@RunWith(PowerMockRunner.class)
public class cNoteDisplayViewFactoryTest extends AndroidTest {


    Context mockedContext;
    cNoteView mockedTextView;
    cTaskNoteView mockedTaskView;

    @Before
    public void SetUp() throws Exception {
        mockedContext = mock(Context.class);
        mockedTextView = mock(cNoteView.class);
        mockedTaskView = mock(cTaskNoteView.class);
        whenNew(cNoteView.class).withAnyArguments().thenReturn(mockedTextView);
        whenNew(cTaskNoteView.class).withAnyArguments().thenReturn(mockedTaskView);
    }

    @Ignore("due to reflection creation, View Objects created in the factory cannot be mocked?" +
            "need to be tested in an android test instead")
    @Test
    public void getView() {
        // given
        cTextNote note = new cTextNote();
        when(mockedTextView.getContent()).thenReturn(note);
        // when
        cNoteDisplayView view = cNoteDisplayViewFactory.getView(mockedContext,note);
        // then
        fail("false positive here; due to default value returned!");
        assertTrue(view instanceof cNoteView);
    }

    @Test
    public void getViewUnknownType() {
        // given
        StorageObjectTestImpl note = new StorageObjectTestImpl();
        // when
        cNoteDisplayView view = cNoteDisplayViewFactory.getView(mockedContext,note);
        // then
        assertTrue(view instanceof cNoteView);
    }

    @Ignore("due to reflection creation, View Objects created in the factory cannot be mocked?" +
            "need to be tested in an android test instead")
    @Test
    public void getViewParentType() {
        // given
        TaskNoteTestImpl note = new TaskNoteTestImpl();
        when(mockedTaskView.getContent()).thenReturn(note);
        // when
        cNoteDisplayView view = cNoteDisplayViewFactory.getView(mockedContext,note);
        // then
        assertTrue(view instanceof cTaskView);
    }

    static class TaskNoteTestImpl extends cTaskNote {

        @Override
        public int getVersion() {
            return 0;
        }
    }


    static class StorageObjectTestImpl extends cStorageObject {

        @Override
        public int getVersion() {
            return 0;
        }
    }

}