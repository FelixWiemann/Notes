package com.nepumuk.notizen.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.Notes.cTextNote;


public class TextNoteFragment extends  NoteDisplayFragment<cTextNote>{

    /**
     * state if the message field is being changed
     * if it is being changed we don't want the changed data being updated by the view model again
     */
    private boolean typingInMessage = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createView(inflater,container, R.layout.note_display_fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EditText tv = getView().findViewById(R.id.ndf_tv);
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!updatingUI) {
                    // we are not updating the UI, so we are sure that the changes are made by the user
                    typingInMessage = true;
                    // update view model after setting the new message text to the note
                    cTextNote note = mViewModel.getValue();
                    note.setMessage(s.toString());
                    mViewModel.setNote(note);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void updateUI(cTextNote data) {
        // only change the text, if the changes did not happen by user input
        // because otherwise we get into a loop!
        if (!typingInMessage) {
            EditText tv = getView().findViewById(R.id.ndf_tv);
            tv.setText(data.getMessage());
        }
        // changes by typing have been done, we can safely assume new changes are by app
        typingInMessage = false;
    }

}
