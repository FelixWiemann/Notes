package com.example.felix.notizen.views.fragments;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NoteDisplayFragmentFactoryTest extends AndroidTest {

    @Test
    public void generateFragmentUnknownDataBaseStorable() {
        // given
        UnknownDataBaseStorable storable = new UnknownDataBaseStorable();
        // when
        NoteDisplayFragment fragment = NoteDisplayFragmentFactory.generateFragment(storable);
        // then
        assertTrue(fragment instanceof NotImplementedFragment);
    }

    @Test
    public void generateFragment() {
        // given
        cTextNote storable = new cTextNote();
        // when
        NoteDisplayFragment fragment = NoteDisplayFragmentFactory.generateFragment(storable);
        // then
        assertTrue(fragment instanceof TextNoteFragment);
    }

    @Test
    public void generateFragmentForSuper() {
        // given
        TestTaskNote storable = new TestTaskNote();
        // when
        NoteDisplayFragment fragment = NoteDisplayFragmentFactory.generateFragment(storable);
        // then
        assertTrue(fragment instanceof TaskNoteFragment);
    }

    static class TestTaskNote extends cTaskNote{

    }

    static class UnknownDataBaseStorable implements DatabaseStorable {

        /**
         * version on the object.
         * Increment if the format has changed fundamentally and you have to change/add fields
         *
         * @return version
         */
        @Override
        public int getVersion() {
            return 0;
        }

        /**
         * get the data string that shall be stored in the database
         *
         * @return string
         */
        @Override
        public String getDataString() {
            return null;
        }

        /**
         * get the type of the object.
         * will be saved in the database for deserialization
         *
         * @return
         */
        @Override
        public String getType() {
            return null;
        }

        /**
         * id of the object
         * primary key of the database
         *
         * @return
         */
        @Override
        public String getId() {
            return null;
        }
    }

}