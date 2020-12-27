package com.nepumuk.notizen.views.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.ViewModelStoreOwner;

import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.powermock.api.mockito.PowerMockito.mock;

@Ignore("see TODO below")
public class CreateTaskDialogFragmentTest extends FragmentTest<CreateTaskDialogFragment> {

    @Test
    public void onCreateView() {
    }

    @Test
    public void onViewCreated() {
    }

    @Override
    public void setUp() throws Exception {
        scenario = FragmentScenario.launchInContainer(CreateTaskDialogFragment.class,null, new CreateTaskDialogFragmentFactory());
    }
    // TODO rework CreateTaskDialogFragment to be testable
    //  Argument LifecycleOwner kills this test atm
    @Ignore
    static class CreateTaskDialogFragmentFactory extends FragmentFactory{
        @NonNull
        @Override
        public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
            try {
                Class<? extends Fragment> cls = loadFragmentClass(classLoader, className);
                return cls.getConstructor(ViewModelStoreOwner.class).newInstance(mock(ViewModelStoreOwner.class));
            } catch (java.lang.InstantiationException e) {
                throw new Fragment.InstantiationException("Unable to instantiate fragment " + className
                        + ": make sure class name exists, is public, and has an"
                        + " empty constructor that is public", e);
            } catch (IllegalAccessException e) {
                throw new Fragment.InstantiationException("Unable to instantiate fragment " + className
                        + ": make sure class name exists, is public, and has an"
                        + " empty constructor that is public", e);
            } catch (NoSuchMethodException e) {
                throw new Fragment.InstantiationException("Unable to instantiate fragment " + className
                        + ": could not find Fragment constructor", e);
            } catch (InvocationTargetException e) {
                throw new Fragment.InstantiationException("Unable to instantiate fragment " + className
                        + ": calling Fragment constructor caused an exception", e);
            }
        }
    }
}