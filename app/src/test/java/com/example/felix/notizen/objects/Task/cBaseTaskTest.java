package com.example.felix.notizen.objects.Task;

import com.example.felix.notizen.objects.Task.cBaseTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created as part of notes in package com.example.felix.notizen.test.FrontEnd.Task
 * by Felix "nepumuk" Wiemann on 22/04/17.
 */
public class cBaseTaskTest {

    private cBaseTask task;

    @Before
    public void setUp() throws Exception {
        task = new cBaseTask(UUID.randomUUID(),"title","text",false) {
            @Override
            public int getVersion() {
                return 0;
            }

            @Override
            public void deleteTask() {

            }
        };
        assertEquals("setup is done",task.isDone(),false);
        assertEquals("setup get text",task.getText(),"text");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isDone() throws Exception {
        // tested in setup
    }

    @Test
    public void setDone() throws Exception {
        task.setDone(true);
        assertEquals("set done",task.isDone(),true);
    }

    @Test
    public void getText() throws Exception {
        // tested in setup
    }

    @Test
    public void setText() throws Exception {
        task.setText("new text");
        assertEquals("set text",task.getText(),"new text");
    }

    @Test
    public void deleteTask() throws Exception {
        // shall be overwritten by each implementation, nothing to test here
    }

}