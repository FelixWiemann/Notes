package com.nepumuk.notizen.objects.Task;

import com.nepumuk.notizen.objects.StorableFactoy.StorableFactory;
import com.nepumuk.notizen.testutils.AndroidTest;

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
    }

    @Test
    public void deleteTask() {
        // given
        // when
        testTask.deleteTask();
        // then
        // nothing is happening nothing to check for
    }

    @Test
    public void getVersion() {
        // given
        int expected = 1;
        // when
        int actual = testTask.getVersion();
        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testJsonPacking() throws Exception{
        // given
        String JSON = testTask.toJson();
        // when
        Object o = StorableFactory.createFromData(testTask.getId(),testTask.getType(),JSON,testTask.getVersion());
        // then
        assertEquals(testTask,o);
    }
}