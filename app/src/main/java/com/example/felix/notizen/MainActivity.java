package com.example.felix.notizen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.Toast;

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
import com.example.felix.notizen.views.OnListItemInPositionClickListener;
import com.example.felix.notizen.views.OnLongPressListener;
import com.example.felix.notizen.views.SwipableListView;
import com.example.felix.notizen.views.adapters.cSwipableViewAdapter;
import com.example.felix.notizen.views.fabs.FabSpawnerFab;
import com.example.felix.notizen.objects.filtersort.FilterShowAll;
import com.example.felix.notizen.objects.filtersort.SortProvider;

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
    private cSwipableViewAdapter adapter;
    private NoteViewModel model;
    private SwipableListView lv;

    public static final int REQUEST_EDIT_NOTE = 1;

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
        lv = findViewById(R.id.adapterView);
        // since we have a header view, we get a HeaderViewListAdapter from which we need the wrapped adapter
        adapter = (cSwipableViewAdapter)((HeaderViewListAdapter)lv.getAdapter()).getWrappedAdapter();
        adapter.onClickListenerLeft = new OnListItemInPositionClickListener() {
            @Override
            public void onClick(int position) {
                model.deleteData(adapter.getItem(position));
            }
        };
        // update list view adapter on changes of the view model
        model.observeForEver(new Observer<HashMap<String, DatabaseStorable>>() {
            @Override
            public void onChanged(@Nullable HashMap<String, DatabaseStorable> map) {
                List<DatabaseStorable> list = new ArrayList<>();
                try {
                    if (map == null) {
                        return;
                    }

                    // Concurrent Modification Exception happened again, added additional logging
                    for (Map.Entry<String, DatabaseStorable> set : map.entrySet()) {
                        list.add(set.getValue());
                    }

                    adapter.replace(list);
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "onChanged: adapter updated");
                    lv.update(list.size());
                    // sort and filter new data based on current settings
                    adapter.filter();
                    adapter.sort();
                } catch (ConcurrentModificationException ex){
                    Log.e(TAG, "onChanged: got a concurrentmodex", ex);
                    Log.e(TAG, "map data: " + map );
                    Log.e(TAG, "list data: " + list);
                    Log.e(TAG, "is fetching " + model.isCurrentlyFetchingDataFromDB());
                    throw new RuntimeException("fail");
                }
            }
        });
        lv.filter(new FilterShowAll());
        lv.sort(SortProvider.SortByType);
        lv.setOnLongPressListener(new OnLongPressListener() {
            @Override
            public void onLongPress(DatabaseStorable databaseStorable) {
                callEditNoteActivityForResult(databaseStorable);
            }
        });

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
                // TODO create task notes
                ArrayList<cBaseTask> list = new ArrayList<>();
                list.add(new cTask(UUID.randomUUID(),"test task 1","no need to be worried", false));
                list.add(new cTask(UUID.randomUUID(),"test task 2","no need to be worried", false));
                list.add(new cTask(UUID.randomUUID(),"test task 3","no need to be worried", false));
                cTaskNote testNote = new cTaskNote(UUID.randomUUID(),"task note test hardcoded",list);
                model.updateOrCreate(testNote);
                Toast.makeText(MainActivity.this, "create task note",Toast.LENGTH_SHORT).show();
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
