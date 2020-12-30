package com.nepumuk.notizen.views.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.Test;

public class TaskNoteFragmentTest extends FragmentTest<TaskNoteFragment> {

    @Test
    public void onCreateView() {
    }

    @Test
    public void updateUI() {
    }

    @Test
    public void onAttach() {
    }

    @Test
    public void registerFabProvider() {
    }

    @Override
    public void setUp() throws Exception {

        // Create a TestNavHostController
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());
        navController.setViewModelStore(new ViewModelStore());
        navController.setGraph(R.navigation.nav_graph_main_fragment);

        FragmentFactory factory = new FragmentFactory(){
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                Fragment  f = super.instantiate(classLoader, className);
                ((TaskNoteFragment) f).setNavigationController(navController);
                return f;
            }
        };
        scenario = FragmentScenario.launchInContainer(TaskNoteFragment.class,null,R.style.Theme_AppCompat_Light_NoActionBar,factory);

    }
}