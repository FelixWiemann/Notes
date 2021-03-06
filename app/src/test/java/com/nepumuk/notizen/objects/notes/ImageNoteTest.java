package com.nepumuk.notizen.objects.notes;

import com.nepumuk.notizen.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImageNoteTest extends AndroidTest {

    ImageNote testNote;
    String imageLocation;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        imageLocation = "path/to/image/location";
        testNote = new ImageNote(UUID.randomUUID(),"image title",imageLocation);
    }

    @Test
    public void deleteNote() {
        testNote.deleteNote();
        // TODO write test for implementation
    }

    @Test
    public void getImageLocation() {
        assertEquals(imageLocation, testNote.getImageLocation());
    }

    @Test
    public void getVersion() {
        assertEquals(1, testNote.getVersion());
    }


    @Test
    public void testJson() throws Exception{
        String JSON = testNote.toJson();
        Object o = StorableFactory.createFromData(testNote.getId(),testNote.getType(),JSON,testNote.getVersion());
        assertEquals(testNote,o);
        assertTrue(JSON.contains("imageLocation\":"));
        // TODO
        //  make sure all expected components are stored in JSON
        //  comparing these doesn't really makes sense regarding the data.
        //  it only makes sure the storage packer knows what to do...
    }
}