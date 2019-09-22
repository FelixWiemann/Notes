package com.example.felix.notizen.objects.Task;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.objects.StoragePackerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class cTimedTaskTest {

    cTimedTask testTask;

    @Before
    public void setUp() throws Exception {
        cNoteLogger.getInstanceWithoutInit().init(System.getProperty("java.io.tmpdir"),1,1,1,true);
        testTask = new cTimedTask(UUID.randomUUID(),"dis da title", "dis da text",false);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTaskDueDate() {
    }

    @Test
    public void setTaskDueDate() {
        long now = new Date().getTime();
        testTask.setTaskDueDate(now);
        assertEquals("setTaskDueDate",testTask.getTaskDueDate(),now);
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