package com.nepumuk.notizen.views.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.StorageObject;

/**

 */
public class NoteDisplayHeaderFragment extends NoteDisplayFragment<StorageObject> {


    public NoteDisplayHeaderFragment() {
        super();
        // Required empty public constructor
    }

    /**
     * state if the message field is being changed
     * if it is being changed we don't want the changed data being updated by the view model again
     */
    private boolean typingInMessage = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createView(inflater,container, R.layout.fragment_note_display_header);
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        EditText tv = view.findViewById(R.id.et_Title);
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!updatingUI) {
                    // we are not updating the UI, so we are sure that the changes are made by the user
                    typingInMessage = true;
                    // update view model after setting the new message text to the note
                    StorageObject note = mViewModel.getValue();
                    note.setTitle(s.toString());
                    mViewModel.setNote(note);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void updateUI(StorageObject data) {
        // only change the text, if the changes did not happen by user input
        // because otherwise we get into a loop!
        if (!typingInMessage) {
            EditText tv = getView().findViewById(R.id.et_Title);
            tv.setText(data.getTitle());
        }
        // changes by typing have been done, we can safely assume new changes are by app
        typingInMessage = false;
    }


}
