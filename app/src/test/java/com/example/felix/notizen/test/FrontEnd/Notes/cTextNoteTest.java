package com.example.felix.notizen.test.FrontEnd.Notes;

import com.example.felix.notizen.FrontEnd.Notes.cTextNote;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created as part of notes in package com.example.felix.notizen.test
 * by Felix "nepumuk" Wiemann on 22/04/17.
 */
public class cTextNoteTest {
    cTextNote testNote;


    @Before
    public void setUp() throws Exception {
        testNote = new cTextNote(UUID.fromString("id"),"title","message");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getMessage() throws Exception {
        assertEquals("message",testNote.getMessage());
    }

    @Test
    public void setMessage() throws Exception {
        testNote.setMessage("new message");
        assertEquals("new message",testNote.getMessage());
    }

    @Test
    public void deleteNote() throws Exception {

    }

    @Test
    public void addAdditionalData() throws Exception {
        testNote.addAdditionalData("datablob");
        assertEquals("datablob",testNote.getMessage());
    }

}