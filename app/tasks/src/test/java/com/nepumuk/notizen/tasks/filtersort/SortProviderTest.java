package com.nepumuk.notizen.tasks.filtersort;

import com.nepumuk.notizen.tasks.objects.Task;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;

public class SortProviderTest {
    @Mock
    Task taskDone1;
    @Mock
    Task taskDone2;
    @Mock
    Task taskNotDone;

    @Test
    public void testSortTasksDone(){

        MockitoAnnotations.initMocks(this);
        // given
        when(taskDone1.isDone()).thenReturn(true);
        when(taskDone2.isDone()).thenReturn(true);
        when(taskNotDone.isDone()).thenReturn(false);

        // when/then
        assertEquals(0, SortProvider.SortTasksDone.compare(taskDone1, taskDone2));
        assertTrue(0<SortProvider.SortTasksDone.compare(taskDone1, taskNotDone));
        assertTrue(0>SortProvider.SortTasksDone.compare(taskNotDone,taskDone1));
    }

}