package com.example.felix.notizen.views.fragments;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

/**
 * View Model containing an instance of a note of type T extending DatabaseStorable
 *
 * the note is being held in a MutableLiveData
 *
 * @param <T> Type of the DatabaseStorable to be hold
 */
public class EditNoteViewModel<T extends DatabaseStorable> extends ViewModel {

    private MutableLiveData<T> note = new MutableLiveData<>();

    public void setNote(DatabaseStorable note) {
        this.note.setValue((T)note);
    }

    /**
     * utility function to wrap MutableLiveData.getValue()
     * @return value of MutableLiveData
     */
    public T getValue(){
        return note.getValue();
    }

    /**
     * utility function to wrap MutableLiveData.observe
     * @param owner
     * @param observer
     */
    public void observe(LifecycleOwner owner, Observer observer){
        note.observe(owner, observer);
    }


}
