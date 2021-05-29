package com.nepumuk.notizen.tasks;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nepumuk.notizen.core.filtersort.TextFilter;
import com.nepumuk.notizen.core.objects.UnpackingDataException;
import com.nepumuk.notizen.core.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.core.utils.ResourceManger;
import com.nepumuk.notizen.core.views.SwipableOnItemTouchListener;
import com.nepumuk.notizen.core.views.SwipeRecyclerView;
import com.nepumuk.notizen.core.views.adapters.SwipableRecyclerAdapter;
import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderInterface;
import com.nepumuk.notizen.core.views.fragments.EditNoteFragment;
import com.nepumuk.notizen.core.views.fragments.EditNoteViewModel;
import com.nepumuk.notizen.core.views.fragments.FabProvider;
import com.nepumuk.notizen.core.views.fragments.NoteDisplayFragment;
import com.nepumuk.notizen.core.views.fragments.RequiresFabFragment;
import com.nepumuk.notizen.tasks.filtersort.SortProvider;
import com.nepumuk.notizen.tasks.objects.BaseTask;
import com.nepumuk.notizen.tasks.objects.Task;
import com.nepumuk.notizen.tasks.objects.TaskNote;

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

    private boolean wasActionOnView = false;
    private SwipableRecyclerAdapter<BaseTask> adapter;

    public TaskNoteFragment() {
        super();
        currentEditedNoteIndex = INVALID_INDEX;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = createView(inflater,container, R.layout.task_note_display_fragment);
        // TODO move view init out of here, as per recommendation
        taskHolder = v.findViewById(R.id.task_holder);
        adapter = taskHolder.getAdapter();
        adapter.OnLeftClick = (clickedOn, parentView) -> {
            currentEditedNoteIndex = taskHolder.getChildAdapterPosition(parentView);
            if (currentEditedNoteIndex==-1) return;
            wasActionOnView = true;
            BaseTask task = adapter.getItem(currentEditedNoteIndex);
            deleteTask(task);
            taskHolder.resetSwipeState();
        };
        taskHolder.addOnItemTouchListener(new SwipableOnItemTouchListener(taskHolder, (e)-> {
            if (wasActionOnView){
                wasActionOnView = false;
                return false;
            }
            View childView = taskHolder.findChildViewUnder(e.getX(), e.getY());
            currentEditedNoteIndex = taskHolder.getChildAdapterPosition(childView);
            if (currentEditedNoteIndex == -1) return true;
            RecyclerView.ViewHolder holder = taskHolder.getChildViewHolder(childView);
            if (holder instanceof ViewHolderInterface){
                ViewHolderInterface<TaskNote> holderInterface = (ViewHolderInterface<TaskNote>)holder;
                if(holderInterface.wasChildClicked()){
                    holderInterface.resetChildClickedState();
                    return false;
                }
            }
            BaseTask task = adapter.getItem(currentEditedNoteIndex);
            callEditTaskFragment(task);
            return false;
            })
        );
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if ((getParentFragment() instanceof EditNoteFragment))
            ((EditNoteFragment)getParentFragment()).setSearchVisible(true,phrase -> adapter.filter(new TextFilter<>(phrase)), R.string.hint_search_tasks);
        NavController controller = NavHostFragment.findNavController(this);
        // get the view model of the parent activity
        taskViewModel = new ViewModelProvider(controller.getCurrentBackStackEntry()).get(EditNoteViewModel.class);
        // let this observe the view model
        taskViewModel.observe(this, data-> {
            if (data.save) {
                updateTask(data.data);
            }
        });
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
        adapter.sort(SortProvider.SortTasksDone);
        adapter.notifyDataSetChanged();
    }

    /**
     * update the displayed task note with the given task
     * @param updated
     */
    private void updateTask(BaseTask updated){
        if (updated == null) return;
        EditNoteViewModel.SaveState<TaskNote> state = mViewModel.getSaveState();
        if (currentEditedNoteIndex == INVALID_INDEX){
            currentEditedNoteIndex = NOT_YET_INDEXED;
            state.data.addTask(updated);
        }else {
            state.data.updateTask(updated);
        }
        mViewModel.setNote(state);
    }

    private void deleteTask(BaseTask updated){
        if (updated == null) return;
        mViewModel.getSaveState().data.deleteTask(updated);
        mViewModel.update();
    }

    private void callEditTaskFragment(BaseTask taskToEdit){
        EditNoteViewModel.SaveState<BaseTask> saveState = null;
        try {
            saveState = new EditNoteViewModel.SaveState
                    (StorableFactory.createFromData(
                            taskToEdit.getId(),
                            taskToEdit.getType(),
                            taskToEdit.getDataString(),
                            taskToEdit.getVersion()));
            saveState.origin = EditNoteViewModel.SaveState.Origin.PARENT;
            taskViewModel.setNote(saveState);
            Navigation.findNavController(requireView()).navigate(R.id.createTaskDialogFragment);
        } catch (UnpackingDataException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void registerFabProvider(FabProvider provider) {
        fabProvider = provider;
        if (fabProvider.getFab() == null)  return;
        fabProvider.getFab().setImageResource(R.drawable.ic_create_task_note);
        fabProvider.getFab().setContentDescription(ResourceManger.getString(R.string.content_add_task));
        fabProvider.getFab().show();
        fabProvider.getFab().setOnClickListener(v -> {
            currentEditedNoteIndex = INVALID_INDEX;
            callEditTaskFragment(new Task(UUID.randomUUID(),"","",false));
        });
    }
}
