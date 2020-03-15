package com.example.felix.notizen.objects.filtersort;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class FilterTests extends AndroidTest {

    @Test
    public void testHideAll() {
        // given
        FilterHideAll<DatabaseStorable> filter = new FilterHideAll<>();
        cTaskNote object1 = new cTaskNote(UUID.randomUUID(),"asd",new ArrayList<cBaseTask>());
        cTaskNote object2 = new cTaskNote(UUID.randomUUID(),"asd",new ArrayList<cBaseTask>());
        // when/then
        assertFalse(filter.filter(object1));
        assertFalse(filter.filter(object2));
    }

    @Test
    public void testShowAll() {
        // given
        FilterShowAll<DatabaseStorable> filter = new FilterShowAll<>();
        cTaskNote object1 = new cTaskNote(UUID.randomUUID(),"",new ArrayList<cBaseTask>());
        cTaskNote object2 = new cTaskNote(UUID.randomUUID(),"asd",new ArrayList<cBaseTask>());
        // when/then
        assertTrue(filter.filter(object1));
        assertTrue(filter.filter(object2));
    }

    @Test
    public void testHideDone() {
        // given
        FilterHideDone filter = new FilterHideDone();
        cTask object1 = new cTask(UUID.randomUUID(),"","",false);
        cTask object2 = new cTask(UUID.randomUUID(),"","",true);

        // when/then
        assertTrue(filter.filter(object1));
        assertFalse(filter.filter(object2));
    }
    @Test
    public void testBasedOnClass() {
        // given
        FilterBasedOnClass filter = new FilterBasedOnClass(cTask.class);
        cTask object1 = new cTask(UUID.randomUUID(),"","",false);
        cTaskNote object2 = new cTaskNote(UUID.randomUUID(),"",new ArrayList<cBaseTask>());
        cTextNote object3 = new cTextNote(UUID.randomUUID(),"","");

        // when/then
        assertTrue(filter.filter(object1));
        assertFalse(filter.filter(object2));
        assertFalse(filter.filter(object3));
    }
}