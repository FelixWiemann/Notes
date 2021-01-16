package com.nepumuk.notizen.core.views.fragments;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;

/**
 * <p>View model containing an instance of a note of type <code>T</code> extending  {@link  DatabaseStorable}</p>
 *
 * </p><p>the note is being held in a {@link MutableLiveData}</p>
 *
 * @param <T> Type of the {@link  DatabaseStorable} to be hold
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
     * utility function to wrap {@link MutableLiveData#getValue()}
     * @return value of MutableLiveData
     */
    public T getValue(){
        return note.getValue().data;
    }

    public boolean isValueSet(){
        return  note.getValue()!=null;
    }

    /**
     * utility function to wrap {@link MutableLiveData#observe}
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
        /**
         * <p>whether the changed data have to be saved in some way</p>
         * <p></p>
         * <p>this should be set to true for any instance ( = {@link Origin#EDITOR}) that changed the value and wants to have it saved</p>
         * <p></p>
         * <p>this should be used for any instance ( = {@link Origin#PARENT}) that called another one ( = {@link Origin#EDITOR}) to change data</p>
         */
        public boolean save = false;
        /**
         * held data
         */
        public Y data;
        /**
         * {@link Origin} of the data
         */
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
             * created by the editor, the instance changing the {@link #data}
             */
            EDITOR,
            /**
             * created by the parent to request changes to {@link #data} before it called the editor
             */
            PARENT
        }
    }


}
