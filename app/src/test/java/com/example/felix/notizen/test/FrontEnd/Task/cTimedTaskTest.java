package com.example.felix.notizen.test.FrontEnd.Task;

import com.example.felix.notizen.objects.Task.cTimedTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created as part of notes in package com.example.felix.notizen.test.FrontEnd.Task
 * by Felix "nepumuk" Wiemann on 22/04/17.
 */
public class cTimedTaskTest {
    cTimedTask timedTask;

    @Test
    public void getTaskDueDate() throws Exception {
        // tested in setTaskDueDate
    }

    @Test
    public void setTaskDueDate() throws Exception {
        long now = new Date().getTime();
        timedTask.setTaskDueDate(now);
        assertEquals("setTaskDueDate",timedTask.getTaskDueDate(),now);
    }


    @Before
    public void setUp() throws Exception {
        timedTask = new cTimedTask(UUID.randomUUID(),"title", "text", false);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void deleteTask() throws Exception {

    }

}