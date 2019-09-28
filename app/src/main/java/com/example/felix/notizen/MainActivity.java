package com.example.felix.notizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.felix.notizen.Settings.cSetting;
import com.example.felix.notizen.Settings.cSettingException;
import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.Utils.DBAccess.cDBDataHandler;
import com.example.felix.notizen.Utils.DBAccess.cDBHelper;
import com.example.felix.notizen.Utils.JsonManager.cJsonManager;
import com.example.felix.notizen.Utils.JsonManager.cJsonManagerException;
import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.Utils.Logger.cNoteLoggerException;
import com.example.felix.notizen.Utils.cContextManager;
import com.example.felix.notizen.Utils.cContextManagerException;
import com.example.felix.notizen.Utils.cNoteMaster;
import com.example.felix.notizen.objects.Notes.cImageNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.objects.cStorageObject;
import com.example.felix.notizen.views.OnLongPressListener;
import com.example.felix.notizen.views.cExpandableViewAdapter;
import com.example.felix.notizen.views.customListView;
import com.example.felix.notizen.views.viewsort.FilterShowAll;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private cNoteLogger log;
    private cNoteMaster noteMaster;
    private cJsonManager jsonManager;
    private cSetting settings;
    private static final String TAG = "MAINACTIVITY";
    private cExpandableViewAdapter adapter;

    /*
     *TODO
     * move data handling to a ViewModel connected to the Database via LiveData
     * live data will probably need a map of data with uuid:note
     * makes it possible to update single entries + update the db accordingly
     *
     */

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
        cDBDataHandler handler = new cDBDataHandler();
        handler.reinitDatabase();
        // settings available, now inflate everything
        setContentView(R.layout.activity_main);
        // init vars
        log = cNoteLogger.getInstance();
        log.initAppl();
        log.logInfo("onCreate");
        jsonManager = cJsonManager.getInstance();
        customListView lv = findViewById(R.id.adapterView);
        lv.init();
        adapter = (cExpandableViewAdapter) lv.getAdapter();
        Log.d(TAG, "list view");
        log.logDebug(new cTextNote(UUID.randomUUID() ,"schidel didudel","note").toJson());
        Log.d(TAG, "logged");
        lv.add(new cTextNote(UUID.randomUUID() ,"text note","note"));
        handler.insert(new cTextNote(UUID.randomUUID() ,"new title","some interesting content"));
        handler.insert(new cImageNote(UUID.randomUUID() ,"title image","aadsasd"));
        List<DatabaseStorable> list = handler.read();
        for (DatabaseStorable storable: list) {
            try{
                lv.add((cStorageObject) storable);
            }catch (NullPointerException np){
                log.logError(np.getMessage());
            }
        }
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
                    Toast.makeText(this, storable.getDataString(),Toast.LENGTH_LONG).show();
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | IOException | InvocationTargetException | ClassNotFoundException e) {
                    Log.e(TAG, "onActivityResult: ", e);
                }
                // TODO handle the data given back in the intent (update data)
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
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
        log.logInfo("onPause");
        // TODO: loop over everything in the Adapter -> save if necessary
        new cDBDataHandler().update(adapter.getAllObjects());

        // TODO: remove for production
        // also make cDBHelper.getInstance() protected!
        File f = new File(cDBHelper.getInstance().getDBPAth());
        Path originalPath = Paths.get(f.getPath());
        log.logDebug("saving Database for debug " + originalPath.toString());
        log.logDebug("to " + this.getApplicationContext().getExternalFilesDir("TEST").getAbsolutePath());
        Path copied = Paths.get(this.getApplicationContext().getExternalFilesDir("TEST").getAbsolutePath() + "/file.db");
        try {
            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
