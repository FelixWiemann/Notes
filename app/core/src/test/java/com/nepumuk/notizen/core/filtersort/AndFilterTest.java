package com.nepumuk.notizen.core.filtersort;

import com.nepumuk.notizen.core.objects.SortableObject;
import com.nepumuk.notizen.core.objects.StorageObject;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

public class AndFilterTest {
    @Test
    public void testFilter() {
        // given these objects
        StorageObject obj1 = mock(StorageObject.class);
        StorageObject obj2 = mock(StorageObject.class);
        StorageObject obj3 = mock(StorageObject.class);
        StorageObject obj4 = mock(StorageObject.class);
        StorageObject obj5 = mock(StorageObject.class);
        StorageObject obj6 = mock(StorageObject.class);
        StorageObjectTestImpl obj7 = new StorageObjectTestImpl();
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
        // with this AndFilter config
        AndFilter<SortableObject> queue = new AndFilter<>()
                .appendFilter(new FilterShowAll<>())
                .appendFilter(new HideAllOfType<>(StorageObjectTestImpl.class));
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
        assertTrue(kept.contains(obj5));
        assertTrue(kept.contains(obj6));
        assertFalse(kept.contains(obj7));
    }

    static class StorageObjectTestImpl extends StorageObject{

        @Override
        public int getVersion() {
            return 0;
        }
    }

}