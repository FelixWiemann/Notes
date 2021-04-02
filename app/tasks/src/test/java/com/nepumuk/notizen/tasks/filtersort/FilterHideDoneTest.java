package com.nepumuk.notizen.tasks.filtersort;

import com.nepumuk.notizen.tasks.objects.Task;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

public class FilterHideDoneTest {
    @Mock
    Task taskDone;
    @Mock
    Task taskNotDone;


    @Test
    public void testHideDone() {
        MockitoAnnotations.initMocks(this);
        // given
        when(taskDone.isDone()).thenReturn(true);
        when(taskNotDone.isDone()).thenReturn(false);
        FilterHideDone filterHideDone = new FilterHideDone();

        // when/then
        assertFalse(filterHideDone.filter(taskDone));
        assertTrue(filterHideDone.filter(taskNotDone));


    }

}