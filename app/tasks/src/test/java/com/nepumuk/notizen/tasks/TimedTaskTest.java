package com.nepumuk.notizen.tasks;

import com.nepumuk.notizen.core.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.core.utils.DateStrategy;
import com.nepumuk.notizen.tasks.objects.TimedTask;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimedTaskTest extends com.nepumuk.notizen.tasks.testutils.AndroidTest {

    private TimedTask testTask;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testTask = new TimedTask(UUID.randomUUID(),"dis da title", "dis da text",false);
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
        Object o = StorableFactory.createFromData(testTask.getId(),testTask.getType(),JSON,testTask.getVersion());
        assertEquals(testTask,o);
        assertTrue(JSON.contains("taskDueDate\":"));
        // TODO
        //  make sure all expected components are stored in JSON
        //  comparing these doesn't really makes sense regarding the data.
        //  it only makes sure the storage packer knows what to do...
    }
}