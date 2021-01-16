package com.nepumuk.notizen.core.views;

import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(maxSdk = Build.VERSION_CODES.P)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*", "androidx.*" })
@PrepareForTest(SwipeRecyclerView.class)
public class SwipeRecyclerViewTest {

    Context context;
    SwipeRecyclerView recyclerViewUnderTest;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        // given
        recyclerViewUnderTest = new SwipeRecyclerView<>(context);
    }

    @Test
    public void constructorContext() throws Exception {
        // given
        // when
        SwipeRecyclerView view = new SwipeRecyclerView(context);
        // then
        verifyInit(view);
    }

    @Test
    public void constructorContextBundle() throws Exception {
        // given
        // when
        SwipeRecyclerView view = new SwipeRecyclerView(context, null);
        // then
        verifyInit(view);
    }

    @Test
    public void constructorContextBundleStyle() throws Exception {
        // given
        // when
        SwipeRecyclerView view = new SwipeRecyclerView(context, null ,0);
        // then
        verifyInit(view);
    }

    private void verifyInit(SwipeRecyclerView view){
        // verify we have an adapter
        assertNotNull(view.getAdapter());
        // TODO verify we have an item touch helper
        // verify we have layout
        assertNotNull(view.getLayoutManager());
        // verify we have at least one item decoration
        assertTrue(view.getItemDecorationCount()>0);
    }

    @Test
    public void resetSwipeState() throws Exception {
        // given
        // when
        recyclerViewUnderTest.resetSwipeState();
        // then
        // TODO verify, helpercallback.resetSwipeState has been called
    }

    @Test
    public void setStateFalse() {
        // given
        final boolean newState = false;
        // when
        recyclerViewUnderTest.SetState(newState);
        // then
        assertEquals(newState,recyclerViewUnderTest.isItemSwipeMenuActive);
    }

    @Test
    public void setStateTrue() {
        // given
        final boolean newState = true;
        // when
        recyclerViewUnderTest.SetState(newState);
        // then
        assertEquals(newState,recyclerViewUnderTest.isItemSwipeMenuActive);
    }

}