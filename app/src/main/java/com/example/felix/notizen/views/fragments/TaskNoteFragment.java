package com.example.felix.notizen.views.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.views.adapters.SortableRecyclerAdapter;
import com.example.felix.notizen.views.viewsort.SortProvider;


public class TaskNoteFragment extends NoteDisplayFragment<cTaskNote> implements RequiresFabFragment {

    private static final String TAG = "TaskNoteFragment";
    private static final int INVALID_INDEX = -1;
    RecyclerView taskHolder;
    SortableRecyclerAdapter<cBaseTask> adapter;
    FabProvider fabProvider;
    EditNoteViewModel<cBaseTask> taskViewModel;


    private int currentEditedNoteIndex;

    public TaskNoteFragment(){
        currentEditedNoteIndex = INVALID_INDEX;
    }


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
        // TODO
        //  create custom OnItemTouchListener to provide more actions easily to the child view
        //  actions: swipe -> for swipable view
        //  this would also clean up the code significantly
        taskHolder.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            View down;
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View underEvent = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    down = underEvent;
                }
                // TODO Clean up!
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (down == null) {
                        return false;
                    }
                    if (down.equals(underEvent)){
                        onTouchEvent(recyclerView, motionEvent);
                    }
                    down = null;
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child==null) return;
                currentEditedNoteIndex = recyclerView.getChildAdapterPosition(child);
                cBaseTask task = adapter.getItem(currentEditedNoteIndex);
                taskViewModel.setNote(task);
                callEditTaskFragment();
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get the view model of the parent activity
        taskViewModel = ViewModelProviders.of(TaskNoteFragment.this).get(EditNoteViewModel.class);
        // let this observe the view model
        taskViewModel.observe(this, new Observer<cBaseTask>() {
            @Override
            public void onChanged(@Nullable cBaseTask updatedData) {
                updateTask(updatedData);
            }
        });
    }

    private void updateTask(cBaseTask updated){
        if (updated == null) return;
        cTaskNote data = mViewModel.getValue();
        if (currentEditedNoteIndex == INVALID_INDEX){
            data.addTask(updated);
        }else {
            data.updateTask(updated);
        }
        mViewModel.setNote(data);
    }

    private void callEditTaskFragment(){
        CreateTaskDialogFragment fragment = new CreateTaskDialogFragment();
        fragment.show(getFragmentManager(), "CREATE_TASK");
    }

    @Override
    public void registerFabProvider(FabProvider provider) {
        fabProvider = provider;
        if (fabProvider.getFab() == null)  return;
        fabProvider.getFab().setImageResource(R.drawable.ic_create_task_note);
        fabProvider.getFab().show();
        fabProvider.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentEditedNoteIndex = INVALID_INDEX;
                callEditTaskFragment();
            }
        });
    }
}
