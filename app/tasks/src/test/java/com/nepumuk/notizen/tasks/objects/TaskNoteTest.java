package com.nepumuk.notizen.tasks.objects;

import com.nepumuk.notizen.core.objects.UnpackingDataException;
import com.nepumuk.notizen.core.objects.storable_factory.StorableFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;

public class TaskNoteTest extends com.nepumuk.notizen.tasks.testutils.AndroidTest {

    private TaskNote testNote;

    private Task mockedTask;
    private Task task1;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        TaskNote test = new TaskNote(UUID.randomUUID(),"task note", new ArrayList<>());
        testNote = spy(test);
        task1 = new Task(UUID.randomUUID(),"title task", "text task", false );
        testNote.addTask(task1);
        testNote.addTask(new Task(UUID.randomUUID(),"test task 2", "text task", false ));
        mockedTask = mock(Task.class);
    }

    @Test
    public void deleteNote() {
        // given
        testNote.addTask(mockedTask);
        // when
        testNote.deleteNote();
        // then
        verify(mockedTask,Mockito.atLeastOnce()).deleteTask();
        assertEquals(0,testNote.getTaskList().size());

    }
    @Test
    public void deleteEmptyNote() {
        // given
        testNote.deleteNote();
        // when
        testNote.deleteNote();
        // then
        // no exception or anything
    }


    @Test
    public void addTask() {
        // given
        Task mockedTask2 = mock(Task.class);
        // when
        testNote.addTask(mockedTask2);
        // then
        assertTrue(testNote.getTaskList().contains(mockedTask2));
    }

    @Test
    public void getTaskAtPos() {
        // given
        // when
        testNote.addTask(mockedTask);
        // then
        assertEquals(mockedTask,testNote.getTaskAtPos(2));
    }

    @Test
    public void getTaskList() {
        // if this simple getter fails, the other tests will also fail
    }

    @Test
    public void getVersion() {
        // given
        int expected = 1;
        // when
        int actual = testNote.getVersion();
        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testJson() {
        // given
        // Jackson's toJSON doesn't like mocks, therefore a new note needed to be created
        testNote = new TaskNote(UUID.randomUUID(),"task note", new ArrayList<>());
        testNote.addTask(task1);
        testNote.addTask(new Task(UUID.randomUUID(),"test task 2", "text task", false ));
        // when
        String JSON = testNote.toJson();
        // then
        assertTrue(JSON.contains("TaskList\":"));
        assertTrue(JSON.contains(testNote.getTaskAtPos(0).getIdString()));
        assertTrue(JSON.contains(testNote.getTaskAtPos(1).getIdString()));
        assertTrue(JSON.contains(testNote.getTaskAtPos(0).getText()));
        assertTrue(JSON.contains(testNote.getTaskAtPos(0).getTitle()));
        assertTrue(JSON.contains(Long.toString(testNote.getTaskAtPos(0).getTaskCompleteDate())));
    }

    @Test
    public void testCreationFromJSON() throws UnpackingDataException {
        // given
        // Jackson's toJSON doesn't like mocks, therefore a new note needed to be created
        testNote = new TaskNote(UUID.randomUUID(),"task note", new ArrayList<>());
        String JSON = testNote.toJson();
        // when
        Object o = StorableFactory.createFromData(testNote.getId(),testNote.getType(),JSON,testNote.getVersion());
        // then
        assertEquals(testNote,o);
    }


    @Test
    public void testUpdateTask() throws UnpackingDataException {
        // given
        testNote.deleteNote();
        // create a new object, otherwise by updating the task object will also update it in the list inside of the note to test
        testNote.addTask((BaseTask) StorableFactory.createFromData(task1.getId(),task1.getType(),task1.toJson(),task1.getVersion()));
        task1.setDone(true);
        // when
        testNote.updateTask(task1);
        // then
        assertTrue(testNote.getTaskAtPos(0).isDone());
    }

    @Test
    public void testDeleteTask(){
        // given
        int expectedSize = 1;
        // when
        testNote.deleteTask(task1);
        // then
        assertEquals(expectedSize, testNote.getTaskList().size());
    }




}