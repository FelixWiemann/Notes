package com.example.felix.notizen.views.adapters.ViewHolders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class TaskViewHolderTest extends AndroidTest {

    TaskViewHolder viewHolderUnderTest;
    private TextView titleMock;
    private TextView messageMock;
    private CheckBox doneMock;

    @Before
    public void setUp() throws Exception{
        super.setUp();
        titleMock = mock(TextView.class);
        messageMock = mock(TextView.class);
        doneMock = mock(CheckBox.class);
        View viewMock = mock(View.class);
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
        cBaseTask testTask = new cTask(uuid, title, text, doneState);
        // when
        viewHolderUnderTest.bind(testTask);
        // then
        verify(titleMock,atLeastOnce()).setText(eq(title));
        verify(messageMock,atLeastOnce()).setText(eq(text));
        verify(doneMock,atLeastOnce()).setChecked(eq(doneState));
        verify(doneMock, times(2)).setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) any());
    }
}