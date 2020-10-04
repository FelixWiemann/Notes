package com.nepumuk.notizen.objects.filtersort;

import com.nepumuk.notizen.objects.notes.ImageNote;
import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.objects.tasks.BaseTask;
import com.nepumuk.notizen.objects.tasks.Task;
import com.nepumuk.notizen.objects.tasks.TimedTask;
import com.nepumuk.notizen.testutils.AndroidTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class SortProviderTest extends AndroidTest{

    @Test
    public void testSortByTitleAscending(){
        // given
        TextNote object1 = null;
        TextNote object2 = new TextNote(UUID.randomUUID(), "aaa", "");
        TextNote object3 = new TextNote(UUID.randomUUID(), "aba", "");
        TextNote object4 = new TextNote(UUID.randomUUID(), "bbb", "");
        ImageNote object5 = new ImageNote(UUID.randomUUID(), "abc", "");
        TextNote object6 = new TextNote(UUID.randomUUID(), "", "");
        TextNote object7 = new TextNote(UUID.randomUUID(), "bbb", "");

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
        TextNote object1 = null;
        TextNote object2 = new TextNote(UUID.randomUUID(), "aaa", "");
        TextNote object3 = new TextNote(UUID.randomUUID(), "aba", "");
        TextNote object4 = new TextNote(UUID.randomUUID(), "bbb", "");
        ImageNote object5 = new ImageNote(UUID.randomUUID(), "abc", "");
        TextNote object6 = new TextNote(UUID.randomUUID(), "", "");
        TextNote object7 = new TextNote(UUID.randomUUID(), "bbb", "");

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
        BaseTask object1 = null;
        BaseTask object2 = new Task(UUID.randomUUID(), "aaa", "", false);
        BaseTask object3 = new Task(UUID.randomUUID(), "aba", "", true);
        BaseTask object4 = new Task(UUID.randomUUID(), "bbb", "", true);
        BaseTask object5 = new Task(UUID.randomUUID(), "abc", "", false);
        BaseTask object6 = new Task(UUID.randomUUID(), "", "", true);
        BaseTask object7 = new Task(UUID.randomUUID(), "bbb", "", false);

        // when/then
        assertTrue(SortProvider.SortTasksDone.compare(object3, object2)>0);
        assertTrue(SortProvider.SortTasksDone.compare(object2, object3)<0);
        assertTrue(SortProvider.SortTasksDone.compare(object4, object2)>0);
        assertTrue(SortProvider.SortTasksDone.compare(object3, object1)<0);
        assertTrue(SortProvider.SortTasksDone.compare(object1, object3)>0);
        assertTrue( SortProvider.SortTasksDone.compare(object3, object7)>0);
        assertEquals(0, SortProvider.SortTasksDone.compare(object5, object2));
        assertEquals(0, SortProvider.SortTasksDone.compare(object3, object6));
        assertEquals(0, SortProvider.SortTasksDone.compare(object3, object3));
        assertEquals(0, SortProvider.SortTasksDone.compare(object1, object1));


    }

    @Test
    public void testSortByType(){
        // given
        TextNote object1 = null;
        TextNote object2 = new TextNote(UUID.randomUUID(), "aaa", "");
        Task object3 = new Task(UUID.randomUUID(), "aba", "", false);
        TextNote object4 = new TextNote(UUID.randomUUID(), "bbb", "");
        ImageNote object5 = new ImageNote(UUID.randomUUID(), "abc", "");
        TimedTask object6 = new TimedTask(UUID.randomUUID(), "", "", false);
        TaskNote object7 = new TaskNote(UUID.randomUUID(), "bbb", new ArrayList<>());
        // when/then
        assertTrue(SortProvider.SortByType.compare(object3, object2)>0);
        assertTrue(SortProvider.SortByType.compare(object2, object3)<0);
        assertTrue(SortProvider.SortByType.compare(object5, object2)<0);
        assertTrue(SortProvider.SortByType.compare(object3, object6)<0);
        assertTrue(SortProvider.SortByType.compare(object3, object1)<0);
        assertTrue(SortProvider.SortByType.compare(object1, object3)>0);
        assertTrue(SortProvider.SortByType.compare(object3, object7)>0);
        assertEquals(0, SortProvider.SortByType.compare(object4, object2));
        assertEquals(0, SortProvider.SortByType.compare(object3, object3));
        assertEquals(0, SortProvider.SortByType.compare(object1, object1));

    }

}