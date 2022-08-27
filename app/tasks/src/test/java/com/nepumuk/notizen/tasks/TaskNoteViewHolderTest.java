package com.nepumuk.notizen.tasks;

import android.os.Build;
import android.view.View;

import com.nepumuk.notizen.core.utils.ResourceManager;
import com.nepumuk.notizen.core.views.NestedRecyclerView;
import com.nepumuk.notizen.core.views.adapters.SortableRecyclerAdapter;
import com.nepumuk.notizen.tasks.objects.BaseTask;
import com.nepumuk.notizen.tasks.objects.TaskNote;
import com.nepumuk.notizen.tasks.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith( RobolectricTestRunner.class)
@Config(maxSdk = Build.VERSION_CODES.P)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*" })
@PrepareForTest({TaskNoteViewHolder.class, ResourceManager.class})
@Ignore("fix null pointers in recycler view tests due to mocking...")
public class TaskNoteViewHolderTest extends AndroidTest {

    @Mock
    View viewMock;
    @Mock
    NestedRecyclerView recyclerViewMock;

    SortableRecyclerAdapter<BaseTask> adapter = new SortableRecyclerAdapter<>(new ArrayList<>(), 0);

    TaskNoteViewHolder underTest;

    @Mock
    TaskNote taskNote;


    @Before
    public void setUp(){
        PowerMockito.mockStatic(ResourceManager.class);
        MockitoAnnotations.initMocks(this);
        when(viewMock.findViewById(anyInt())).thenReturn(recyclerViewMock);
        ArrayList<BaseTask> tasks =  new ArrayList<>();
        tasks.add(mock(BaseTask.class));
        when(taskNote.getTaskList()).thenReturn(new ArrayList<>());

        underTest = new TaskNoteViewHolder(viewMock);
    }

    @Test
    public void bindNoAdapterSet() {
        // given
        // when
        underTest.bind(taskNote);
        // then
    }

    @Test
    public void bindAdapterSet() {
        // given
        when(recyclerViewMock.getAdapter()).thenReturn(adapter);
        // when
        underTest.bind(taskNote);

    }

}
