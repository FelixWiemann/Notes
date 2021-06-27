package com.nepumuk.notizen.views.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nepumuk.notizen.R;
import com.nepumuk.notizen.core.filtersort.FilterShowAll;
import com.nepumuk.notizen.core.filtersort.SortProvider;
import com.nepumuk.notizen.core.filtersort.TextFilter;
import com.nepumuk.notizen.core.filtersort.ViewFilter;
import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.objects.UnpackingDataException;
import com.nepumuk.notizen.core.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.core.utils.MainViewModel;
import com.nepumuk.notizen.core.utils.ShortCutHelper;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.core.views.SearchView;
import com.nepumuk.notizen.core.views.SwipableOnItemTouchListener;
import com.nepumuk.notizen.core.views.SwipeRecyclerView;
import com.nepumuk.notizen.core.views.adapters.BaseRecyclerAdapter;
import com.nepumuk.notizen.core.views.adapters.CompoundAdapter;
import com.nepumuk.notizen.core.views.adapters.SwipableRecyclerAdapter;
import com.nepumuk.notizen.core.views.adapters.TitleAdapter;
import com.nepumuk.notizen.core.views.adapters.view_holders.CompoundViewHolder;
import com.nepumuk.notizen.core.views.fragments.EditNoteViewModel;
import com.nepumuk.notizen.tasks.objects.DefaultTaskNoteStrategy;
import com.nepumuk.notizen.textnotes.DefaultTextNoteStrategy;
import com.nepumuk.notizen.views.NoteListViewHeaderView;
import com.nepumuk.notizen.views.fabs.FabSpawnerFab;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";


    private SwipeRecyclerView recyclerView;
    private MainViewModel mainViewModel;
    private EditNoteViewModel<DatabaseStorable> editNoteModel;


    public static final int REQUEST_EDIT_NOTE = 1;
    public int currentEditedNoteIndex = 0;
    private boolean deleteWasClicked = false;
    private CompoundAdapter<StorageObject> adapter ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content =  inflater.inflate(R.layout.activity_main_content, container, false);
        initFragment(content);
        setHasOptionsMenu(true);
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
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        editNoteModel = new ViewModelProvider(requireActivity()).get(EditNoteViewModel.class);
        if (editNoteModel.isValueSet()&&editNoteModel.getSaveState().origin != EditNoteViewModel.SaveState.Origin.PARENT){
            // if created via deeplink
            mainViewModel.updateOrCreate(editNoteModel.getValue());
        }
        // setup views
        recyclerView = content.findViewById(R.id.adapterView);
        ArrayList<StorageObject> list = new ArrayList<>();
        adapter = new CompoundAdapter<>(list, R.layout.compound_view);
        adapter.registerAdapter(new TitleAdapter<>(list,2),R.id.titleid);
        adapter.registerAdapter(new BaseRecyclerAdapter<>(list,1), R.id.content);
        SwipableRecyclerAdapter<StorageObject> swipeAdapter = new SwipableRecyclerAdapter<>(list,0, true, R.layout.swipable_left, R.layout.swipable_empty);
        swipeAdapter.OnLeftClick = (clickedOn, parentView) -> {
            currentEditedNoteIndex = recyclerView.getChildAdapterPosition((View)parentView.getParent().getParent());
            if (currentEditedNoteIndex == RecyclerView.NO_POSITION) return;
            deleteWasClicked = true;
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.confirm_delete_note)//
                    .setNegativeButton(R.string.image_delete,((dialog, which) -> {
                        StorageObject task = adapter.getItem(currentEditedNoteIndex);
                        mainViewModel.deleteData(task);
                        recyclerView.resetSwipeState();
                    }))
                    .setPositiveButton(R.string.action_cancel,((dialog, which) -> {

                    })).create().show();
        };
        adapter.registerAdapter(swipeAdapter,R.id.compound_content);
        final NoteListViewHeaderView headerView = content.findViewById(R.id.headerView);
        // update list view adapter on changes of the view model
        mainViewModel.observeForEver(map -> {
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
                Log.e(TAG, "is fetching " + mainViewModel.isCurrentlyFetchingDataFromDB());
                throw new RuntimeException("fail");
            }
        });
        adapter.filter(new FilterShowAll<>());

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
            callEditNote(task);
            return false;
        }));

        adapter.notifyDataSetChanged();
        final FabSpawnerFab fabSpawner = content.findViewById(R.id.fab_add_notes);
        FloatingActionButton fab =  content.findViewById(R.id.fab_text_note);
        fab.setOnClickListener(view -> {
            callEditNote(DefaultTextNoteStrategy.create());
            // report as per https://developer.android.com/guide/topics/ui/shortcuts/best-practices
            new ShortCutHelper(getContext()).reportUsage(ShortCutHelper.ID_NEW_TEXT_NOTE);
            fabSpawner.callOnClick();
        });
        fabSpawner.addFabToSpawn(fab);
        fab = content.findViewById(R.id.fab_task_note);
        fab.setOnClickListener(view -> {
            // report as per https://developer.android.com/guide/topics/ui/shortcuts/best-practices
            new ShortCutHelper(getContext()).reportUsage(ShortCutHelper.ID_NEW_TASK_NOTE);
            callEditNote(DefaultTaskNoteStrategy.create());
            fabSpawner.callOnClick();
        });
        fabSpawner.addFabToSpawn(fab);
        // TODO add fab for new types
        //  e.g. camera
    }

    /**
     * calls the edit note activity with the given note for editing purposes
     * @param storable note to be edited
     */
    public void callEditNote(DatabaseStorable storable) {

        NavDirections action = MainFragmentDirections.actionMainFragmentToNavEditNote();
        Navigation.findNavController(requireView()).navigate(action);
        try {
            // TODO do I have to go via storable factory?
            //  implement deep clone?
            //  is ref enough?
            EditNoteViewModel.SaveState saveState = new EditNoteViewModel.SaveState(StorableFactory.createFromData(storable.getId(),storable.getType(),storable.getDataString(),storable.getVersion()));
            saveState.origin = EditNoteViewModel.SaveState.Origin.PARENT;
            editNoteModel.replace(saveState);
        } catch (UnpackingDataException e) {
            Log.e(TAG, "callEditNote: ", e);
        }
        editNoteModel.getSaveState().save = false;
        editNoteModel.observe(this,state ->{
            if(state.save){
                mainViewModel.updateOrCreate(state.data);
                state.save = false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_fragment_menu, menu);
        ((SearchView) menu.findItem(R.id.app_bar_search).getActionView()).watcher = phrase -> {
            adapter.filter(new TextFilter<>(phrase));
        };
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(requireView())) || super.onOptionsItemSelected(item);
    }

    public void filter(ViewFilter<StorageObject> filter){
        adapter.filter(filter);
    }
}
