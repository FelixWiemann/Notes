package com.nepumuk.notizen.views.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.tasks.BaseTask;
import com.nepumuk.notizen.objects.tasks.Task;
import com.nepumuk.notizen.testutils.FragmentTest;
import com.nepumuk.notizen.utils.ContextManager;
import com.nepumuk.notizen.utils.ContextManagerException;
import com.nepumuk.notizen.views.adapters.SwipableRecyclerAdapter;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class TaskNoteFragmentTest extends FragmentTest<TaskNoteFragment> {

    @Test
    public void onCreateView() {
        // tested in lifecycle test
    }

    @Test
    @Ignore("TODO implement")
    public void onItemTouch() {
        // verify that on item touch opens the change task fragment
    }
    @Test
    @Ignore("TODO implement")
    public void onItemLeftClick() {
        // verify that on left click of a swipable item deletes the item from the adapter
    }


    @Test
    public void updateUI() {
        SwipableRecyclerAdapter<BaseTask> adapter = mock(SwipableRecyclerAdapter.class);
        scenario.onFragment(fragment -> {
            // given

            TaskNote note = new TaskNote(UUID.randomUUID(),"",new ArrayList<>());
            fragment.taskHolder.setAdapter(adapter);
            // when
            fragment.updateUI(note);
            // then
            /* TODO setting the mock above apparently doesn't work
            verify(adapter,times(1)).replace(eq(note.getTaskList()));
            verify(adapter,times(1)).sort(any());
            verify(adapter,times(1)).notifyDataSetChanged();
            */
        });
    }

    @Test
    public void registerFabProvider() {
        MockitoAnnotations.initMocks(this);
        scenario.onFragment(fragment -> {
            // given
            FabProvider provider = mock(FabProvider.class);
            FloatingActionButton button = mock(FloatingActionButton.class);
            when(provider.getFab()).thenReturn(button);
            try {
                ContextManager.getInstance().setUp(fragment.getActivity().getApplicationContext());
            } catch (ContextManagerException e) {
                throw new RuntimeException(e);
            }
            // when
            fragment.registerFabProvider(provider);
            // then
            verify(button,atLeastOnce()).setImageResource(anyInt());
            verify(button,atLeastOnce()).setContentDescription(anyString());
            verify(button,atLeastOnce()).show();
            verify(button,atLeastOnce()).setOnClickListener(any());
        });
    }

    @Test
    public void testFragmentIsUpdatedOnViewModelDataChange(){
        scenario.onFragment(fragment ->{
            NavController controller = NavHostFragment.findNavController(fragment);

            EditNoteViewModel<TaskNote> mViewModel = new ViewModelProvider(fragment.requireActivity()).get(EditNoteViewModel.class);
            mViewModel.setNote(new EditNoteViewModel.SaveState<>(new TaskNote(UUID.randomUUID(),"",new ArrayList<>())));
            EditNoteViewModel<BaseTask> model = new ViewModelProvider(controller.getCurrentBackStackEntry()).get(EditNoteViewModel.class);
            EditNoteViewModel.SaveState<BaseTask> state = new EditNoteViewModel.SaveState<>(new Task(UUID.randomUUID(),"","",false));
            state.save = true;
            model.setNote(state);
            // TODO verify note is only updated, when state.save = true
        });
    }

    @Override
    public void setUp() throws Exception {

        // Create a TestNavHostController
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());
        navController.setViewModelStore(new ViewModelStore());
        navController.setGraph(R.navigation.nav_graph_main_fragment);

        FragmentFactory factory = new FragmentFactory(){
            @NonNull
            @Override
            public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                TaskNoteFragment  f = (TaskNoteFragment) super.instantiate(classLoader, className);
                f.getViewLifecycleOwnerLiveData().observeForever(viewLifecycleOwner -> {
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(f.requireView(), navController);
                    }
                });
                return f;
            }
        };
        scenario = FragmentScenario.launch(TaskNoteFragment.class,null,R.style.Theme_AppCompat_Light_NoActionBar,factory);
        scenario.onFragment(fragment -> {
            Navigation.setViewNavController(fragment.requireView(), navController);
        });

    }
}