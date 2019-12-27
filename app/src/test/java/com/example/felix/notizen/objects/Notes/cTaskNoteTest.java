package com.example.felix.notizen.objects.Notes;

import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

public class cTaskNoteTest  extends AndroidTest {

    cTaskNote testNote;

    cTask mockedTask;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testNote = new cTaskNote(UUID.randomUUID(),"task note",new ArrayList<cBaseTask>());
        testNote.addTask(new cTask(UUID.randomUUID(),"title task", "text task", false ));
        testNote.addTask(new cTask(UUID.randomUUID(),"test task 2", "text task", false ));
        mockedTask = mock(cTask.class);
    }

    @Test
    public void deleteNote() {
        testNote.addTask(mockedTask);
        // note deletion
        testNote.deleteNote();
        Mockito.verify(mockedTask,Mockito.atLeastOnce()).deleteTask();

        assertEquals(0,testNote.getTaskList().size());

        // empty task note deletion
        testNote.deleteNote();

        //throw new NotYetImplementedException();
    }

    @Test
    public void addTask() {
        cTask mockedTask2 = mock(cTask.class);
        // add and test if it is in the task list
        testNote.addTask(mockedTask2);
        assertTrue(testNote.getTaskList().contains(mockedTask2));
    }

    @Test
    public void getTaskAtPos() {
        testNote.addTask(mockedTask);
        assertEquals(mockedTask,testNote.getTaskAtPos(2));
    }

    @Test
    public void getTaskList() {
        // if this simple getter fails, the other tests will also fail
    }

    @Test
    public void getVersion() {
        assertEquals(1, testNote.getVersion());
    }

    @Test
    public void testJson() throws Exception{
        // ! this test doesn't like the mocks, don't use mocks here...
        String JSON = testNote.toJson();
        System.out.println(JSON);
        Object o = StoragePackerFactory.createFromData(testNote.getId(),testNote.getType(),JSON,testNote.getVersion());
        assertEquals(testNote,o);
        assertTrue(JSON.contains("TaskList\":"));
        assertTrue(JSON.contains(testNote.getTaskAtPos(0).getIdString()));
        assertTrue(JSON.contains(testNote.getTaskAtPos(1).getIdString()));
        assertTrue(JSON.contains(testNote.getTaskAtPos(0).getText()));
        assertTrue(JSON.contains(testNote.getTaskAtPos(0).getTitle()));
        assertTrue(JSON.contains(Long.toString(testNote.getTaskAtPos(0).getTaskCOmpleteDate())));

    }
}