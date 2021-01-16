package com.nepumuk.notizen.tasks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;

import com.nepumuk.notizen.tasks.testutils.FragmentTest;

import org.junit.Test;

public class CreateTaskDialogFragmentTest extends FragmentTest<CreateTaskDialogFragment> {

    @Test
    public void onViewCreated() {
    }

    @Override
    public void setUp() throws Exception {
        // Create a TestNavHostController
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());
        navController.setViewModelStore(new ViewModelStore());
        navController.setGraph(R.navigation.nav_graph_task_note_fragment);
        navController.setCurrentDestination(R.id.createTaskDialogFragment);

        FragmentFactory factory = new FragmentFactory(){
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                CreateTaskDialogFragment f = (CreateTaskDialogFragment) super.instantiate(classLoader, className);
                f.getViewLifecycleOwnerLiveData().observeForever(viewLifecycleOwner -> {
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(f.requireView(), navController);
                    }
                });
                return f;
            }
        };
        scenario = FragmentScenario.launchInContainer(CreateTaskDialogFragment.class,null, R.style.Theme_AppCompat_Light_NoActionBar,factory);
    }
}