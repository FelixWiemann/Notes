package com.example.felix.notizen.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.felix.notizen.R;
import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

public class NotImplementedFragment extends NoteDisplayFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createView(inflater,container, R.layout.fragment_view_not_implemented);
    }

    @Override
    protected void updateUI(DatabaseStorable updatedData) {

    }
}
