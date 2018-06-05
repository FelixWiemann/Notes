package com.example.felix.notizen.test.BackEnd.Logger;

import com.example.felix.notizen.Utils.Logger.cLogEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created as part of notes in package com.example.felix.notizen.test.BackEnd.Logger
 * by Felix "nepumuk" Wiemann on 04/06/17.
 */
@SuppressWarnings("unused")
public class cLogEntryTest {

    private cLogEntry logEntry;

    @Before
    public void setUp() throws Exception {
        logEntry = new cLogEntry("test message",4);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getMessage() throws Exception {
        assertEquals("test message",logEntry.getMessage());
    }

    @Test
    public void getLogLevel() throws Exception {
        assertEquals(4,logEntry.getLogLevel());
    }

    @Test
    public void getTimeStamp() throws Exception {
        // TODO assert timestamps
    }

}