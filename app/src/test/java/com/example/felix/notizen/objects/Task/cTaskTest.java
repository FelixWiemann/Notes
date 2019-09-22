package com.example.felix.notizen.objects.Task;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.objects.StoragePackerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class cTaskTest {

    cTask testTask;

    @Before
    public void setUp() throws Exception {
        cNoteLogger.getInstanceWithoutInit().init(System.getProperty("java.io.tmpdir"),1,1,1,true);
        testTask = new cTask(UUID.randomUUID(),"title", "text", false);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void deleteTask() {
    }

    @Test
    public void getVersion() {
    }

    @Test
    public void testJson() throws Exception{
        String JSON = testTask.toJson();
        Object o = StoragePackerFactory.createFromData(testTask.getId(),testTask.getType(),JSON,testTask.getVersion());
        assertEquals(testTask,o);
        System.out.println(o);
        System.out.println(testTask);
    }
}