package com.nepumuk.notizen.objects.filtersort;

import com.nepumuk.notizen.objects.SortableObject;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.objects.tasks.Task;
import com.nepumuk.notizen.testutils.AndroidTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterTests extends AndroidTest {

    @Test
    public void testHideAll() {
        // given
        FilterHideAll<StorageObject> filter = new FilterHideAll<>();
        TaskNote object1 = new TaskNote(UUID.randomUUID(),"asd", new ArrayList<>());
        TaskNote object2 = new TaskNote(UUID.randomUUID(),"asd", new ArrayList<>());
        // when/then
        assertFalse(filter.filter(object1));
        assertFalse(filter.filter(object2));
    }

    @Test
    public void testShowAll() {
        // given
        FilterShowAll<StorageObject> filter = new FilterShowAll<>();
        TaskNote object1 = new TaskNote(UUID.randomUUID(),"", new ArrayList<>());
        TaskNote object2 = new TaskNote(UUID.randomUUID(),"asd", new ArrayList<>());
        // when/then
        assertTrue(filter.filter(object1));
        assertTrue(filter.filter(object2));
    }

    @Test
    public void testHideDone() {
        // given
        FilterHideDone<Task> filter = new FilterHideDone<>();
        Task object1 = new Task(UUID.randomUUID(),"","",false);
        Task object2 = new Task(UUID.randomUUID(),"","",true);

        // when/then
        assertTrue(filter.filter(object1));
        assertFalse(filter.filter(object2));
    }

    @Test
    public void testHideDoneNoTask() {
        // given
        FilterHideDone<TextNote> filter = new FilterHideDone<>();
        TextNote object1 = new TextNote(UUID.randomUUID(),"some title","some message");
        TextNote object2 = new TextNote(UUID.randomUUID(),"some title","some message");

        // when/then
        assertTrue(filter.filter(object1));
        assertTrue(filter.filter(object2));
    }


    @Test
    public void testShowAllOfType() {
        // given
        ShowAllOfType<SortableObject> filter = new ShowAllOfType<>(Task.class);
        Task object1 = new Task(UUID.randomUUID(),"","",false);
        TaskNote object2 = new TaskNote(UUID.randomUUID(),"", new ArrayList<>());
        TextNote object3 = new TextNote(UUID.randomUUID(),"","");

        // when/then
        assertTrue(filter.filter(object1));
        assertFalse(filter.filter(object2));
        assertFalse(filter.filter(object3));
    }

    @Test
    public void testHideAllOfType() {
        // given
        HideAllOfType<SortableObject> filter = new HideAllOfType<>(Task.class);
        Task object1 = new Task(UUID.randomUUID(),"","",false);
        TaskNote object2 = new TaskNote(UUID.randomUUID(),"", new ArrayList<>());
        TextNote object3 = new TextNote(UUID.randomUUID(),"","");

        // when/then
        assertFalse(filter.filter(object1));
        assertTrue(filter.filter(object2));
        assertTrue(filter.filter(object3));
    }
}