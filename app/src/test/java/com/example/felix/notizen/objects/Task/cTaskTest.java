package com.example.felix.notizen.objects.Task;

import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class cTaskTest  extends AndroidTest {

    cTask testTask;

    @Before
    public void setUp() throws Exception {
        super.setUp();
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
    }
}