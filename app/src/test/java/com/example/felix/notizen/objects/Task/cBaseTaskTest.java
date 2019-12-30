package com.example.felix.notizen.objects.Task;

import com.example.felix.notizen.testutils.AndroidTest;
import com.example.felix.notizen.views.viewsort.SortCategory;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created as part of notes in package com.example.felix.notizen.test.FrontEnd.Task
 * by Felix "nepumuk" Wiemann on 22/04/17.
 */
public class cBaseTaskTest  extends AndroidTest {

    private cBaseTask task;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        task = new cBaseTask(UUID.randomUUID(),"title","text",false) {
            @Override
            public int getVersion() {
                return 0;
            }

            @Override
            public void deleteTask() {

            }
        };
    }

    @Test
    public void isDone() throws Exception {
        assertFalse("setup is done", task.isDone());
    }

    @Test
    public void setDone() throws Exception {
        task.setDone(true);
        assertTrue("set done", task.isDone());
    }

    @Test
    public void getText() throws Exception {
        assertEquals("setup get text",task.getText(),"text");
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

    @Test
    public void toJson(){
        String json = task.toJson();
        assertTrue(json.contains("title\":"));
        assertTrue(json.contains("Text\":"));
        assertTrue(json.contains("Done\":"));
        assertTrue(json.contains("taskCompleteDate\":"));
    }

    @Test
    public void completeDate() throws InterruptedException {
        task.setDone(true);
        long lastchangedate = task.getLastChangedDate();
        assertTrue(lastchangedate!=-1);
        assertTrue(task.getTaskCOmpleteDate() != -1);
        // make sure we are not running through it too fast, otherwise test might fail
        Thread.sleep(100);
        task.setDone(false);
        assertTrue(lastchangedate<task.getLastChangedDate());
        assertEquals(task.getTaskCOmpleteDate(), -1);
    }


    @Test
    public void sortingOnTaskComplete(){
        assertEquals( task.getSortable(SortCategory.TASK_DONE_TIME), task.getTaskCOmpleteDate());
    }

}