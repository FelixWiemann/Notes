package com.nepumuk.notizen.objects.Task;

import com.nepumuk.notizen.testutils.AndroidTest;
import com.nepumuk.notizen.objects.filtersort.SortCategory;

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

    private String title = "title";
    private String text = "text";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        task = new cBaseTask(UUID.randomUUID(), title, text,false) {
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
    public void isDone() {
        // given
        // when
        boolean done = task.isDone();
        // then
        assertFalse(done);
    }

    @Test
    public void setDoneTrue() {
        // given
        // when
        task.setDone(true);
        // then
        assertTrue(task.isDone());
    }
    @Test
    public void setDoneFalse() {
        // given
        task.setDone(true);
        // when
        task.setDone(false);
        // then
        assertFalse(task.isDone());
    }

    @Test
    public void getText() {
        // given
        // when
        String got = task.getText();
        // then
        assertEquals(got, this.text);
    }

    @Test
    public void setText() {
        // given
        String newText = "new text";
        // when
        task.setText(newText);
        // then
        assertEquals(newText,task.getText());
    }

    @Test
    public void deleteTask() {
        // given
        // when
        // then
        // shall be overwritten by each implementation, nothing to test here
    }

    @Test
    public void toJson(){
        // given
        // when
        String json = task.toJson();
        // then
        assertTrue(json.contains("title\":"));
        assertTrue(json.contains("Text\":"));
        assertTrue(json.contains("Done\":"));
        assertTrue(json.contains("taskCompleteDate\":"));
    }

    @Test
    public void completeDate() throws InterruptedException {
        // given
        task.setDone(true);
        long lastChangeDate = task.getLastChangedDate();
        assertTrue(lastChangeDate !=-1);
        assertTrue(task.getTaskCompleteDate() != -1);

        // make sure we are not running through it too fast, otherwise test might fail
        Thread.sleep(100);
        // when
        task.setDone(false);
        // then
        assertTrue(lastChangeDate <task.getLastChangedDate());
        assertEquals(task.getTaskCompleteDate(), -1);
    }


    @Test
    public void sortingOnTaskComplete(){
        // given
        long taskCompleteDate = task.getTaskCompleteDate();
        // when
        Object sortable = task.getSortable(SortCategory.TASK_DONE_TIME);
        // then
        assertEquals(sortable, taskCompleteDate);
    }

    @Test
    public void sortingOnTaskDone(){
        // given
        boolean taskDone = task.isDone();
        // when
        Object sortable = task.getSortable(SortCategory.TASK_DONE_STATE);
        // then
        assertEquals(sortable, taskDone);
    }

}