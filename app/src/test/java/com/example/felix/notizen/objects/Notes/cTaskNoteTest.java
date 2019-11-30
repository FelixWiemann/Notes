package com.example.felix.notizen.objects.Notes;

import com.example.felix.notizen.Utils.NotYetImplementedException;
import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@Ignore("Not Yet Implemented")
public class cTaskNoteTest  extends AndroidTest {

    cTaskNote testNote;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testNote = new cTaskNote(UUID.randomUUID(),"task note",new ArrayList<cBaseTask>());
    }

    @Test
    public void fromJson() {
        throw new NotYetImplementedException();
    }

    @Test
    public void deleteNote() {
        throw new NotYetImplementedException();
    }

    @Test
    public void addTask() {
        throw new NotYetImplementedException();
    }

    @Test
    public void getTaskAtPos() {
        throw new NotYetImplementedException();
    }

    @Test
    public void getTaskList() {
        throw new NotYetImplementedException();
    }

    @Test
    public void getVersion() {

        throw new NotYetImplementedException();
    }

    @Test
    public void testJson() throws Exception{
        String JSON = testNote.toJson();
        Object o = StoragePackerFactory.createFromData(testNote.getId(),testNote.getType(),JSON,testNote.getVersion());
        assertEquals(testNote,o);
        // TODO
        //  make sure all expected components are stored in JSON
        //  comparing these doesn't really makes sense regarding the data.
        //  it only makes sure the storage packer knows what to do...
        throw new NotYetImplementedException("See TODO");
    }
}