package com.nepumuk.notizen.objects.filtersort;

import com.nepumuk.notizen.objects.Notes.cImageNote;
import com.nepumuk.notizen.objects.Notes.cTaskNote;
import com.nepumuk.notizen.objects.Notes.cTextNote;
import com.nepumuk.notizen.objects.Task.cBaseTask;
import com.nepumuk.notizen.objects.Task.cTask;
import com.nepumuk.notizen.objects.Task.cTimedTask;
import com.nepumuk.notizen.testutils.AndroidTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class SortProviderTest extends AndroidTest{

    @Test
    public void testSortByTitleAscending(){
        // given
        cTextNote object1 = null;
        cTextNote object2 = new cTextNote(UUID.randomUUID(), "aaa", "");
        cTextNote object3 = new cTextNote(UUID.randomUUID(), "aba", "");
        cTextNote object4 = new cTextNote(UUID.randomUUID(), "bbb", "");
        cImageNote object5 = new cImageNote(UUID.randomUUID(), "abc", "");
        cTextNote object6 = new cTextNote(UUID.randomUUID(), "", "");
        cTextNote object7 = new cTextNote(UUID.randomUUID(), "bbb", "");

        // when/then
        assertTrue(SortProvider.SortByTitleAscending.compare(object3, object2)>0);
        assertTrue(SortProvider.SortByTitleAscending.compare(object2, object3)<0);
        assertTrue(SortProvider.SortByTitleAscending.compare(object4, object2)>0);
        assertTrue(SortProvider.SortByTitleAscending.compare(object5, object2)>0);
        assertTrue(SortProvider.SortByTitleAscending.compare(object3, object6)>0);
        assertTrue(SortProvider.SortByTitleAscending.compare(object3, object1)>0);
        assertTrue(SortProvider.SortByTitleAscending.compare(object1, object3)<0);
        assertEquals(0, SortProvider.SortByTitleAscending.compare(object3, object3));
        assertEquals(0, SortProvider.SortByTitleAscending.compare(object4, object7));
        assertEquals(0, SortProvider.SortByTitleAscending.compare(object1, object1));

    }

    @Test
    public void testSortByTitleDescending(){
        // given
        cTextNote object1 = null;
        cTextNote object2 = new cTextNote(UUID.randomUUID(), "aaa", "");
        cTextNote object3 = new cTextNote(UUID.randomUUID(), "aba", "");
        cTextNote object4 = new cTextNote(UUID.randomUUID(), "bbb", "");
        cImageNote object5 = new cImageNote(UUID.randomUUID(), "abc", "");
        cTextNote object6 = new cTextNote(UUID.randomUUID(), "", "");
        cTextNote object7 = new cTextNote(UUID.randomUUID(), "bbb", "");

        // when/then
        assertTrue(SortProvider.SortByTitleDescending.compare(object3, object2)<0);
        assertTrue(SortProvider.SortByTitleDescending.compare(object2, object3)>0);
        assertTrue(SortProvider.SortByTitleDescending.compare(object4, object2)<0);
        assertTrue(SortProvider.SortByTitleDescending.compare(object5, object2)<0);
        assertTrue(SortProvider.SortByTitleDescending.compare(object3, object6)<0);
        assertTrue(SortProvider.SortByTitleDescending.compare(object3, object1)<0);
        assertTrue(SortProvider.SortByTitleDescending.compare(object1, object3)>0);
        assertEquals(0, SortProvider.SortByTitleDescending.compare(object3, object3));
        assertEquals(0, SortProvider.SortByTitleDescending.compare(object4, object7));
        assertEquals(0, SortProvider.SortByTitleDescending.compare(object1, object1));

    }

    @Test
    public void testSortTasksDone(){
        // given
        cBaseTask object1 = null;
        cBaseTask object2 = new cTask(UUID.randomUUID(), "aaa", "", false);
        cBaseTask object3 = new cTask(UUID.randomUUID(), "aba", "", true);
        cBaseTask object4 = new cTask(UUID.randomUUID(), "bbb", "", true);
        cBaseTask object5 = new cTask(UUID.randomUUID(), "abc", "", false);
        cBaseTask object6 = new cTask(UUID.randomUUID(), "", "", true);
        cBaseTask object7 = new cTask(UUID.randomUUID(), "bbb", "", false);

        // when/then
        assertTrue(SortProvider.SortTasksDone.compare(object3, object2)>0);
        assertTrue(SortProvider.SortTasksDone.compare(object2, object3)<0);
        assertTrue(SortProvider.SortTasksDone.compare(object4, object2)>0);
        assertTrue(SortProvider.SortTasksDone.compare(object3, object1)<0);
        assertTrue(SortProvider.SortTasksDone.compare(object1, object3)>0);
        assertEquals(0, SortProvider.SortTasksDone.compare(object5, object2));
        assertEquals(0, SortProvider.SortTasksDone.compare(object3, object6));
        assertEquals(0, SortProvider.SortTasksDone.compare(object3, object3));
        assertEquals(0, SortProvider.SortTasksDone.compare(object1, object1));

    }

    @Test
    public void testSortByType(){
        // given
        cTextNote object1 = null;
        cTextNote object2 = new cTextNote(UUID.randomUUID(), "aaa", "");
        cTask object3 = new cTask(UUID.randomUUID(), "aba", "", false);
        cTextNote object4 = new cTextNote(UUID.randomUUID(), "bbb", "");
        cImageNote object5 = new cImageNote(UUID.randomUUID(), "abc", "");
        cTimedTask object6 = new cTimedTask(UUID.randomUUID(), "", "", false);
        cTaskNote object7 = new cTaskNote(UUID.randomUUID(), "bbb", new ArrayList<cBaseTask>());
        // when/then
        assertTrue(SortProvider.SortByType.compare(object3, object2)>0);
        assertTrue(SortProvider.SortByType.compare(object2, object3)<0);
        assertTrue(SortProvider.SortByType.compare(object5, object2)<0);
        assertTrue(SortProvider.SortByType.compare(object3, object6)<0);
        assertTrue(SortProvider.SortByType.compare(object3, object1)<0);
        assertTrue(SortProvider.SortByType.compare(object1, object3)>0);
        assertEquals(0, SortProvider.SortByType.compare(object4, object2));
        assertEquals(0, SortProvider.SortByType.compare(object3, object3));
        assertEquals(0, SortProvider.SortByType.compare(object1, object1));

    }

}