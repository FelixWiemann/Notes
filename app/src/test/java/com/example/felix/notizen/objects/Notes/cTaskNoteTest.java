package com.example.felix.notizen.objects.Notes;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.objects.Task.cBaseTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class cTaskNoteTest {

    cTaskNote testNote;

    @Before
    public void setUp() throws Exception {
        cNoteLogger.getInstanceWithoutInit().init(System.getProperty("java.io.tmpdir"),1,1,1,true);
        testNote = new cTaskNote(UUID.randomUUID(),"task note",new ArrayList<cBaseTask>());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fromJson() {
    }

    @Test
    public void deleteNote() {
    }

    @Test
    public void addTask() {
    }

    @Test
    public void getTaskAtPos() {
    }

    @Test
    public void getTaskList() {
    }

    @Test
    public void getVersion() {
    }

    @Test
    public void testJson() throws Exception{
        String JSON = testNote.toJson();
        Object o = StoragePackerFactory.createFromData(testNote.getId(),testNote.getType(),JSON,testNote.getVersion());
        assertEquals(testNote,o);
        System.out.println(o);
        System.out.println(testNote);
    }
}