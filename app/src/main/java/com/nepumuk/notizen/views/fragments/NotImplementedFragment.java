package com.nepumuk.notizen.views.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.utils.db_access.DatabaseStorable;

public class NotImplementedFragment extends NoteDisplayFragment<DatabaseStorable> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createView(inflater,container, R.layout.fragment_view_not_implemented);
    }

    @Override
    protected void updateUI(DatabaseStorable updatedData) {

    }
}
