package com.nepumuk.notizen.core.testutils;


import android.content.Intent;
import android.util.Log;

import androidx.annotation.CallSuper;

import com.nepumuk.notizen.core.utils.ResourceManager;
import com.nepumuk.notizen.db.AppDataBaseHelper;
import com.nepumuk.notizen.core.utils.BackgroundWorker;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Helper test class to be extended, if android stuff needs to be mocked
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, ResourceManager.class, AppDataBaseHelper.class, BackgroundWorker.class})
@PowerMockIgnore("jdk.internal.reflect.*")
public abstract class AndroidTest {

    /**
     * intent that is being mocked and can be used for your tests
     * modify it as necessary in your test case
     */
    @Mock
    public Intent mockedIntent;

    public final String ResourcesGetStringValue = "DEFAULT_TEST_STRING";

    @Captor
    ArgumentCaptor<Runnable> captor;

    @CallSuper
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Log.class);
        when(Log.e(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(Throwable.class))).then(new Answer<Object>() {
            /**
             * @param invocation the invocation on the mock.
             * @return the value to be returned
             */
            @Override
            public Object answer(InvocationOnMock invocation) {
                String TAG = invocation.getArgument(0);
                String Message = invocation.getArgument(1);

                Throwable exs = invocation.getArgument(2);
                System.out.println(TAG + ": "+ Message + ": ");
                printException(exs, "");
                return null;
            }
        });

        PowerMockito.mockStatic(ResourceManager.class);
        when(ResourceManager.getString(ArgumentMatchers.anyInt())).thenReturn(ResourcesGetStringValue);
        PowerMockito.whenNew(Intent.class).withNoArguments().thenReturn(mockedIntent);
        PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(mock(Intent.class));
       // PowerMockito.whenNew(BackgroundWorker.class).withArguments(captor.capture()).thenReturn(new BackgroundWorkerTestImpl(captor.getValue()));

        PowerMockito.mockStatic(AppDataBaseHelper.class);
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

    private class BackgroundWorkerTestImpl extends BackgroundWorker {

        Runnable runnable;

        public BackgroundWorkerTestImpl(Runnable runnable){
            super(runnable);
            this.runnable = runnable;
        }

        @Override
        public void start(){
            runnable.run();
        }

    }

}
