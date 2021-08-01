package com.nepumuk.notizen.core.views.fragments;

import android.widget.EditText;

import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.core.R;
import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.testutils.FragmentTest;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

public class NoteDisplayHeaderFragmentTest extends FragmentTest<NoteDisplayHeaderFragment> {
    final String testTitle = "test_title";
    final String newTitle = "new_title";

    StorageObject storageObject = new StorageObject(UUID.randomUUID(),testTitle) {
        @Override
        public int getVersion() {
            return 0;
        }

        @Override
        public DatabaseStorable deepCopy() {
            return this;
        }
    };

    @Override
    public void setUp() throws Exception {
        scenario = FragmentScenario.launchInContainer(NoteDisplayHeaderFragment.class);
        scenario.onFragment(fragment -> fragment.mViewModel.setNote(new EditNoteViewModel.SaveState<>(storageObject)));
    }

    @Test
    public void onCreateView() {
        // tested when cycling through fragment lifecycle states in FragmentTest
    }

    @Test
    public void verifyTextWatcherIsAddedToEditTitle() {
        scenario.onFragment(fragment -> {
            // given
            EditText editText = fragment.getView().findViewById(R.id.et_Title);
            fragment.mViewModel = spy(fragment.mViewModel);
            // when
            editText.setText("test");
            // then
            // verify view model gets updated, when user changed text
            verify(fragment.mViewModel,atLeastOnce()).update();
        });
    }

    @Test
    public void updateUiWhenTyping() {
        scenario.onFragment(fragment -> {
            // given
            fragment.typingInMessage = true;
            storageObject.setTitle(newTitle);
            EditText editText = fragment.getView().findViewById(R.id.et_Title);
            // when
            fragment.updateUI(storageObject);
            // then
            // verify title is not set
            assertNotEquals(newTitle,editText.getText().toString());
            // verify typing state is changed back
            assertFalse(fragment.typingInMessage);
        });
    }

    @Test
    public void updateUiFromDataChange() {
        scenario.onFragment(fragment -> {
            // given
            fragment.typingInMessage = false;
            storageObject.setTitle(newTitle);
            EditText editText = fragment.getView().findViewById(R.id.et_Title);
            // when
            fragment.updateUI(storageObject);
            // then
            // verify title is set
            assertEquals(newTitle,editText.getText().toString());
            // verify typing state is still not typing
            assertFalse(fragment.typingInMessage);
        });
    }
}