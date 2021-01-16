package com.nepumuk.notizen.textnotes;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nepumuk.notizen.core.utils.SimpleTextWatcher;
import com.nepumuk.notizen.core.views.fragments.NoteDisplayFragment;
import com.nepumuk.notizen.textnotes.objects.TextNote;


public class TextNoteFragment extends NoteDisplayFragment<TextNote> {

    /**
     * state if the message field is being changed
     * if it is being changed we don't want the changed data being updated by the view model again
     */
    public boolean typingInMessage = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createView(inflater,container, R.layout.note_display_fragment);
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        EditText tv = view.findViewById(R.id.ndf_tv);
        tv.addTextChangedListener(textWatcher);
    }

    @Override
    public void updateUI(TextNote data) {
        // only change the text, if the changes did not happen by user input
        // because otherwise we get into a loop!
        if (!typingInMessage) {
            EditText tv = getView().findViewById(R.id.ndf_tv);
            tv.setText(data.getMessage());
        }
        // changes by typing have been done, we can safely assume new changes are by app
        typingInMessage = false;
    }

    private final TextWatcher textWatcher = new SimpleTextWatcher(editable->{
        if (!updatingUI) {
            // we are not updating the UI, so we are sure that the changes are made by the user
            typingInMessage = true;
            // update view model after setting the new message text to the note
            mViewModel.getValue().setMessage(editable.toString());
            mViewModel.update();
        }
    });

}
