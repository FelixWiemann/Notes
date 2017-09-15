package com.example.felix.notizen.test.FrontEnd.Notes;

import com.example.felix.notizen.FrontEnd.Notes.cNote;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 *
 *
 *
 * Created as part of notes in package ${PACKAGE_NAME}
 * by Felix "nepumuk" Wiemann on 13/04/17.
 */
@SuppressWarnings("unused")
// extending test class from cNote, as it is not possible to test abstract classes directly
// testing on functions in implemented cNoteTest
public class cNoteTest extends cNote {

    private cNoteTest note;

    /**
     * create new note
     *
     * @param pID    id of note
     * @param pTitle title of note
     *
     */
    cNoteTest(String pID, String pTitle) {
        super(UUID.fromString(pID), pTitle);
    }

    @Before
    public void setUp() throws Exception {
        note = new cNoteTest("ID","title");
        // calc timediff between creation date set in constructor and now
        long timeCreationDiff = note.getCreationDate()- new Date().getTime();
        long timeModificationDiff = note.getLastChangedDate() - new Date().getTime();

        // allowed diff is 1s, as 1 sec is sufficient accuracy for this scope
        Boolean diff = timeCreationDiff < 1000;
        assertEquals("setup time creation diff",diff,true);
        diff = timeModificationDiff < 1000;
        assertEquals("setup time last change diff",diff,true);
        // setting title and id tested in cIdObject
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_setLastChangedDate() throws Exception {
        // tested n setup
    }

    @Test
    public void test_deleteNote() throws Exception {
        // must be tested for each implementation
    }

    @Test
    public void test_addAdditionalData() throws Exception {
        // must be tested for each implementation
    }

    @Test
    public void test_getLastChangedDate() throws Exception {
        // tested in setup
    }

    @Test
    public void test_getCreationDate() throws Exception {
        // tested in setup
    }

    @Test
    public void test_setCreationDate() throws Exception {
        // tested in setup
    }



    /**
     * abstract method to override in inherited classes to handle deletion of the note
     * especially stored data of the note.
     */
    @Override
    public void deleteNote() {
        // shall be tested specificly for each implementation
    }

    /**
     * add additional data to this note
     * pDataBlob may contain any kind of data, determined by type of note
     *
     * @param pDataBlob contains the Data to add, in case of these classes:</p>
     *                  cImageNote: string containing location of Image
     *                  cTextNote: string containing the message of the note
     *                  cTaskNote: List containing all tasks of the note
     */
    @Override
    public void addAdditionalData(Object pDataBlob) {
        // must be tested for each implementation

    }


    @Override
    public String generateJSONString() {
        return null;
    }
}