package com.nepumuk.notizen.core.objects;

import com.nepumuk.notizen.core.filtersort.SortCategory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SortableObjectTest {

    private SortableObject object;

    @Before
    public void SetUp(){
        object = new SortableObject();
    }

    @Test
    public void addSortable() {
        object.addSortable(SortCategory.TITLE, () -> "title");
        object.addSortable(SortCategory.TITLE, null);
    }

    @Test
    public void getSortable() {
        assertNull(object.getSortable(SortCategory.TITLE));
        object.addSortable(SortCategory.TITLE,null);
        assertNull(object.getSortable(SortCategory.TITLE));
        object.addSortable(SortCategory.TITLE, () -> "title");
        assertEquals("title",object.getSortable(SortCategory.TITLE));
        assertNull(object.getSortable(SortCategory.TEXT));
    }
}