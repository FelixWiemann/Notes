package com.nepumuk.notizen.testutils;

import android.os.Build;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static android.os.Looper.getMainLooper;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

/**
 * helper class to provide basic testing functionality for fragments
 * <p></p> provides the following basic tests
 * <li>{@link #testLifeCycle()}, which cycles the fragment through all life cycles</li>
 */
@RunWith(AndroidJUnit4.class)
@Config(maxSdk = Build.VERSION_CODES.P)
public abstract class FragmentTest<T extends Fragment> {

    public FragmentScenario<T> scenario;

    @Before
    public void before() throws Exception {
        setUp();
    }

    @After
    public void after() throws Exception{
        tearDown();
        scenario.moveToState(Lifecycle.State.DESTROYED);
        shadowOf(getMainLooper()).idle();
    }

    @Test
    public void testLifeCycle(){
        // make sure scenario was correctly set up in setUp;
        assertNotNull(scenario);
        scenario.moveToState(Lifecycle.State.CREATED);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            // Moving state to STARTED is not supported on Android API level 23 and lower.
            // This restriction comes from the combination of the Android framework bug around
            // the timing of onSaveInstanceState invocation and its workaround code in FragmentActivity.
            // See http://issuetracker.google.com/65665621#comment3 for more information.
            // at least test everything else...
            scenario.moveToState(Lifecycle.State.STARTED);

        }
        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.moveToState(Lifecycle.State.DESTROYED);
    }

    /**
     * set up the fragment test. this will be called at @Before
     * <p></p> make sure to set the FragmentScenario {@link #scenario} in here
     * <p></p> you need to move it into the correct state for each test yourself.
     * @throws Exception
     */
    public abstract void setUp() throws Exception;

    /**
     * override tear down, if you need to have a tear-down action for your tests
     */
    public void tearDown(){

    }
}
