package com.nepumuk.notizen.core.objects;

import com.nepumuk.notizen.core.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created as part of notes in package com.nepumuk.notizen.test.FrontEnd
 * by Felix "nepumuk" Wiemann on 29/04/17.
 */
public class IdObjectTest extends AndroidTest {

    private IdObject object;

    private String idString;
    private final String testTitle = "test title";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        UUID id = UUID.randomUUID();
        idString = id.toString();
        object = new IdObject(id.toString(),testTitle);
    }

    @Test
    public void Constructor() {
        assertEquals("Constructor_ID",object.getID(),idString);
        assertEquals("Constructor_Title",object.getTitle(),testTitle);
    }

    @Test
    public void setTitle() {
        String newTitle = "new title";
        object.setTitle(newTitle);
        assertEquals("Constructor_Title",object.getTitle(),newTitle);
    }

    @Test(expected = IllegalStateException.class)
    public void setId() {
        String ID_string = UUID.randomUUID().toString();
        object.setID(ID_string);
    }

    @Test
    public void getID() {
        assertEquals("ID",idString,object.getID());
    }

    @Test
    public void getTitle() {
        assertEquals("Constructor_Title",object.getTitle(),testTitle);
    }

    @Test
    public void testJson(){
        String json = object.toJson();
        assertTrue(json.contains(testTitle));
        assertTrue(json.contains(idString));
        assertEquals(object, new IdObject(idString, testTitle));
        assertNotEquals(object, new IdObject(UUID.randomUUID().toString(), testTitle));
    }


}