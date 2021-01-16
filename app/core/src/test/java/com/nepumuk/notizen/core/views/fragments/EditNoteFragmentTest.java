package com.nepumuk.notizen.core.views.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;

import com.nepumuk.notizen.core.R;
import com.nepumuk.notizen.core.testutils.DataBaseStorableTestImpl;
import com.nepumuk.notizen.core.testutils.FragmentTest;
import com.nepumuk.notizen.core.toolbar.InterceptableNavigationToolbar;

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

    private EditNoteViewModel.SaveState saveState;

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
        saveState = new EditNoteViewModel.SaveState(new DataBaseStorableTestImpl());
        FragmentFactory factory = new FragmentFactory(){
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                EditNoteFragment f = (EditNoteFragment) super.instantiate(classLoader, className);
                // set navController before onCreateView is called
                f.getViewLifecycleOwnerLiveData().observeForever(viewLifecycleOwner -> {
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(f.requireView(), navController);
                        new ViewModelProvider(f.requireActivity()).get(EditNoteViewModel.class).setNote(saveState);
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
        scenario = FragmentScenario.launch(EditNoteFragment.class,args.toBundle(), R.style.Theme_AppCompat_Light_NoActionBar,factory);

        scenario.onFragment(fragment -> {
            //new ViewModelProvider(fragment.requireActivity()).get(EditNoteViewModel.class).setNote();
        });
    }

    @Override
    public void tearDown() {
        verify(toolbar,atLeastOnce()).removeInterceptUpNavigationListener(any());
    }
}