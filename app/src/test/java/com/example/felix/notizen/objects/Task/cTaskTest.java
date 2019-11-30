package com.example.felix.notizen.objects.Task;

import com.example.felix.notizen.Utils.NotYetImplementedException;
import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

@Ignore("Not Yet Implemented")
public class cTaskTest  extends AndroidTest {

    private cTask testTask;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testTask = new cTask(UUID.randomUUID(),"title", "text", false);
    }

    @Test
    public void deleteTask() {
        throw new NotYetImplementedException();
    }

    @Test
    public void getVersion() {
        throw new NotYetImplementedException();
    }

    @Test
    public void testJson() throws Exception{
        String JSON = testTask.toJson();
        Object o = StoragePackerFactory.createFromData(testTask.getId(),testTask.getType(),JSON,testTask.getVersion());
        assertEquals(testTask,o);
        // TODO
        //  make sure all expected components are stored in JSON
        //  comparing these doesn't really makes sense regarding the data.
        //  it only makes sure the storage packer knows what to do...
        throw new NotYetImplementedException("See TODO");
    }
}