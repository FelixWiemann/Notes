package com.example.felix.notizen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.felix.notizen.Settings.cSetting;
import com.example.felix.notizen.Settings.cSettingException;
import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.Utils.NoteViewModel;
import com.example.felix.notizen.Utils.cContextManager;
import com.example.felix.notizen.Utils.cContextManagerException;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.StorableFactoy.DefaultTextNoteStrategy;
import com.example.felix.notizen.objects.StorableFactoy.StorableFactory;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.objects.UnpackingDataException;
import com.example.felix.notizen.objects.cStorageObject;
import com.example.felix.notizen.objects.filtersort.FilterShowAll;
import com.example.felix.notizen.objects.filtersort.SortProvider;
import com.example.felix.notizen.views.NoteListViewHeaderView;
import com.example.felix.notizen.views.SwipableOnItemTouchListener;
import com.example.felix.notizen.views.SwipableView;
import com.example.felix.notizen.views.adapters.BaseRecyclerAdapter;
import com.example.felix.notizen.views.adapters.CompoundAdapter;
import com.example.felix.notizen.views.adapters.OnSwipeableClickListener;
import com.example.felix.notizen.views.adapters.SwipableRecyclerAdapter;
import com.example.felix.notizen.views.adapters.TitleAdapter;
import com.example.felix.notizen.views.fabs.FabSpawnerFab;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    private cSetting settings;
    private static final String TAG = "MAINACTIVITY";
    private NoteViewModel model;
    private RecyclerView recyclerView;

    public static final int REQUEST_EDIT_NOTE = 1;
    public int currentEditedNoteIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // must init setting-singleton first to make sure cust views have access to settings before
        // them getting inflated
        settings = cSetting.getInstance();
        try {
            settings.init(this.getApplicationContext());
        } catch (cSettingException e) {
            Log.e(TAG, "onCreate: error during settings setup", e);
        }
        try {
            cContextManager.getInstance().setUp(this.getApplicationContext());
        } catch (cContextManagerException e) {
            Log.e(TAG, "onCreate: error during context setup", e);
        }
        model = ViewModelProviders.of(this).get(NoteViewModel.class);
        setContentView(R.layout.activity_main);
        // init vars
        Log.i(TAG, "onCreate");
        // TODO HEADER VIEW
        recyclerView = findViewById(R.id.adapterView);
        ArrayList<cStorageObject> list = new ArrayList<>();
        final CompoundAdapter<cStorageObject> adapter = new CompoundAdapter<>(list, R.layout.compound_view);
        adapter.registerAdapter(new TitleAdapter(list,2),R.id.titleid);
        adapter.registerAdapter(new BaseRecyclerAdapter<>(list,1), R.id.content);
        SwipableRecyclerAdapter<cStorageObject> swipeAdapter = new SwipableRecyclerAdapter<>(list,0, true);
        swipeAdapter.OnLeftClick = new OnSwipeableClickListener() {
            @Override
            public void onClick(View clickedOn, SwipableView parentView) {
                currentEditedNoteIndex = recyclerView.getChildAdapterPosition((View)parentView.getParent().getParent());
                if (currentEditedNoteIndex==-1) return;
                cStorageObject task = adapter.getItem(currentEditedNoteIndex);
                model.deleteData(task);
                Log.d(TAG, "OnLeftClick: " + task);
            }
        };
        adapter.registerAdapter(swipeAdapter,R.id.compound_content);
        final NoteListViewHeaderView headerView = findViewById(R.id.headerView);
        // update list view adapter on changes of the view model
        model.observeForEver(new Observer<HashMap<String, DatabaseStorable>>() {
            @Override
            public void onChanged(@Nullable HashMap<String, DatabaseStorable> map) {
                List<cStorageObject> list = new ArrayList<>();
                try {
                    if (map == null) {
                        return;
                    }

                    // Concurrent Modification Exception happened again, added additional logging
                    for (Map.Entry<String, DatabaseStorable> set : map.entrySet()) {
                        list.add((cStorageObject) set.getValue());
                    }

                    adapter.replace(list);
                    Log.d(TAG, "onChanged: adapter updated");
                    // sort and filter new data based on current settings
                    adapter.filter();
                    adapter.sort();
                    headerView.update(list.size());
                } catch (ConcurrentModificationException ex){
                    Log.e(TAG, "onChanged: got a concurrentmodex", ex);
                    Log.e(TAG, "map data: " + map );
                    Log.e(TAG, "list data: " + list);
                    Log.e(TAG, "is fetching " + model.isCurrentlyFetchingDataFromDB());
                    throw new RuntimeException("fail");
                }
            }
        });
        adapter.filter(new FilterShowAll());
        adapter.sort(SortProvider.SortByType);
        recyclerView.setAdapter(adapter);
        // TODO this item touch helper blocks scrolling of inner recycler views...
        recyclerView.addOnItemTouchListener(new SwipableOnItemTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                //Toast.makeText(getApplicationContext(),"onTouchEvent",Toast.LENGTH_SHORT).show();
                Log.d("RecyclerView.SimpleOnItemTouchListener","onTouchEvent");
                currentEditedNoteIndex = recyclerView.getChildAdapterPosition(recyclerView.findChildViewUnder(e.getX(),e.getY()));
                if (currentEditedNoteIndex==-1) return false;
                cStorageObject task = adapter.getItem(currentEditedNoteIndex);
                callEditNoteActivityForResult(task);
                return false;
            }
        }));

        adapter.notifyDataSetChanged();
        FabSpawnerFab fabSpawner = findViewById(R.id.fab_add_notes);
        FloatingActionButton fab =  findViewById(R.id.fab_text_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO make sure a text note is created
                callEditNoteActivityForResult();
            }
        });
        fabSpawner.addFabToSpawn(fab);
        fab = findViewById(R.id.fab_task_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<cBaseTask> list = new ArrayList<>();
                // TODO use string resources
                list.add(new cTask(UUID.randomUUID(),"Task 1","enter text", false));
                cTaskNote testNote = new cTaskNote(UUID.randomUUID(),"",list);
                callEditNoteActivityForResult(testNote);
            }
        });
        fabSpawner.addFabToSpawn(fab);
        // TODO add fab for new types
        //  e.g. camera

        DatabaseStorable fromIntent = IntentHandler.StorableFromIntent(getIntent());
        if (fromIntent != null) callEditNoteActivityForResult(fromIntent);
        Log.d(TAG, "done creating");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO update view model data on click
        model.getData().getValue().values().forEach(new Consumer<DatabaseStorable>() {
            @Override
            public void accept(DatabaseStorable storable) {
                if (storable instanceof cTaskNote) model.updateData(storable);
            }
        });
    }

    /**
     * calles the edit note activity with a null-note and thus creating a new one,
     * as defined in the currently set DefaultStorable {@link StorableFactory#getDefaultStorable()}
     */
    private void callEditNoteActivityForResult(){
        // create a new default text note and directly edit it.
        callEditNoteActivityForResult(new DefaultTextNoteStrategy().createDefault());
    }

    /**
     * calls the edit note activity with the given note for editing purposes
     * @param storable note to be edited
     */
    private void callEditNoteActivityForResult(DatabaseStorable storable){
        Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
        intent = StorableFactory.addToIntent(intent, storable);
        startActivityForResult(intent,REQUEST_EDIT_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: got result");
        // Check which request we're responding to
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

    private static class IntentHandler{
        static DatabaseStorable StorableFromIntent(Intent intent){
            if (intent == null || intent.getAction()==null) return null;
            switch (intent.getAction()){
                case Intent.ACTION_SEND:
                    return handleMimeType(intent);
                default:
                    // intent doesn't have data
                    return null;
            }
        }

        private static DatabaseStorable handleMimeType(Intent intent){
            final String MIME_TYPE = intent.getType();
            // could not determine Mime-Type
            if (MIME_TYPE == null) return StorableFactory.getDefaultStorable();
            if (MIME_TYPE.startsWith("text/")){
                String text = intent.hasExtra(Intent.EXTRA_TEXT) ? intent.getStringExtra(Intent.EXTRA_TEXT) : "retrieving data failed";
                // empty default title for easier title manipulation
                String title = intent.hasExtra(Intent.EXTRA_TITLE) ? intent.getStringExtra(Intent.EXTRA_TITLE) : "";
                return new cTextNote(UUID.randomUUID(), title, text);
            }
            // could not be handled, return default storable
            return StorableFactory.getDefaultStorable();
        }
    }
}
