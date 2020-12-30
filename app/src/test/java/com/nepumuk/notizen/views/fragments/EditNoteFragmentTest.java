package com.nepumuk.notizen.views.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.testutils.FragmentTest;

import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

@Ignore("Nav Host issue")
public class EditNoteFragmentTest extends FragmentTest<EditNoteFragment> {

    @Test
    public void onCreate() {
        scenario.onFragment(fragment -> {
            // given
            // when
            fragment.requireActivity().onBackPressed();
            // then
            verify(fragment.callback,times(1)).handleOnBackPressed();
        });
    }

    @Test
    public void onCreateView() {
    }

    @Test
    public void onStart() {
    }

    @Test
    public void onAttach() {
    }

    @Test
    public void discard() {
    }

    @Test
    public void saveAndExit() {
    }

    @Test
    public void cancelExit() {
    }

    @Test
    public void getFab() {
    }

    @Test
    public void saveDialogIfChanged() {
    }

    @Test
    public void getModel() {
    }

    @Test
    public void onCreateOptionsMenu() {
    }

    @Test
    public void onOptionsItemSelected() {
    }

    @Override
    public void setUp() throws Exception {
        EditNoteFragmentArgs args = new EditNoteFragmentArgs.Builder().setType("TextNote").build();
        scenario = FragmentScenario.launchInContainer(EditNoteFragment.class,args.toBundle(), R.style.Theme_AppCompat_Light_NoActionBar,new FragmentFactory(){
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                EditNoteFragment f = (EditNoteFragment) super.instantiate(classLoader, className);
                f.callback = spy(f.callback);
                return f;
            }
        });
    }
}