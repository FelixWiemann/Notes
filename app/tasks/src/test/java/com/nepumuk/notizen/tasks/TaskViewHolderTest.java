package com.nepumuk.notizen.tasks;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nepumuk.notizen.core.utils.ResourceManger;
import com.nepumuk.notizen.tasks.objects.BaseTask;
import com.nepumuk.notizen.tasks.objects.Task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

public class TaskViewHolderTest extends com.nepumuk.notizen.tasks.testutils.AndroidTest {

    TaskViewHolder viewHolderUnderTest;
    @Mock
    private TextView titleMock;
    @Mock
    private TextView messageMock;
    @Mock
    private CheckBox doneMock;
    @Mock
    private View viewMock;

    @Mock
    BaseTask task;

    @Captor
    ArgumentCaptor<CompoundButton.OnCheckedChangeListener> onCheckedChangeListenerArgumentCaptor;

    @Before
    public void setUp() throws Exception{
        super.setUp();
        MockitoAnnotations.initMocks(this);

        when(viewMock.findViewById(eq(R.id.title_view))).thenReturn(titleMock);
        when(viewMock.findViewById(eq(R.id.content_view))).thenReturn(messageMock);
        when(viewMock.findViewById(eq(R.id.task_view_cb))).thenReturn(doneMock);

        viewHolderUnderTest = new TaskViewHolder(viewMock);
    }

    @Test
    public void bind() {
        // given
        UUID uuid = UUID.randomUUID();
        String title = "TITLE";
        String text = "text";
        final boolean doneState = false;
        BaseTask testTask = new Task(uuid, title, text, doneState);
        // when
        viewHolderUnderTest.bind(testTask);
        // then
        verify(titleMock,atLeastOnce()).setText(eq(title));
        verify(messageMock,atLeastOnce()).setText(eq(text));
        verify(doneMock,atLeastOnce()).setChecked(eq(doneState));
        verify(doneMock, times(2)).setOnCheckedChangeListener(onCheckedChangeListenerArgumentCaptor.capture());

        // when
        onCheckedChangeListenerArgumentCaptor.getValue().onCheckedChanged(null, true);
        // then
        assertTrue(testTask.isDone());
        assertTrue(viewHolderUnderTest.wasChildClicked());
        // when
        onCheckedChangeListenerArgumentCaptor.getValue().onCheckedChanged(null, false);
        // then
        assertFalse(testTask.isDone());
    }



    @Test
    public void getStateDescriptionTrue() {
        // given
        final boolean newDone = true;
        String resultString = "RESULT_DONE";
        when(task.isDone()).thenReturn(newDone);
        when(ResourceManger.getString(eq(R.string.content_task_done))).thenReturn(resultString);
        // when
        String result = viewHolderUnderTest.getStateDescription(task.isDone());
        // then
        assertEquals(resultString,result);
    }
    @Test
    public void getStateDescriptionFalse() {
        // given
        final boolean newDone = false;
        String resultString = "RESULT_OPEN";
        when(task.isDone()).thenReturn(newDone);
        when(ResourceManger.getString(eq(R.string.content_task_open))).thenReturn(resultString);
        // when
        String result = viewHolderUnderTest.getStateDescription(task.isDone());
        // then
        assertEquals(resultString,result);
    }

}