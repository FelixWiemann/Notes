package com.nepumuk.notizen.views;

import android.content.Context;

import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.testutils.AndroidTest;
import com.nepumuk.notizen.views.note_views.NoteDisplayView;
import com.nepumuk.notizen.views.note_views.NoteView;
import com.nepumuk.notizen.views.note_views.TaskNoteView;
import com.nepumuk.notizen.views.note_views.TaskView;

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

@PrepareForTest({NoteDisplayViewFactory.class})
@RunWith(PowerMockRunner.class)
public class noteDisplayViewFactoryTest extends AndroidTest {


    Context mockedContext;
    NoteView mockedTextView;
    TaskNoteView mockedTaskView;

    @Before
    public void SetUp() throws Exception {
        mockedContext = mock(Context.class);
        mockedTextView = mock(NoteView.class);
        mockedTaskView = mock(TaskNoteView.class);
        whenNew(NoteView.class).withAnyArguments().thenReturn(mockedTextView);
        whenNew(TaskNoteView.class).withAnyArguments().thenReturn(mockedTaskView);
    }

    @Ignore("due to reflection creation, View Objects created in the factory cannot be mocked?" +
            "need to be tested in an android test instead")
    @Test
    public void getView() {
        // given
        TextNote note = new TextNote();
        when(mockedTextView.getContent()).thenReturn(note);
        // when
        NoteDisplayView view = NoteDisplayViewFactory.getView(mockedContext,note);
        // then
        fail("false positive here; due to default value returned!");
        assertTrue(view instanceof NoteView);
    }

    @Test
    public void getViewUnknownType() {
        // given
        StorageObjectTestImpl note = new StorageObjectTestImpl();
        // when
        NoteDisplayView view = NoteDisplayViewFactory.getView(mockedContext,note);
        // then
        assertTrue(view instanceof NoteView);
    }

    @Ignore("due to reflection creation, View Objects created in the factory cannot be mocked?" +
            "need to be tested in an android test instead")
    @Test
    public void getViewParentType() {
        // given
        TaskNoteTestImpl note = new TaskNoteTestImpl();
        when(mockedTaskView.getContent()).thenReturn(note);
        // when
        NoteDisplayView view = NoteDisplayViewFactory.getView(mockedContext,note);
        // then
        assertTrue(view instanceof TaskView);
    }

    static class TaskNoteTestImpl extends TaskNote {

        @Override
        public int getVersion() {
            return 0;
        }
    }


    static class StorageObjectTestImpl extends StorageObject {

        @Override
        public int getVersion() {
            return 0;
        }
    }

}