package com.example.felix.notizen.objects.Notes;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.objects.StorageUnpackerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class cImageNoteTest {

    cImageNote testNote;

    @Before
    public void setUp() throws Exception {
        cNoteLogger.getInstanceWithoutInit().init(System.getProperty("java.io.tmpdir"),1,1,1,true);
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
        Object o = StorageUnpackerFactory.getInstance().createFromData(testNote.getId(),testNote.getType(),JSON,testNote.getVersion());
        assertEquals(testNote,o);
        System.out.println(o);
        System.out.println(testNote);
    }
}