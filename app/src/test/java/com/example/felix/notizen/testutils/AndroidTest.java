package com.example.felix.notizen.testutils;


import android.support.annotation.CallSuper;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


/**
 * Helper test class to be extended, if android stuff needs to be mocked
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public abstract class AndroidTest {

    @CallSuper
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Log.class);
    }

}
