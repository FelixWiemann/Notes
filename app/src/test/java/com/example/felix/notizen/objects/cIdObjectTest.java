package com.example.felix.notizen.objects;

import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created as part of notes in package com.example.felix.notizen.test.FrontEnd
 * by Felix "nepumuk" Wiemann on 29/04/17.
 */
@SuppressWarnings("unused")
public class cIdObjectTest  extends AndroidTest {

    private cIdObject object;

    private String idString;
    private String testTitle = "test title";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        UUID id = UUID.randomUUID();
        idString = id.toString();
        object = new cIdObject(id,testTitle);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void Constructor() {
        assertEquals("Constructor_ID",object.getID(),UUID.fromString(idString));
        assertEquals("Constructor_Title",object.getTitle(),testTitle);
    }

    @Test
    public void setTitle() {
        String newTitle = "new title";
        object.setTitle(newTitle);
        assertEquals("Constructor_Title",object.getTitle(),newTitle);
    }

    @Test(expected = cIdObjectException.class)
    public void setId() throws Exception {
        String ID_string = UUID.randomUUID().toString();
        object.setId(UUID.fromString(ID_string));
    }

    @Test
    public void getID() {
        assertEquals("ID",UUID.fromString(idString),object.getID());
    }

    @Test
    public void getTitle() {
        assertEquals("Constructor_Title",object.getTitle(),testTitle);
    }



}