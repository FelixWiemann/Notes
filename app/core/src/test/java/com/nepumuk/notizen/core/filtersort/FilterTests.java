package com.nepumuk.notizen.core.filtersort;

import com.nepumuk.notizen.core.testutils.AndroidTest;

import org.junit.Ignore;
import org.junit.Test;

public class FilterTests extends AndroidTest {

    @Test
    @Ignore("get basic notes")
    public void testHideAll() {
      /*  // given
        FilterHideAll<StorageObject> filter = new FilterHideAll<>();
        TaskNote object1 = new TaskNote(UUID.randomUUID(),"asd", new ArrayList<>());
        TaskNote object2 = new TaskNote(UUID.randomUUID(),"asd", new ArrayList<>());
        // when/then
        assertFalse(filter.filter(object1));
        assertFalse(filter.filter(object2));*/
    }

    @Test
    @Ignore("get basic notes")
    public void testShowAll() {
       /* // given
        FilterShowAll<StorageObject> filter = new FilterShowAll<>();
        TaskNote object1 = new TaskNote(UUID.randomUUID(),"", new ArrayList<>());
        TaskNote object2 = new TaskNote(UUID.randomUUID(),"asd", new ArrayList<>());
        // when/then
        assertTrue(filter.filter(object1));
        assertTrue(filter.filter(object2));*/
    }

    @Test
    @Ignore("get basic notes")
    public void testHideDone() {
       /* // given
        FilterHideDone<Task> filter = new FilterHideDone<>();
        Task object1 = new Task(UUID.randomUUID(),"","",false);
        Task object2 = new Task(UUID.randomUUID(),"","",true);

        // when/then
        assertTrue(filter.filter(object1));
        assertFalse(filter.filter(object2));*/
    }

    @Test
    @Ignore("get basic notes")
    public void testHideDoneNoTask() {
       /* // given
        FilterHideDone<TextNote> filter = new FilterHideDone<>();
        TextNote object1 = new TextNote(UUID.randomUUID(),"some title","some message");
        TextNote object2 = new TextNote(UUID.randomUUID(),"some title","some message");

        // when/then
        assertTrue(filter.filter(object1));
        assertTrue(filter.filter(object2));*/
    }


    @Test
    @Ignore("get basic notes")
    public void testShowAllOfType() {
     /*   // given
        ShowAllOfType<SortableObject> filter = new ShowAllOfType<>(Task.class);
        Task object1 = new Task(UUID.randomUUID(),"","",false);
        TaskNote object2 = new TaskNote(UUID.randomUUID(),"", new ArrayList<>());
        TextNote object3 = new TextNote(UUID.randomUUID(),"","");

        // when/then
        assertTrue(filter.filter(object1));
        assertFalse(filter.filter(object2));
        assertFalse(filter.filter(object3));*/
    }

    @Test
    @Ignore("get basic notes")
    public void testHideAllOfType() {
        // given
       /* HideAllOfType<SortableObject> filter = new HideAllOfType<>(Task.class);
        Task object1 = new Task(UUID.randomUUID(),"","",false);
        TaskNote object2 = new TaskNote(UUID.randomUUID(),"", new ArrayList<>());
        TextNote object3 = new TextNote(UUID.randomUUID(),"","");

        // when/then
        assertFalse(filter.filter(object1));
        assertTrue(filter.filter(object2));
        assertTrue(filter.filter(object3));*/
    }
}