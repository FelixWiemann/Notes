package com.nepumuk.notizen.core.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nepumuk.notizen.core.R;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;

public class NotImplementedFragment extends NoteDisplayFragment<DatabaseStorable> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createView(inflater,container, R.layout.fragment_view_not_implemented);
    }

    @Override
    protected void updateUI(DatabaseStorable updatedData) {

    }
}
