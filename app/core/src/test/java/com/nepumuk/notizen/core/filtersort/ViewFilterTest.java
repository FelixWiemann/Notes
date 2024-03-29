package com.nepumuk.notizen.core.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.objects.SortableObject;
import com.nepumuk.notizen.core.testutils.DataBaseStorableTestImpl;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ViewFilterTest {

    ViewFilter none ;
    ViewFilter all ;

    @Before
    public void setUp(){
        none = new ViewFilter() {
            @Override
            public boolean filter(@NonNull SortableObject toFilter) {
                return false;
            }
        };
        all = new ViewFilter() {
            @Override
            public boolean filter(@NonNull SortableObject toFilter) {
                return true;
            }
        };
    }

    @Test
    public void filterAll() {
        // given
        List<DatabaseStorable> storables = new ArrayList<>();
        storables.add(new DataBaseStorableTestImpl());
        storables.add(new DataBaseStorableTestImpl());
        List<DatabaseStorable> show = new ArrayList<>();
        List<DatabaseStorable> hide = new ArrayList<>();

        // when
        all.filter(storables, show, hide);
        // then
        assertEquals(0,hide.size());
        assertEquals(storables.size(),show.size());
    }
    @Test
    public void filterNone() {
        // given
        List<DatabaseStorable> storables = new ArrayList<>();
        storables.add(new DataBaseStorableTestImpl());
        storables.add(new DataBaseStorableTestImpl());
        List<DatabaseStorable> show = new ArrayList<>();
        List<DatabaseStorable> hide = new ArrayList<>();

        // when
        none.filter(storables, show, hide);
        // then
        assertEquals(0,show.size());
        assertEquals(storables.size(),hide.size());
    }
}