package com.nepumuk.notizen.views.adapters;

import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.objects.filtersort.FilterHideAll;
import com.nepumuk.notizen.objects.filtersort.FilterShowAll;
import com.nepumuk.notizen.objects.filtersort.SortProvider;
import com.nepumuk.notizen.objects.filtersort.ViewFilter;
import com.nepumuk.notizen.testutils.DataBaseStorableTestImpl;

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
import static org.mockito.Mockito.atLeastOnce;
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
@PrepareForTest(SortableRecyclerAdapter.class)
public class SortableRecyclerAdapterTest {

    private SortableRecyclerAdapter<StorageObject> adapterUnderTest;

    private StorageObject s1;
    private StorageObject s2;

    private ViewFilter<StorageObject> filterMock;
    private FilterShowAll<StorageObject> mockShowAll;


    @Before
    public void setUp() {
        adapterUnderTest = spy(new SortableRecyclerAdapter<>(new ArrayList<StorageObject>(), 0));
        // don't do anything on baseAdapter.notifyDataSetChanged, as not mocked
        doNothing().when(adapterUnderTest).notifyDataSetChanged();

        filterMock = (ViewFilter<StorageObject>) mock(ViewFilter.class);
        doCallRealMethod().when(filterMock).filter(ArgumentMatchers.<StorageObject>anyList(), ArgumentMatchers.<StorageObject>anyList(), ArgumentMatchers.<StorageObject>anyList());
        mockShowAll = spy(new FilterShowAll());

        s1 = new DataBaseStorableTestImpl();
        s2 = new DataBaseStorableTestImpl();
    }

    @Test
    public void sort() {
        // given
        adapterUnderTest.add(s1);
        adapterUnderTest.add(s2);
        // when
        adapterUnderTest.sort(SortProvider.SortByType);
        // then
        verify(adapterUnderTest, times(1)).sort();
        verify(adapterUnderTest, atLeastOnce()).notifyDataSetChanged();
    }

    @Test
    public void filter() throws Exception{
        // given
        whenNew(FilterShowAll.class).withNoArguments().thenReturn(mockShowAll);
        // when
        adapterUnderTest.filter();
        // then
        verify(adapterUnderTest).notifyDataSetChanged();
        verify(mockShowAll, times(2)).filter(ArgumentMatchers.<StorageObject>anyList(), ArgumentMatchers.<StorageObject>anyList(), ArgumentMatchers.<StorageObject>anyList());
    }

    @Test
    public void filterSpecificFilter() {
        // given
        adapterUnderTest.add(s1);
        adapterUnderTest.add(s2);
        when(filterMock.filter(any(StorageObject.class))).thenReturn(false);
        // when
        adapterUnderTest.filter(filterMock);
        // then
        assertEquals(0, adapterUnderTest.getItemCount());
    }
    @Test
    public void filterSpecificFilterSecondTime() {
        // given
        filterSpecificFilter();
        when(filterMock.filter(any(StorageObject.class))).thenReturn(true);
        // when
        adapterUnderTest.filter(filterMock);
        // then
        assertEquals(2, adapterUnderTest.getItemCount());
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
        verify(mockShowAll).filter(any(StorageObject.class));

    }

    @Test
    public void clearSort() {
        // given
        clearInvocations(adapterUnderTest);
        // when
        adapterUnderTest.clear();
        // then
        verify(adapterUnderTest, times(1)).notifyDataSetChanged();
        clearInvocations(adapterUnderTest);
        // next sorting shouldn't do anything
        adapterUnderTest.sort();
        verify(adapterUnderTest,never()).notifyDataSetChanged();

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
        List<StorageObject> result = adapterUnderTest.getAllObjects();
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
        ArrayList<StorageObject> newList = new ArrayList<>();
        // when
        adapterUnderTest.replace(newList);
        // then
        verify(adapterUnderTest).clear();
        verify(adapterUnderTest).addAll(ArgumentMatchers.same(newList));
        verify(adapterUnderTest).filter();
        verify(adapterUnderTest).sort();
    }
}