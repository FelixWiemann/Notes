package com.nepumuk.notizen.textnotes;

import android.view.View;
import android.widget.TextView;

import com.nepumuk.notizen.core.utils.ResourceManager;
import com.nepumuk.notizen.textnotes.objects.TextNote;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ResourceManager.class)
public class TextNoteViewHolderTest {

    @Mock
    View viewMock;
    @Mock
    TextView textViewMock;
    @Captor
    ArgumentCaptor<CharSequence> contentDescCaptor;

    @Before
    public void setUp(){
        when(viewMock.findViewById(anyInt())).thenReturn(textViewMock);
        mockStatic(ResourceManager.class);
    }

    @Test
    public void bind() {
        // given
        TextNoteViewHolder underTest =new TextNoteViewHolder(viewMock);
        UUID uuid = UUID.randomUUID();
        String title = "this is title";
        String message = "this is message";
        TextNote toBind = new TextNote(uuid.toString(), title,message);
        // when
        underTest.bind(toBind);
        // then
        verify(textViewMock,atLeastOnce()).setText(eq(message));
        verify(textViewMock,atLeastOnce()).setContentDescription(contentDescCaptor.capture());
        assertTrue(contentDescCaptor.getValue().toString().contains(message));

    }
}