package com.example.felix.notizen.testutils;


import android.content.Intent;
import android.support.annotation.CallSuper;
import android.util.Log;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


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
        when(Log.e(anyString(),anyString(),any(Throwable.class))).then(new Answer<Object>() {
            /**
             * @param invocation the invocation on the mock.
             * @return the value to be returned
             * @throws Throwable the throwable to be thrown
             */
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String TAG = invocation.getArgument(0);
                String Message = invocation.getArgument(1);

                Throwable exs = invocation.getArgument(2);
                System.out.println(TAG + ": "+ Message + ": ");
                printException(exs, "");
                return null;
            }
        });
        PowerMockito.whenNew(Intent.class).withNoArguments().thenReturn(mockedIntent);
        PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(mock(Intent.class));
    }

    private void printException(Throwable throwable, String indent){
        StringBuilder exText = new StringBuilder();
        for (StackTraceElement el: throwable.getStackTrace()) {
            exText.append(indent).append(el.toString()).append("\n");
        }
        System.out.println(throwable.toString() + "\n " + exText);
        if (throwable.getCause()!= null){
            System.out.println(indent + "Caused By: ");
            printException(throwable.getCause(), indent + "    ");
        }
    }
}
