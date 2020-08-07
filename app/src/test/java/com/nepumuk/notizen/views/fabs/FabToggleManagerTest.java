package com.nepumuk.notizen.views.fabs;

import android.support.design.widget.FloatingActionButton;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class FabToggleManagerTest {


    FloatingActionButton fabMock;

    FabToggleManager toggleManager;

    @Before
    public void setUp() throws Exception {
        toggleManager = new FabToggleManager();
        fabMock = mock(FloatingActionButton.class);
    }

    @Test
    public void toggle_on() {
        // given
        toggleManager.addFabToToggle(fabMock);
        clearInvocations(fabMock);
        // when
        toggleManager.toggle();
        // then
        verify(fabMock).show();
    }

    @Test
    public void toggle_off() {
        // given
        toggleManager.addFabToToggle(fabMock);
        toggleManager.toggle();
        clearInvocations(fabMock);

        // when
        toggleManager.toggle();

        // then
        verify(fabMock).hide();
    }

    @Test
    public void addFabToToggleInOffState() {
        // given setup

        // when
        toggleManager.addFabToToggle(fabMock);

        // then
        verify(fabMock).hide();
    }

    @Test
    public void addFabToToggleInOnState() {
        // given
        toggleManager.toggle();

        // when
        toggleManager.addFabToToggle(fabMock);

        // then
        verify(fabMock).show();
    }

    @Test
    public void removeFabToToggle() {
        // given
        toggleManager.addFabToToggle(fabMock);
        clearInvocations(fabMock);

        // when
        toggleManager.removeFabToToggle(fabMock);

        //then
        toggleManager.toggle();
        verifyNoMoreInteractions(fabMock);
    }
}