package com.nepumuk.notizen.core.views.fragments;

import com.nepumuk.notizen.core.testutils.AndroidTest;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;

import org.junit.Ignore;
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
    @Ignore
    public void generateFragment() {
        // given TODO
       /* TextNote storable = new TextNote();
        // when
        NoteDisplayFragment fragment = NoteDisplayFragmentFactory.generateFragment(storable);
        // then
        assertTrue(fragment instanceof TextNoteFragment);*/
    }

    @Test
    @Ignore
    public void generateFragmentForSuper() {
       /* // given
        TestTaskNote storable = new TestTaskNote();
        // when
        NoteDisplayFragment fragment = NoteDisplayFragmentFactory.generateFragment(storable);
        // then
        assertTrue(fragment instanceof TaskNoteFragment);*/
    }

    /*
    static class TestTaskNote extends TaskNote {

    }*/

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