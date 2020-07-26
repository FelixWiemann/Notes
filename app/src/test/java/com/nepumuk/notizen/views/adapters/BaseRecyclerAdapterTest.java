package com.nepumuk.notizen.views.adapters;

import com.nepumuk.notizen.objects.cStorageObject;
import com.nepumuk.notizen.testutils.DataBaseStorableTestImpl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.spy;


@RunWith(PowerMockRunner.class)
@PrepareForTest(BaseRecyclerAdapter.class)
public class BaseRecyclerAdapterTest {

    private BaseRecyclerAdapter<cStorageObject> adapterUnderTest;

    private cStorageObject s1;
    private cStorageObject s2;


    @Before
    public void setUp(){
        s1 = new DataBaseStorableTestImpl();
        s2 = new DataBaseStorableTestImpl();
        adapterUnderTest = spy(new BaseRecyclerAdapter<>(new ArrayList<cStorageObject>(),0));
        doNothing().when(adapterUnderTest).notifyDataSetChanged();
    }

    @Ignore("TODO ")
    @Test
    public void addAll() {
        // given
        // when
        // then
        int actualCount = adapterUnderTest.getItemCount();
        //assertEquals(expectedCount, actualCount);
        verify(adapterUnderTest,times(actualCount)).notifyDataSetChanged();
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
        verify(adapterUnderTest,times(actualCount)).notifyDataSetChanged();
    }


    @Test
    public void getCount() {
        // I assume that list.size() works...
        adapterUnderTest.getItemCount();
    }

    @Test
    public void getItem() {
        adapterUnderTest.add(s1);
        // I assume that list.get() works...
        adapterUnderTest.getItem(0);
    }

    @Test
    public void clear() {
        // given
        adapterUnderTest.add(s1);
        adapterUnderTest.add(s2);
        clearInvocations(adapterUnderTest);
        // when
        adapterUnderTest.clear();
        // then
        assertEquals(0, adapterUnderTest.getAllObjects().size());
        verify(adapterUnderTest,times(1)).notifyDataSetChanged();
    }

    @Test
    public void getItemViewType() {
        // given
        int result;
        adapterUnderTest.add(s1);
        // when
        result = adapterUnderTest.getItemViewType(0);
        // then
        assertEquals(-1, result);
    }

}