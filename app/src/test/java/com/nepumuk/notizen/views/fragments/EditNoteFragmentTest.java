package com.nepumuk.notizen.views.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.testutils.FragmentTest;
import com.nepumuk.notizen.views.InterceptableNavigationToolbar;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

public class EditNoteFragmentTest extends FragmentTest<EditNoteFragment> {

    @Mock
    private InterceptableNavigationToolbar toolbar;

    @Test
    public void onCreate() {
        scenario.onFragment(fragment -> {
            // given
            // when
            fragment.requireActivity().onBackPressed();
            // then
            verify(fragment.callback,times(1)).handleOnBackPressed();
            verify(toolbar,times(1)).addInterceptUpNavigationListener(any());
        });
    }

    @Override
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Create a TestNavHostController
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());
        navController.setViewModelStore(new ViewModelStore());
        navController.setGraph(R.navigation.nav_graph_main_fragment);

        FragmentFactory factory = new FragmentFactory(){
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                EditNoteFragment f = (EditNoteFragment) super.instantiate(classLoader, className);
                // set navController before onCreateView is called
                f.getViewLifecycleOwnerLiveData().observeForever(viewLifecycleOwner -> {
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(f.requireView(), navController);
                    }
                });
                // inject mocked toolbar
                f.toolbar = toolbar;
                // spy on the registered callback
                f.callback = spy(f.callback);
                return f;
            }
        };

        EditNoteFragmentArgs args = new EditNoteFragmentArgs.Builder().setType("TextNote").build();
        scenario = FragmentScenario.launchInContainer(EditNoteFragment.class,args.toBundle(), R.style.Theme_AppCompat_Light_NoActionBar,factory);
    }

    @Override
    public void tearDown() {
        verify(toolbar,atLeastOnce()).removeInterceptUpNavigationListener(any());
    }
}