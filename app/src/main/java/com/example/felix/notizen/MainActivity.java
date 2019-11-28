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

import com.example.felix.notizen.Settings.cSetting;
import com.example.felix.notizen.Settings.cSettingException;
import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.Utils.NoteViewModel;
import com.example.felix.notizen.Utils.cContextManager;
import com.example.felix.notizen.Utils.cContextManagerException;
import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.views.OnListItemInPositionClickListener;
import com.example.felix.notizen.views.OnLongPressListener;
import com.example.felix.notizen.views.cExpandableViewAdapter;
import com.example.felix.notizen.views.customListView;
import com.example.felix.notizen.views.viewsort.FilterShowAll;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private cSetting settings;
    private static final String TAG = "MAINACTIVITY";
    private cExpandableViewAdapter adapter;
    private NoteViewModel model;
    private customListView lv;

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
        adapter = (cExpandableViewAdapter)((HeaderViewListAdapter)lv.getAdapter()).getWrappedAdapter();
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
                for (String key: map.keySet()) {
                    list.add(map.get(key));
                }
                adapter.replace(list);
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onChanged: adapter updated");
                lv.update(list.size());
            }
        });
        lv.filter(new FilterShowAll());
        lv.setOnLongPressListener(new OnLongPressListener() {
            @Override
            public void onLongPress(DatabaseStorable databaseStorable) {
                callEditNoteActivityForResult(databaseStorable);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callEditNoteActivityForResult();
            }
        });
        Log.d(TAG, "done creating");
    }

    /**
     * calles the edit note activity with a null-note and thus creating a new one
     */
    private void callEditNoteActivityForResult(){
        callEditNoteActivityForResult(null);
    }

    /**
     * calls the edit note activity with the given note for editing purposes
     * @param storable note to be edited
     */
    private void callEditNoteActivityForResult(DatabaseStorable storable){
        Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
        intent = StoragePackerFactory.addToIntent(intent, storable);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: got result");
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    DatabaseStorable storable = StoragePackerFactory.storableFromIntent(data);
                    model.updateOrCreate(storable);
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
                    Log.e(TAG, "onActivityResult: ", e);
                }
            }
        }
    }
}
