package com.example.felix.notizen.objects.Notes;

import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class cImageNoteTest extends AndroidTest {

    cImageNote testNote;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testNote = new cImageNote(UUID.randomUUID(),"image title","/image/location");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void deleteNote() {
    }

    @Test
    public void getImageLocation() {
    }

    @Test
    public void getVersion() {
    }


    @Test
    public void testJson() throws Exception{
        String JSON = testNote.toJson();
        Object o = StoragePackerFactory.createFromData(testNote.getId(),testNote.getType(),JSON,testNote.getVersion());
        assertEquals(testNote,o);
    }
}