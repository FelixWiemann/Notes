package com.example.felix.notizen.views.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.views.adapters.SortableRecyclerAdapter;
import com.example.felix.notizen.views.viewsort.SortProvider;


public class TaskNoteFragment extends NoteDisplayFragment<cTaskNote> {

    private static final String TAG = "TaskNoteFragment";
    RecyclerView taskHolder;
    SortableRecyclerAdapter<cBaseTask> adapter;

    /**
     * override to provide custom layout resource and view group container for the fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = createView(inflater,container, R.layout.task_note_display_fragment);
        taskHolder = v.findViewById(R.id.task_holder);
        taskHolder.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    /**
     * will be called after the activity has been created and the fragment has been added.
     * now initialization or updatingUI data can happen
     *
     * @param updatedData
     */
    @Override
    protected void updateUI(cTaskNote updatedData) {
        if (adapter == null) {
            adapter = new SortableRecyclerAdapter<>(updatedData.getTaskList());
            taskHolder.setAdapter(adapter);
        }else {
            adapter.replace(updatedData.getTaskList());
        }
        adapter.sort(SortProvider.SortTasksDone);
        adapter.notifyDataSetChanged();
        Log.d(TAG, "updateUI: done");
    }
}
