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
     * forces an update on the live data stored in this view model
     */
    public void update(){
        // TODO should probably moved to base ViewModel, that needs an observable...
        //   e.g. https://stackoverflow.com/questions/48020377/livedata-update-on-object-field-change
        note.setValue(note.getValue());
    }

    /**
     * replaces the currently stored value with the given one
     * @param note
     */
    public void replace(SaveState<T> note){
        this.note.setValue(note);
    }

    /**
     * sets the given value as the data
     * synchronously if value was null, otherwise async.
     * use {@link EditNoteViewModel#replace(SaveState)} if setting shall be made immediately and cannot wait
     * @param note
     */
    public void setNote(SaveState<T> note) {
        // first time set it, afterwards post it asynchronously
        if (this.note.getValue()== null){
            this.note.setValue(note);
        }else{
            this.note.postValue(note);
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

    /**
     * helper class for keeping track of data state before it is saved
     * @param <Y> type param
     */
    public static class SaveState<Y extends DatabaseStorable>{
        public boolean save = false;
        public Y data;
        public Origin origin = null;
        public SaveState(Y data) {
            super();
            this.data = data;
        }

        /**
         * enum as definition on who is the creator
         */
        public enum  Origin{
            /**
             * was created by an editor
             */
            EDIT,
            /**
             * was created from main
             */
            MAIN
        }
    }


}
