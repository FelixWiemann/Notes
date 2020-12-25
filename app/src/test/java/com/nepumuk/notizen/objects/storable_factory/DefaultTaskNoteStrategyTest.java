package com.nepumuk.notizen.objects.storable_factory;

import com.nepumuk.notizen.objects.notes.TaskNote;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultTaskNoteStrategyTest {

    @Test
    public void createDefault() {
        // given
        TaskNote note ;
        // when
        note = DefaultTaskNoteStrategy.create();
        // then
        assertEquals(0,note.getTaskList().size());
        assertEquals("",note.getTitle());
    }

    @Test
    public void create() {
        // given
        TaskNote note ;
        // when
        note = DefaultTaskNoteStrategy.create();
        // then
        assertEquals(DefaultTaskNoteStrategy.DEFAULT_LIST.size(),note.getTaskList().size());
        assertEquals(DefaultTaskNoteStrategy.DEFAULT_TITLE,note.getTitle());
    }
}