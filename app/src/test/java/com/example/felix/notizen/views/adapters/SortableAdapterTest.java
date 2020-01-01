package com.example.felix.notizen.views.adapters;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.testutils.DataBaseStorableTestImpl;
import com.example.felix.notizen.views.viewsort.FilterHideAll;
import com.example.felix.notizen.views.viewsort.FilterShowAll;
import com.example.felix.notizen.views.viewsort.ViewFilter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SortableAdapter.class)
public class SortableAdapterTest {

    private SortableAdapter adapterUnderTest;

    private DatabaseStorable s1;
    private DatabaseStorable s2;

    private ViewFilter filterMock;
    private FilterShowAll mockShowAll;


    @Before
    public void setUp() {
        adapterUnderTest = spy(new SortableAdapter());
        // don't do anything on baseAdapter.notifyDataSetChanged, as not mocked
        doNothing().when(adapterUnderTest).notifyDataSetChanged();

        filterMock = mock(ViewFilter.class);
        doCallRealMethod().when(filterMock).filter(ArgumentMatchers.<DatabaseStorable>anyList(), ArgumentMatchers.<DatabaseStorable>anyList(), ArgumentMatchers.<DatabaseStorable>anyList());
        mockShowAll = spy(new FilterShowAll());

        s1 = new DataBaseStorableTestImpl();
        s2 = new DataBaseStorableTestImpl();
    }

    @Test
    public void getItemViewType() {
        // given
        int result;
        // when
        result = adapterUnderTest.getItemViewType(1);
        // then
        assertEquals(-1, result);
    }

    @Test
    public void getCount() {
        // I assume that list.add() works...
    }

    @Test
    public void getItem() {
        // I assume that list.get(itemPosition) works...
        adapterUnderTest.add(new DataBaseStorableTestImpl());
        adapterUnderTest.getItem(0);
    }

    @Test
    public void getItemId() {
        // given
        int itemPos = 12;
        // when
        long itemId = adapterUnderTest.getItemId(itemPos);
        // then
        assertEquals(itemPos, itemId);
    }

    @Ignore ("TODO ")
    @Test
    public void sort() {
        // given
        // when
        // then
    }

    @Ignore ("TODO ")
    @Test
    public void sort1() {
        // given
        // when
        // then
    }

    @Test
    public void filter() throws Exception{
        // given
        whenNew(FilterShowAll.class).withNoArguments().thenReturn(mockShowAll);
        // when
        adapterUnderTest.filter();
        // then
        verify(adapterUnderTest).notifyDataSetChanged();
        verify(mockShowAll, times(2)).filter(ArgumentMatchers.<DatabaseStorable>anyList(), ArgumentMatchers.<DatabaseStorable>anyList(), ArgumentMatchers.<DatabaseStorable>anyList());
    }

    @Test
    public void filterSpecificFilter() {
        // given
        adapterUnderTest.add(s1);
        adapterUnderTest.add(s2);
        when(filterMock.filter(any(DatabaseStorable.class))).thenReturn(false);
        // when
        adapterUnderTest.filter(filterMock);
        // then
        assertEquals(0, adapterUnderTest.getCount());
    }
    @Test
    public void filterSpecificFilterSecondTime() {
        // given
        filterSpecificFilter();
        when(filterMock.filter(any(DatabaseStorable.class))).thenReturn(true);
        // when
        adapterUnderTest.filter(filterMock);
        // then
        assertEquals(2, adapterUnderTest.getCount());
    }

    @Test
    public void clear() {
        // given
        adapterUnderTest.add(s1);
        adapterUnderTest.add(s2);
        // when
        adapterUnderTest.clear();
        // then
        assertEquals(0, adapterUnderTest.getAllObjects().size());
    }

    @Ignore ("TODO ")
    @Test
    public void addAll() {
        // given
        // when
        // then
    }

    @Test
    public void add() {
        // given
        int expectedCount = 10;

        // when
        for (int i = 0; i < expectedCount; i++) {
            adapterUnderTest.add(new DataBaseStorableTestImpl());
        }
        // then
        int actualCount = adapterUnderTest.getCount();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void clearFilter() {
        // given
        // add s1 and hide it
        adapterUnderTest.add(s1);
        adapterUnderTest.filter(mockShowAll);
        // add s2 and don't hide it
        adapterUnderTest.add(s2);
        clearInvocations(adapterUnderTest);
        // when
        adapterUnderTest.clearFilter();
        // then
        verify(adapterUnderTest).filter();
        verify(mockShowAll).filter(any(DatabaseStorable.class));

    }

    @Test
    public void clearSort() {
        // given
        clearInvocations(adapterUnderTest);
        // when
        adapterUnderTest.clear();
        // then
        // next sorting shouldn't do anything
        adapterUnderTest.sort();
        verify(adapterUnderTest, never()).notifyDataSetChanged();
    }

    @Test
    public void getAllObjects() {
        // given
        // add s1 and hide it
        adapterUnderTest.add(s1);
        adapterUnderTest.filter(new FilterHideAll());
        // add s2 and don't hide it
        adapterUnderTest.add(s2);
        // when
        List<DatabaseStorable> result = adapterUnderTest.getAllObjects();
        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(s1));
        assertTrue(result.contains(s2));
    }

    @Test
    public void removeShown() {
        // given
        adapterUnderTest.add(s1);
        adapterUnderTest.filter(new FilterHideAll());
        // add s2 and don't hide it
        adapterUnderTest.add(s2);
        // when
        adapterUnderTest.remove(s2);
        // then
        assertTrue(adapterUnderTest.getAllObjects().contains(s1));
        assertFalse(adapterUnderTest.getAllObjects().contains(s2));
    }

    @Test
    public void removeHidden() {
        // given
        adapterUnderTest.add(s1);
        adapterUnderTest.filter(new FilterHideAll());
        // add s2 and don't hide it
        adapterUnderTest.add(s2);
        // when
        adapterUnderTest.remove(s1);
        // then
        assertTrue(adapterUnderTest.getAllObjects().contains(s2));
        assertFalse(adapterUnderTest.getAllObjects().contains(s1));
    }

    @Ignore ("TODO ")
    @Test
    public void getView() {
        // TODO write
        // given
        // when
        // then
    }

    @Test
    public void replace() {
        // given
        ArrayList<DatabaseStorable> newList = new ArrayList<>();
        // when
        adapterUnderTest.replace(newList);
        // then
        verify(adapterUnderTest).clear();
        verify(adapterUnderTest).addAll(ArgumentMatchers.same(newList));
        verify(adapterUnderTest).filter();
        verify(adapterUnderTest).sort();
    }
}