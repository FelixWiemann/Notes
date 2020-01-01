package com.example.felix.notizen.testutils;


import android.content.Intent;
import android.support.annotation.CallSuper;
import android.util.Log;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.mock;


/**
 * Helper test class to be extended, if android stuff needs to be mocked
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public abstract class AndroidTest {

    /**
     * intent that is being mocked and can be used for your tests
     * modify it as necessary in your test case
     */
    @Mock
    public Intent mockedIntent;

    @CallSuper
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Log.class);
        PowerMockito.whenNew(Intent.class).withNoArguments().thenReturn(mockedIntent);
        PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(mock(Intent.class));
    }
}
