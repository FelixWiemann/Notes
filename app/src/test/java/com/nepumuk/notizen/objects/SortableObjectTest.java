package com.nepumuk.notizen.objects;

import com.nepumuk.notizen.objects.filtersort.SortAble;
import com.nepumuk.notizen.objects.filtersort.SortCategory;

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
        object.addSortable(SortCategory.TITLE, new SortAble<String>() {
            @Override
            public String getData() {
                return  "title";
            }
        });
        object.addSortable(SortCategory.TITLE, null);
    }

    @Test
    public void getSortable() {
        assertNull(object.getSortable(SortCategory.TITLE));
        object.addSortable(SortCategory.TITLE,null);
        assertNull(object.getSortable(SortCategory.TITLE));
        object.addSortable(SortCategory.TITLE, new SortAble<String>() {
            @Override
            public String getData() {
                return  "title";
            }
        });
        assertEquals("title",object.getSortable(SortCategory.TITLE));
        assertNull(object.getSortable(SortCategory.TEXT));
    }
}