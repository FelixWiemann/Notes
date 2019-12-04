package com.example.felix.notizen.objects.Task;

import com.example.felix.notizen.Utils.NotYetImplementedException;
import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class cTaskTest  extends AndroidTest {

    private cTask testTask;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testTask = new cTask(UUID.randomUUID(),"title", "text", false);
        new cTask();
        new cTask(UUID.randomUUID());
    }

    @Test
    public void deleteTask() {
        testTask.deleteTask();
        // nothing is happening nothing to check for
    }

    @Test
    public void getVersion() {
        assertEquals(testTask.getVersion(), 1);
    }

    @Test
    public void testJson() throws Exception{
        String JSON = testTask.toJson();
        Object o = StoragePackerFactory.createFromData(testTask.getId(),testTask.getType(),JSON,testTask.getVersion());
        assertEquals(testTask,o);
    }
}