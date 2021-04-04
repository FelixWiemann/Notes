package com.nepumuk.notizen.core.filtersort;

import com.nepumuk.notizen.core.objects.StorageObject;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

public class FilterQueueTest {
    @Test
    public void addFilter() {
        // given
        AndFilter<StorageObject> filterQueue = new AndFilter<>();
        // when
        filterQueue.appendFilter(new FilterHideAll<>());
        // then
        assertEquals(1, filterQueue.filters.size());
    }

    @Test
    public void filter() {
        // given
        StorageObject task = mock(StorageObject.class);
        FilterQueueTestImplementation filterQueue = new FilterQueueTestImplementation();
        // when
        boolean contains = filterQueue.filter(task);
        // then
        assertTrue(contains);
    }

    static class FilterQueueTestImplementation extends FilterQueue<StorageObject>{
        @Override
        public void filter(List<StorageObject> toFilter, List<StorageObject> keep, List<StorageObject> discard) {
            keep.addAll(toFilter);
        }
    }
}
