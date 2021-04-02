package com.nepumuk.notizen.core.filtersort;

import com.nepumuk.notizen.core.objects.IdObject;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

public class SorterTest {

    Sorter<IdObject> sorter = ((t1, T2) -> -100);

    @Test
    public void sorterTest(){
        assertEquals (0,sorter.compare(null, null));
        assertEquals (1,sorter.compare(null, mock(IdObject.class)));
        assertEquals (-1,sorter.compare(mock(IdObject.class), null));
    }
}