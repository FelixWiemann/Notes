package com.nepumuk.notizen.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nepumuk.notizen.utils.db_access.DatabaseStorable;

/**
 * abstract fragment implementation that shall be used as base fragment for all fragments
 * displaying any kind of notes
 *
 * extends Fragment and holds a taskViewModel of a single note of the displayed note type.
 * the view model is of type EditNoteViewModel<T> where T is the same as in NoteDisplayFragment.
 * @param <T> extending DatabaseStorable representing the type of the displayfragment
 */
public abstract class NoteDisplayFragment<T extends DatabaseStorable> extends Fragment {

    /**
     * view model containing the data displayed in the fragment
     * The viewModel type is the same as the NoteDisplayFragment implementation using it
     */
    protected EditNoteViewModel<T> mViewModel;

    /**
     * is set to true before calling unUpdateData and set to false afterwards again.
     * this can be used to identify changes are not done by the user but rather by the app and
     * therefore don't need to trigger onChangeListeners
     * If you would trigger the change listeners we could get into a recursive loop with
     * updating UI-> triggering on change -> updating view model -> updating UI -> triggering on change ...
     */
    protected boolean updatingUI =false;

    /**
     * override to provide custom layout resource and view group container for the fragment
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public abstract View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState);

    /**
     * creates a fragment view based on the given info
     *
     * @param inflater to be used for inflation
     * @param container the fragment will be placed in
     * @param LayoutId layout resource ID that shall be used for inflation
     * @return created fragment view
     */
    protected View createView(LayoutInflater inflater, ViewGroup container, int LayoutId){
        return inflater.inflate(LayoutId, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the view model of the parent activity
        mViewModel = new ViewModelProvider(requireActivity()).get(EditNoteViewModel.class);
        // let this observe the view model
        mViewModel.observe(this, this::updateData);
    }

    protected void updateData(EditNoteViewModel.SaveState<T> updatedData){
        // since we are updating based on the changed data, set the state
        updatingUI = true;
        updateUI(updatedData.data);
        // and reset after update
        updatingUI = false;
    }

    /**
     * will be called after the activity has been created and the fragment has been added.
     * now initialization or updatingUI data can happen
     */
    protected abstract void updateUI(T updatedData);

}
