package com.nepumuk.notizen.objects.filtersort;

import com.nepumuk.notizen.objects.SortableObject;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.objects.tasks.Task;
import com.nepumuk.notizen.objects.tasks.TimedTask;
import com.nepumuk.notizen.testutils.DataBaseStorableTestImpl;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterQueueTest {

    @Test
    public void addFilter() {
        // given
        FilterQueue<StorageObject> filterQueue = new FilterQueue<>();
        // when
        filterQueue.appendFilter(new FilterHideAll<>());
        // then
        assertEquals(1, filterQueue.filters.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void filter() {
        // given
        Task task = new Task(UUID.randomUUID(),"","", false);
        FilterQueue<StorageObject> filterQueue = new FilterQueue<>();
        // when
        filterQueue.filter(task);
        // then expect exception
    }

    @Test
    public void testFilter() {
        // given these objects
        SortableObject obj1 = new DataBaseStorableTestImpl();
        SortableObject obj2 = new TextNote(UUID.randomUUID(),"","");
        SortableObject obj3 = new TaskNote(UUID.randomUUID(),"",new ArrayList<>());
        SortableObject obj4 = new Task(UUID.randomUUID(),"","",false);
        SortableObject obj5 = new Task(UUID.randomUUID(),"","",true);
        SortableObject obj6 = new TimedTask(UUID.randomUUID(),"","",false);
        SortableObject obj7 = new TimedTask(UUID.randomUUID(),"","",true);
        // in this array
        ArrayList<SortableObject> toSort = new ArrayList<>();
        toSort.add(obj1);
        toSort.add(obj2);
        toSort.add(obj3);
        toSort.add(obj4);
        toSort.add(obj5);
        toSort.add(obj6);
        toSort.add(obj7);
        // to be filtered into these lists
        ArrayList<SortableObject> removed = new ArrayList<>();
        ArrayList<SortableObject> kept = new ArrayList<>();
        // with this FilterQueue config
        FilterQueue<SortableObject> queue = new FilterQueue<>()
                .appendFilter(new FilterShowAll<>())
                .appendFilter(new FilterHideDone<>())
                .appendFilter(new HideAllOfType<>(TimedTask.class));
        // when
        queue.filter(toSort,kept,removed);
        // then
        // verify we still have all objects and lost none
        assertEquals(toSort.size(),kept.size() + removed.size());
        // these are kept
        assertTrue(kept.contains(obj1));
        assertTrue(kept.contains(obj2));
        assertTrue(kept.contains(obj3));
        assertTrue(kept.contains(obj4));
        // obj5 is done
        assertFalse(kept.contains(obj5));
        // all of type TimedTask
        assertFalse(kept.contains(obj6));
        assertFalse(kept.contains(obj6));
        // these where removed
        assertFalse(removed.contains(obj1));
        assertFalse(removed.contains(obj2));
        assertFalse(removed.contains(obj3));
        assertFalse(removed.contains(obj4));
        // obj5 is done
        assertTrue(removed.contains(obj5));
        // all of type TimedTask
        assertTrue(removed.contains(obj6));
        assertTrue(removed.contains(obj6));
    }

}