package com.nepumuk.notizen.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nepumuk.notizen.EditNoteActivity;
import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.objects.UnpackingDataException;
import com.nepumuk.notizen.objects.filtersort.FilterShowAll;
import com.nepumuk.notizen.objects.filtersort.SortProvider;
import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.storable_factory.DefaultTextNoteStrategy;
import com.nepumuk.notizen.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.objects.tasks.BaseTask;
import com.nepumuk.notizen.utils.MainViewModel;
import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.views.NoteListViewHeaderView;
import com.nepumuk.notizen.views.SwipableOnItemTouchListener;
import com.nepumuk.notizen.views.SwipeRecyclerView;
import com.nepumuk.notizen.views.adapters.BaseRecyclerAdapter;
import com.nepumuk.notizen.views.adapters.CompoundAdapter;
import com.nepumuk.notizen.views.adapters.SwipableRecyclerAdapter;
import com.nepumuk.notizen.views.adapters.TitleAdapter;
import com.nepumuk.notizen.views.adapters.view_holders.CompoundViewHolder;
import com.nepumuk.notizen.views.fabs.FabSpawnerFab;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends NavHostFragment {

    private static final String TAG = "MainFragment";


    private SwipeRecyclerView recyclerView;
    private MainViewModel model;


    public static final int REQUEST_EDIT_NOTE = 1;
    public int currentEditedNoteIndex = 0;
    private boolean deleteWasClicked = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content =  inflater.inflate(R.layout.activity_main_content, container, false);
        initFragment(content);
        return content;
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /**
     * init the fragment with the created content view
     * @param content already created content view
     */
    private void initFragment(@NonNull View content){
        model = new ViewModelProvider(this).get(MainViewModel.class);
        // setup views
        recyclerView = content.findViewById(R.id.adapterView);
        ArrayList<StorageObject> list = new ArrayList<>();
        final CompoundAdapter<StorageObject> adapter = new CompoundAdapter<>(list, R.layout.compound_view);
        adapter.registerAdapter(new TitleAdapter(list,2),R.id.titleid);
        adapter.registerAdapter(new BaseRecyclerAdapter<>(list,1), R.id.content);
        SwipableRecyclerAdapter<StorageObject> swipeAdapter = new SwipableRecyclerAdapter<>(list,0, true, R.layout.swipable_left, R.layout.swipable_right);
        swipeAdapter.OnLeftClick = (clickedOn, parentView) -> {
            currentEditedNoteIndex = recyclerView.getChildAdapterPosition((View)parentView.getParent().getParent());
            if (currentEditedNoteIndex == RecyclerView.NO_POSITION) return;
            StorageObject task = adapter.getItem(currentEditedNoteIndex);
            model.deleteData(task);
            recyclerView.resetSwipeState();
            deleteWasClicked = true;
        };
        adapter.registerAdapter(swipeAdapter,R.id.compound_content);
        final NoteListViewHeaderView headerView = content.findViewById(R.id.headerView);
        // update list view adapter on changes of the view model
        model.observeForEver(map -> {
            List<StorageObject> list1 = new ArrayList<>();
            try {
                if (map == null) {
                    return;
                }

                // Concurrent Modification Exception happened again, added additional logging
                for (Map.Entry<String, DatabaseStorable> set : map.entrySet()) {
                    list1.add((StorageObject) set.getValue());
                }

                adapter.replace(list1);
                // sort and filter new data based on current settings
                adapter.filter();
                adapter.sort();
                headerView.update(list1.size());
            } catch (ConcurrentModificationException ex){
                Log.e(TAG, "onChanged: got a concurrentmodex", ex);
                Log.e(TAG, "map data: " + map );
                Log.e(TAG, "list data: " + list1);
                Log.e(TAG, "is fetching " + model.isCurrentlyFetchingDataFromDB());
                throw new RuntimeException("fail");
            }
        });
        adapter.filter(new FilterShowAll());
        adapter.sort(SortProvider.SortByType);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new SwipableOnItemTouchListener(recyclerView,(e) -> {
            if (deleteWasClicked){
                deleteWasClicked = false;
                return false;
            }
            View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
            currentEditedNoteIndex = recyclerView.getChildAdapterPosition(childView);
            // no view, do nothing
            if (currentEditedNoteIndex== RecyclerView.NO_POSITION) return false;

            CompoundViewHolder holder = (CompoundViewHolder) recyclerView.getChildViewHolder(childView);
            if (holder.invertWasClicked){
                // supress view click if invert was clicked
                holder.invertWasClicked = false;
                return false;
            }
            StorageObject task = adapter.getItem(currentEditedNoteIndex);
            callEditNoteActivityForResult(recyclerView, task);
            return false;
        }));

        adapter.notifyDataSetChanged();
        final FabSpawnerFab fabSpawner = content.findViewById(R.id.fab_add_notes);
        FloatingActionButton fab =  content.findViewById(R.id.fab_text_note);
        fab.setOnClickListener(view -> {
            // TODO make sure a text note is created
            callEditNoteActivityForResult(view);
            fabSpawner.callOnClick();
        });
        fabSpawner.addFabToSpawn(fab);
        fab = content.findViewById(R.id.fab_task_note);
        fab.setOnClickListener(view -> {
            ArrayList<BaseTask> list12 = new ArrayList<>();
            // TODO use string resources
            TaskNote testNote = new TaskNote(UUID.randomUUID(),"", list12);
            callEditNoteActivityForResult(view, testNote);
            fabSpawner.callOnClick();
        });
        fabSpawner.addFabToSpawn(fab);
        // TODO add fab for new types
        //  e.g. camera
    }

    /**
     * calles the edit note activity with a null-note and thus creating a new one,
     * as defined in the currently set DefaultStorable {@link StorableFactory#getDefaultStorable()}
     */
    private void callEditNoteActivityForResult(View view){
        // create a new default text note and directly edit it.
        callEditNoteActivityForResult(view, new DefaultTextNoteStrategy().createDefault());
    }

    /**
     * calls the edit note activity with the given note for editing purposes
     * @param storable note to be edited
     */
    public void callEditNoteActivityForResult(View v, DatabaseStorable storable) {
        Intent intent = new Intent(getContext(), EditNoteActivity.class);
        intent = StorableFactory.addToIntent(intent, storable);
        startActivityForResult(intent,REQUEST_EDIT_NOTE);
        /*NavHostFragment navHostFragment = (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.main_fragment_placeholder);

        NavController navController = navHostFragment.getNavController();
        NavDirections action = MainFragmentDirections.actionMainFragmentToEditNoteFragment();
        Navigation.findNavController(v).navigate(action);
        NavBackStackEntry entry = navController.getBackStackEntry(R.id.editNoteFragment);
        new ViewModelProvider(entry).get(EditNoteViewModel.class).setNote(storable);*/
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_NOTE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    DatabaseStorable storable = StorableFactory.storableFromIntent(data);
                    model.updateOrCreate(storable);
                } catch (UnpackingDataException e) {
                    Log.e(TAG, "onActivityResult: ", e);
                }
            }
        }
    }

}
