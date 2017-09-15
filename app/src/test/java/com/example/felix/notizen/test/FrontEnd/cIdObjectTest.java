package com.example.felix.notizen.test.FrontEnd;

import com.example.felix.notizen.FrontEnd.cIdObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created as part of notes in package com.example.felix.notizen.test.FrontEnd
 * by Felix "nepumuk" Wiemann on 29/04/17.
 */
@SuppressWarnings("unused")
public class cIdObjectTest {

    private cIdObject object;

    @Before
    public void setUp() throws Exception {
        object = new cIdObject(UUID.randomUUID(),"test title");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void Constructor() throws Exception {
        assertEquals("Constructor_ID",object.getID(),"1");
        assertEquals("Constructor_Title",object.getTitle(),"test title");
    }

    @Test
    public void setTitle() throws Exception {
        object.setTitle("new Title");
        assertEquals("Constructor_Title",object.getTitle(),"new Title");
    }

    @Test
    public void setId() throws Exception {
        String ID_string = UUID.randomUUID().toString();
        object.setId(UUID.fromString(ID_string));
        assertEquals("Constructor_Title",object.getID().toString(),ID_string);
    }

    @Test
    public void getID() throws Exception {
        // already tested in constructor
    }

    @Test
    public void getTitle() throws Exception {
        // already tested in constructor
    }

}