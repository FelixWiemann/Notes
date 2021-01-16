package com.nepumuk.notizen.textnotes;

import android.widget.EditText;

import androidx.fragment.app.testing.FragmentScenario;

import com.nepumuk.notizen.textnotes.testutils.FragmentTest;
import com.nepumuk.notizen.core.views.fragments.EditNoteViewModel;
import com.nepumuk.notizen.textnotes.objects.TextNote;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

public class TextNoteFragmentTest extends FragmentTest<TextNoteFragment> {

    final String testTitle = "test_title";
    final String testText = "test_text";
    final String newText = "new_text";
    TextNote storageObject = new TextNote(UUID.randomUUID(),testTitle,testText);


    @Override
    public void setUp() throws Exception {
        scenario = FragmentScenario.launchInContainer(TextNoteFragment.class);
        scenario.onFragment(fragment ->{
            fragment.mViewModel.setNote(new EditNoteViewModel.SaveState<>(storageObject));
        });
    }

    @Test
    public void onCreateView() {
        // tested when cycling through fragment lifecycle states in FragmentTest
    }

    public void verifyTextWatcherIsAddedToEditTitle() {
        scenario.onFragment(fragment -> {
            // given
            EditText editText = fragment.getView().findViewById(R.id.ndf_tv);
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
            storageObject.setMessage(newText);
            EditText editText = fragment.getView().findViewById(R.id.ndf_tv);
            // when
            fragment.updateUI(storageObject);
            // then
            // verify title is not set
            assertNotEquals(newText,editText.getText().toString());
            // verify typing state is changed back
            assertFalse(fragment.typingInMessage);
        });
    }

    @Test
    public void updateUiFromDataChange() {
        scenario.onFragment(fragment -> {
            // given
            fragment.typingInMessage = false;
            storageObject.setMessage(newText);
            EditText editText = fragment.getView().findViewById(R.id.ndf_tv);
            // when
            fragment.updateUI(storageObject);
            // then
            // verify title is set
            assertEquals(newText,editText.getText().toString());
            // verify typing state is still not typing
            assertFalse(fragment.typingInMessage);
        });
    }
}