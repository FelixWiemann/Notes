package com.example.felix.notizen.objects.Notes;

import com.example.felix.notizen.objects.StorableFactoy.StorableFactory;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created as part of notes in package com.example.felix.notizen.test
 * by Felix "nepumuk" Wiemann on 22/04/17.
 */
public class cTextNoteTest  extends AndroidTest {

    private final String message = "MESSAGE";

    private cTextNote testNote;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        String title = "title";
        String uuidString = "968bcf03-df33-4cb3-a2aa-75f591e55a36";
        testNote = new cTextNote(UUID.fromString(uuidString), title,message);
    }

    @Test
    public void getMessage(){
        // given
        // when
        String got = testNote.getMessage();
        // then
        assertEquals(message,got);
    }

    @Test
    public void setMessage() {
        // given
        String newMessage = "new message";
        // when
        testNote.setMessage(newMessage);
        // then
        String message = testNote.getMessage();
        assertEquals(newMessage, message);
    }

    @Test
    public void deleteNote() {
        // given
        // when
        testNote.deleteNote();
        // then
        // no need to verify anything, as nothing needs to be done from note side
    }

    @Test
    public void testJson(){
        // given
        // when
        String JSON = testNote.toJson();
        // then
        assertTrue(JSON.contains("message\":"));
        assertTrue(JSON.contains("title\":"));
        assertTrue(JSON.contains("lastChangedDate\":"));
        assertTrue(JSON.contains("creationDate\":"));
        assertTrue(JSON.contains("idString\":"));
    }

    @Test
    public void testUnpackJSON() throws Exception{
        // given
        String JSON = testNote.toJson();
        // when
        Object o = StorableFactory.createFromData(testNote.getId(),testNote.getType(),JSON,testNote.getVersion());
        // then
        assertEquals(testNote,o);
    }

    @Test
    public void getVersion() {
        // if this fails, don't forget:
        // - create data-migration
        // - update database
        // - ...
        // - update test at last
        assertEquals(testNote.getVersion(),1);
    }
}