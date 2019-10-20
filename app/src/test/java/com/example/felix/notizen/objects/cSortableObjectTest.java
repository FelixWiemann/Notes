package com.example.felix.notizen.objects;

import com.example.felix.notizen.views.viewsort.SortAble;
import com.example.felix.notizen.views.viewsort.SortCategory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class cSortableObjectTest {

    private cSortableObject object;

    @Before
    public void SetUp(){
        object = new cSortableObject();
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