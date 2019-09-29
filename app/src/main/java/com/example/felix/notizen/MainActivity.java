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

import com.example.felix.notizen.Settings.cSetting;
import com.example.felix.notizen.Settings.cSettingException;
import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.Utils.JsonManager.cJsonManager;
import com.example.felix.notizen.Utils.JsonManager.cJsonManagerException;
import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.Utils.Logger.cNoteLoggerException;
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

    private cNoteLogger log;
    private cJsonManager jsonManager;
    private cSetting settings;
    private static final String TAG = "MAINACTIVITY";
    private cExpandableViewAdapter adapter;
    private NoteViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // must init setting-singleton first to make sure cust views have access to settings before
        // them getting inflated
        settings = cSetting.getInstance();
        try {
            settings.init(this.getApplicationContext());
        } catch (cSettingException e) {
            e.printStackTrace();
        }
        try {
            cContextManager.getInstance().setUp(this.getApplicationContext());
        } catch (cContextManagerException e) {
            e.printStackTrace();
        }
        model = ViewModelProviders.of(this).get(NoteViewModel.class);

        setContentView(R.layout.activity_main);
        // init vars
        log = cNoteLogger.getInstance();
        log.initAppl();
        log.logInfo("onCreate");
        jsonManager = cJsonManager.getInstance();
        customListView lv = findViewById(R.id.adapterView);
        lv.init();
        adapter = (cExpandableViewAdapter) lv.getAdapter();
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
        log.logInfo("done creating");
        Log.d(TAG, "done creating");
    }

    private void callEditNoteActivityForResult(){
        callEditNoteActivityForResult(null);
    }
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
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | IOException | InvocationTargetException | ClassNotFoundException e) {
                    Log.e(TAG, "onActivityResult: ", e);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        log.logInfo("onStart");
        try {
            jsonManager.read_JSON();
        } catch (cJsonManagerException e) {
            e.logException();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log.logInfo("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log.logInfo("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        log.logInfo("onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log.logInfo("onDestroy");
        try {
            log.flush();
        } catch (cNoteLoggerException e) {
            e.printStackTrace();
        }
    }
}
