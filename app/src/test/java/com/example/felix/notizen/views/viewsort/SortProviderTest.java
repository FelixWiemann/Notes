package com.example.felix.notizen.views.viewsort;

import com.example.felix.notizen.objects.Notes.cImageNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class SortProviderTest {

    @Test
    public void testSortByTitle(){
        cTextNote object1 = null;
        cTextNote object2 = new cTextNote(UUID.randomUUID(), "aaa", "");
        cTextNote object3 = new cTextNote(UUID.randomUUID(), "aba", "");
        cTextNote object4 = new cTextNote(UUID.randomUUID(), "bbb", "");
        cImageNote object5 = new cImageNote(UUID.randomUUID(), "abc", "");
        cTextNote object6 = new cTextNote(UUID.randomUUID(), "", "");
        cTextNote object7 = new cTextNote(UUID.randomUUID(), "bbb", "");

        assertTrue(SortProvider.SortByTitle.compare(object3, object2)>0);
        assertTrue(SortProvider.SortByTitle.compare(object2, object3)<0);
        assertTrue(SortProvider.SortByTitle.compare(object4, object2)>0);
        assertTrue(SortProvider.SortByTitle.compare(object5, object2)>0);
        assertTrue(SortProvider.SortByTitle.compare(object3, object6)>0);
        assertTrue(SortProvider.SortByTitle.compare(object3, object1)>0);
        assertTrue(SortProvider.SortByTitle.compare(object1, object3)<0);
        assertEquals(0, SortProvider.SortByTitle.compare(object3, object3));
        assertEquals(0, SortProvider.SortByTitle.compare(object4, object7));

    }

}