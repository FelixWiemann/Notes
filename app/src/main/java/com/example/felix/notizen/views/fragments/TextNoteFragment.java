package com.example.felix.notizen.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Notes.cTextNote;


public class TextNoteFragment extends  NoteDisplayFragment<cTextNote>{

    public TextNoteFragment(){
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createView(inflater,container, R.layout.note_display_fragment);
    }

    @Override
    protected void onUpdateData(cTextNote data) {
        EditText tv = getView().findViewById(R.id.ndf_tv);
        tv.setText(data.getMessage());
    }

}
