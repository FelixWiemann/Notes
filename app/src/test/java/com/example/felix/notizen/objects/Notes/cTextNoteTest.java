package com.example.felix.notizen.objects.Notes;

import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created as part of notes in package com.example.felix.notizen.test
 * by Felix "nepumuk" Wiemann on 22/04/17.
 */
public class cTextNoteTest  extends AndroidTest {
    cTextNote testNote;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        testNote = new cTextNote(UUID.fromString("968bcf03-df33-4cb3-a2aa-75f591e55a36"),"title","message");
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
    public void testJson() throws Exception{
        String JSON = testNote.toJson();
        Object o = StoragePackerFactory.createFromData(testNote.getId(),testNote.getType(),JSON,testNote.getVersion());
        assertEquals(testNote,o);
    }

    @Test
    public void getVersion() {
    }
}