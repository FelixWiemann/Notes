package com.nepumuk.notizen.objects.tasks;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.filtersort.SortCategory;
import com.nepumuk.notizen.testutils.AndroidTest;
import com.nepumuk.notizen.utils.ResourceManger;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created as part of notes in package com.nepumuk.notizen.test.FrontEnd.Task
 * by Felix "nepumuk" Wiemann on 22/04/17.
 */
public class BaseTaskTest extends AndroidTest {

    private BaseTask task;

    private final String title = "title";
    private final String text = "text";
    private final boolean initDoneState = false;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        // given
        // when
        task = new BaseTask(UUID.randomUUID(), title, text,initDoneState) {
            @Override
            public int getVersion() {
                return 0;
            }

            @Override
            public void deleteTask() {

            }
        };
        // then
        assertEquals(title, task.getTitle());
        assertEquals(text, task.getText());
        assertEquals(initDoneState, task.isDone());
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
        assertNotEquals( -1, task.getTaskCompleteDate());
    }
    @Test
    public void setDoneFalse() {
        // given
        task.setDone(true);
        // when
        task.setDone(false);
        // then
        assertFalse(task.isDone());
        assertEquals( -1, task.getTaskCompleteDate());
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


    @Test
    public void getTaskCompleteDate() {
        // already tested in setDoneState
    }

    @Test
    public void getStateDescriptionTrue() {
        // given
        boolean newDone = true;
        String resultString = "RESULT_DONE";
        task.setDone(newDone);
        when(ResourceManger.getString(eq(R.string.content_task_done))).thenReturn(resultString);
        // when
        // then
        assertEquals(resultString,task.getStateDescription());
    }
    @Test
    public void getStateDescriptionFalse() {
        // given
        boolean newDone = false;
        String resultString = "RESULT_OPEN";
        task.setDone(newDone);
        when(ResourceManger.getString(eq(R.string.content_task_open))).thenReturn(resultString);
        // when
        // then
        assertEquals(resultString,task.getStateDescription());
    }
}