package com.example.felix.notizen.views.adapters;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.testutils.DataBaseStorableTestImpl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.spy;


@RunWith(PowerMockRunner.class)
@PrepareForTest(BaseRecyclerAdapter.class)
public class BaseRecyclerAdapterTest {

    private BaseRecyclerAdapter<DatabaseStorable> adapterUnderTest;

    private DatabaseStorable s1;
    private DatabaseStorable s2;


    @Before
    public void setUp(){
        s1 = new DataBaseStorableTestImpl();
        s2 = new DataBaseStorableTestImpl();

        adapterUnderTest = spy(new BaseRecyclerAdapter<>(new ArrayList<DatabaseStorable>()));

        doNothing().when(adapterUnderTest).notifyDataSetChanged();


    }

    @Ignore("TODO ")
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
        int actualCount = adapterUnderTest.getItemCount();
        assertEquals(expectedCount, actualCount);
    }


    @Test
    public void getCount() {
        // I assume that list.add() works...
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

    @Ignore("basically just testing the call to the factory, test factory instead!")
    @Test
    public void getItemViewType() {
        // given
        int result;
        // when
        result = adapterUnderTest.getItemViewType(1);
        // then
        assertEquals(-1, result);
    }

}