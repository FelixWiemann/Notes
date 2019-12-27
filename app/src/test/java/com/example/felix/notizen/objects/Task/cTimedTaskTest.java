package com.example.felix.notizen.objects.Task;

import com.example.felix.notizen.Utils.DateStrategy;
import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class cTimedTaskTest extends AndroidTest {

    private cTimedTask testTask;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testTask = new cTimedTask(UUID.randomUUID(),"dis da title", "dis da text",false);
    }

    @Test
    public void getTaskDueDate() {
        long taskDueDate = 111111;
        testTask.setTaskDueDate(taskDueDate);
        assertEquals(taskDueDate, testTask.getTaskDueDate());
    }

    @Test
    public void setTaskDueDate() {
        long now = DateStrategy.getCurrentTime();
        testTask.setTaskDueDate(now);
        assertEquals("setTaskDueDate",testTask.getTaskDueDate(),now);
    }

    @Test
    public void deleteTask() {
        testTask.deleteTask();
        // TODO write test for fully implemented
    }

    @Test
    public void getVersion() {
        assertEquals(1, testTask.getVersion());
    }

    @Test
    public void testJson() throws Exception{
        String JSON = testTask.toJson();
        Object o = StoragePackerFactory.createFromData(testTask.getId(),testTask.getType(),JSON,testTask.getVersion());
        assertEquals(testTask,o);
        assertTrue(JSON.contains("taskDueDate\":"));
        // TODO
        //  make sure all expected components are stored in JSON
        //  comparing these doesn't really makes sense regarding the data.
        //  it only makes sure the storage packer knows what to do...
    }
}