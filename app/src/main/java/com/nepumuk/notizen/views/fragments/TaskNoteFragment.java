package com.nepumuk.notizen.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.filtersort.SortProvider;
import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.tasks.BaseTask;
import com.nepumuk.notizen.objects.tasks.Task;
import com.nepumuk.notizen.utils.ResourceManger;
import com.nepumuk.notizen.views.SwipableOnItemTouchListener;
import com.nepumuk.notizen.views.SwipableView;
import com.nepumuk.notizen.views.SwipeRecyclerView;
import com.nepumuk.notizen.views.adapters.OnSwipeableClickListener;
import com.nepumuk.notizen.views.adapters.SwipableRecyclerAdapter;

import java.util.UUID;


public class TaskNoteFragment extends NoteDisplayFragment<TaskNote> implements RequiresFabFragment {

    private static final String LOG_TAG = "TaskNoteFragment";
    /**
     * task has been created, but not yet added to the taskNote
     */
    private static final int INVALID_INDEX = -1;
    /**
     * task has been added, but was not called for editing by click on it but by creation
     */
    private static final int NOT_YET_INDEXED = -2;
    SwipeRecyclerView<BaseTask> taskHolder;
    EditNoteViewModel<BaseTask> taskViewModel;

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
        final SwipableRecyclerAdapter<BaseTask> adapter = taskHolder.getAdapter();
        adapter.OnLeftClick = new OnSwipeableClickListener() {
            @Override
            public void onClick(View clickedOn, SwipableView parentView) {
                currentEditedNoteIndex = taskHolder.getChildAdapterPosition(parentView);
                if (currentEditedNoteIndex==-1) return;
                BaseTask task = adapter.getItem(currentEditedNoteIndex);
                deleteTask(task);
                taskHolder.resetSwipeState();
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
                currentEditedNoteIndex = taskHolder.getChildAdapterPosition(taskHolder.findChildViewUnder(e.getX(),e.getY()));
                if (currentEditedNoteIndex==-1) return true;
                BaseTask task = adapter.getItem(currentEditedNoteIndex);
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
    protected void updateUI(TaskNote updatedData) {
        SwipableRecyclerAdapter<BaseTask> adapter = taskHolder.getAdapter();
        adapter.replace(updatedData.getTaskList());
        taskHolder.setAdapter(adapter);
        adapter.sort(SortProvider.SortTasksDone);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get the view model of the parent activity
        taskViewModel = new ViewModelProvider(TaskNoteFragment.this).get(EditNoteViewModel.class);
        // let this observe the view model
        taskViewModel.observe(this, new Observer<BaseTask>() {
            @Override
            public void onChanged(@Nullable BaseTask updatedData) {
                updateTask(updatedData);
            }
        });
    }


    /**
     * update the displayed task note with the given task
     * @param updated
     */
    private void updateTask(BaseTask updated){
        if (updated == null) return;
        TaskNote data = mViewModel.getValue();
        if (currentEditedNoteIndex == INVALID_INDEX){
            currentEditedNoteIndex = NOT_YET_INDEXED;
            data.addTask(updated);
        }else {
            data.updateTask(updated);
        }
        mViewModel.setNote(data);
    }

    private void deleteTask(BaseTask updated){
        if (updated == null) return;
        TaskNote data = mViewModel.getValue();
        data.deleteTask(updated);
        mViewModel.setNote(data);
    }

    private void callEditTaskFragment(BaseTask taskToEdit){
        taskViewModel.setNote(taskToEdit);
        CreateTaskDialogFragment fragment = new CreateTaskDialogFragment();
        fragment.setTargetFragment(this,1337);
        fragment.show(getParentFragmentManager(), "CREATE_TASK");
    }

    @Override
    public void registerFabProvider(FabProvider provider) {
        fabProvider = provider;
        if (fabProvider.getFab() == null)  return;
        fabProvider.getFab().setImageResource(R.drawable.ic_create_task_note);
        fabProvider.getFab().setContentDescription(ResourceManger.getString(R.string.content_add_task));
        fabProvider.getFab().show();
        fabProvider.getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentEditedNoteIndex = INVALID_INDEX;
                callEditTaskFragment(new Task(UUID.randomUUID(),"","",false));
            }
        });
    }
}
