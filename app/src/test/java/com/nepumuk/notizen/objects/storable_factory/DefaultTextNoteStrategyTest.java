package com.nepumuk.notizen.objects.storable_factory;

import com.nepumuk.notizen.objects.notes.TextNote;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultTextNoteStrategyTest {

    @Test
    public void createDefault() {
        // given
        TextNote note ;
        // when
        note = DefaultTextNoteStrategy.create();
        // then
        assertEquals(DefaultTextNoteStrategy.DEFAULT_MESSAGE,note.getMessage());
        assertEquals(DefaultTextNoteStrategy.DEFAULT_TITLE,note.getTitle());
    }

    @Test
    public void create() {
        // given
        TextNote note ;
        // when
        note = DefaultTextNoteStrategy.create();
        // then
        assertEquals(DefaultTextNoteStrategy.DEFAULT_MESSAGE,note.getMessage());
        assertEquals(DefaultTextNoteStrategy.DEFAULT_TITLE,note.getTitle());
    }
}