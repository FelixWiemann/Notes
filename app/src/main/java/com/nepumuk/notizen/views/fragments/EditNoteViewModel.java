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

    private final MutableLiveData<SaveState<T>> note = new MutableLiveData<>();

    /**
     * replaces the currently stored value with the given one
     * @param note
     */
    public void replace(T note){
        this.note.setValue(new SaveState<>(note));
    }

    /**
     * sets the given value as the data
     * synchronously if value was null, otherwise async.
     * use {@link EditNoteViewModel#replace(DatabaseStorable)} if setting shall be made immediately and cannot wait
     * @param note
     */
    public void setNote(T note) {
        // first time set it, afterwards post it asynchronously
        if (this.note.getValue()== null){
            this.note.setValue(new SaveState<>(note));
        }else{
            this.note.postValue(new SaveState<>(note));
        }
    }

    public SaveState<T> getSaveState(){
        return note.getValue();
    }

    /**
     * utility function to wrap MutableLiveData.getValue()
     * @return value of MutableLiveData
     */
    public T getValue(){
        return note.getValue().data;
    }

    public boolean isValueSet(){
        return  note.getValue()!=null;
    }

    /**
     * utility function to wrap MutableLiveData.observe
     * @param owner of the lifecycle
     * @param observer observing changes
     */
    public void observe(LifecycleOwner owner, Observer<SaveState<T>> observer){
        note.observe(owner, observer);
    }

    static class SaveState<Y extends DatabaseStorable>{
        public boolean save = false;
        public Y data;
        public SaveState(Y data){
            this.data = data;
        }
    }


}
