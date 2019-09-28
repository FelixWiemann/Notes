package com.example.felix.notizen.views.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

public abstract class NoteDisplayFragment<T extends DatabaseStorable> extends Fragment {

    protected EditNoteViewModel<T> mViewModel;

    /**
     * override to provide custom layout resource and viewgroup container for the fragment
     */
    @Override
    public abstract View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState);

    /**
     * creates a fragment view based on the given info
     * @param inflater
     * @param container
     * @param LayoutId
     * @return
     */
    protected View createView(LayoutInflater inflater, ViewGroup container, int LayoutId){
        return inflater.inflate(LayoutId, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(EditNoteViewModel.class);
        mViewModel.getNote().observe(this, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T updatedData) {
                onUpdateData(updatedData);
            }
        });
    }

    /**
     * will be called after the activity has been created and the fragment has been added.
     * now initialization or updating data can happen
     */
    protected abstract void onUpdateData(T updatedData);

    public void init(DatabaseStorable data){
        Log.d("ASDASD", "init: ");
        //mViewModel.setNote(data);
    }

}
