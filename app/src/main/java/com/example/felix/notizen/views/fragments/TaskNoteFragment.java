package com.example.felix.notizen.views.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.objects.filtersort.SortProvider;
import com.example.felix.notizen.views.SwipableOnItemTouchListener;
import com.example.felix.notizen.views.SwipableView;
import com.example.felix.notizen.views.SwipeRecyclerView;
import com.example.felix.notizen.views.adapters.OnSwipeableClickListener;
import com.example.felix.notizen.views.adapters.SwipableRecyclerAdapter;

import java.util.UUID;


public class TaskNoteFragment extends NoteDisplayFragment<cTaskNote> implements RequiresFabFragment {

    private static final String TAG = "TaskNoteFragment";
    /**
     * task has been created, but not yet added to the taskNote
     */
    private static final int INVALID_INDEX = -1;
    /**
     * task has been added, but was not called for editing by click on it but by creation
     */
    private static final int NOT_YET_INDEXED = -2;
    SwipeRecyclerView<cBaseTask> taskHolder;
    EditNoteViewModel<cBaseTask> taskViewModel;

    FabProvider fabProvider;

    private int currentEditedNoteIndex;

    public TaskNoteFragment() {
        super();
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
        final SwipableRecyclerAdapter<cBaseTask> adapter = taskHolder.getAdapter();
        adapter.OnLeftClick = new OnSwipeableClickListener() {
            @Override
            public void onClick(View clickedOn, SwipableView parentView) {
                currentEditedNoteIndex = taskHolder.getChildAdapterPosition(parentView);
                if (currentEditedNoteIndex==-1) return;
                cBaseTask task = adapter.getItem(currentEditedNoteIndex);
                deleteTask(task);
            }
        };
        taskHolder.addOnItemTouchListener(new SwipableOnItemTouchListener(new View.OnTouchListener() {

            /**
             * Called when a touch event is dispatched to a view. This allows listeners to
             * get a chance to respond before the target view.
             *
             * @param v     The view the touch event has been dispatched to.
             * @param e The MotionEvent object containing full information about
             *              the event.
             * @return True if the listener has consumed the event, false otherwise.
             */
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                Log.d("RecyclerView.SimpleOnItemTouchListener","onTouchEvent");
                currentEditedNoteIndex = taskHolder.getChildAdapterPosition(taskHolder.findChildViewUnder(e.getX(),e.getY()));
                if (currentEditedNoteIndex==-1) return true;
                cBaseTask task = adapter.getItem(currentEditedNoteIndex);
                callEditTaskFragment(task);
                return false;
            }
        }));
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
        SwipableRecyclerAdapter<cBaseTask> adapter = taskHolder.getAdapter();
        adapter.replace(updatedData.getTaskList());
        taskHolder.setAdapter(adapter);
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


    /**
     * update the displayed task note with the given task
     * @param updated
     */
    private void updateTask(cBaseTask updated){
        if (updated == null) return;
        cTaskNote data = mViewModel.getValue();
        if (currentEditedNoteIndex == INVALID_INDEX){
            currentEditedNoteIndex = NOT_YET_INDEXED;
            data.addTask(updated);
        }else {
            data.updateTask(updated);
        }
        mViewModel.setNote(data);
    }

    private void deleteTask(cBaseTask updated){
        if (updated == null) return;
        cTaskNote data = mViewModel.getValue();
        data.deleteTask(updated);
        mViewModel.setNote(data);
    }

    private void callEditTaskFragment(cBaseTask taskToEdit){
        taskViewModel.setNote(taskToEdit);
        CreateTaskDialogFragment fragment = new CreateTaskDialogFragment();
        fragment.setTargetFragment(this,1337);
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
                callEditTaskFragment(new cTask(UUID.randomUUID(),"","",false));
            }
        });
    }
}
