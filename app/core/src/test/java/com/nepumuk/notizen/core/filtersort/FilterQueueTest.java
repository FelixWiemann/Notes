package com.nepumuk.notizen.core.filtersort;

import com.nepumuk.notizen.core.objects.StorageObject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
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

    @Test(expected = IllegalArgumentException.class)
    public void filter() {
        // given
        StorageObject task = mock(StorageObject.class);
        AndFilter<StorageObject> filterQueue = new AndFilter<>();
        // when
        filterQueue.filter(task);
        // then expect exception
    }
}
