package com.example.felix.notizen.views.fragments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

public class EditNoteViewModel<T extends DatabaseStorable> extends ViewModel {

    private MutableLiveData<T> note = new MutableLiveData<>();

    public void setNote(DatabaseStorable note) {
        this.note.setValue((T)note);
    }

    public MutableLiveData<T> getNote() {
        return note;
    }
}
