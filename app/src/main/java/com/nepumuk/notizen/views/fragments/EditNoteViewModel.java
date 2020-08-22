package com.nepumuk.notizen.views.fragments;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.nepumuk.notizen.utils.db_access.DatabaseStorable;

/**
 * View Model containing an instance of a note of type T extending DatabaseStorable
 *
 * the note is being held in a MutableLiveData
 *
 * @param <T> Type of the DatabaseStorable to be hold
 */
public class EditNoteViewModel<T extends DatabaseStorable> extends ViewModel {

    private MutableLiveData<T> note = new MutableLiveData<>();

    public void setNote(T note) {
        this.note.setValue(note);
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
     * @param owner of the lifecycle
     * @param observer observing changes
     */
    public void observe(LifecycleOwner owner, Observer<T> observer){
        note.observe(owner, observer);
    }


}
