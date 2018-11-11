package com.example.felix.notizen;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.felix.notizen.Utils.JsonManager.cJsonManager;
import com.example.felix.notizen.Utils.JsonManager.cJsonManagerException;
import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.Utils.Logger.cNoteLoggerException;
import com.example.felix.notizen.Utils.cNoteMaster;
import com.example.felix.notizen.objects.Notes.cNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.Settings.cSetting;
import com.example.felix.notizen.Settings.cSettingException;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.objects.cIdObject;
import com.example.felix.notizen.objects.views.ExpandableView;
import com.example.felix.notizen.objects.views.cExpandableViewAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private cNoteLogger log;
    private cNoteMaster noteMaster;
    private cJsonManager jsonManager;
    private cSetting settings;
    private int n = 0;
    private ListView lv;



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
        // settings available, now inflate everything
        setContentView(R.layout.activity_main);
        // init vars
        log = cNoteLogger.getInstance();
        log.init();
        log.logInfo("onCreate");
        jsonManager = cJsonManager.getInstance();
        noteMaster = cNoteMaster.getInstance();
        lv = (ListView) findViewById(R.id.adapterView);

        cExpandableViewAdapter adapter = new cExpandableViewAdapter();
        ExpandableView ex = new ExpandableView(this, new cTextNote(UUID.randomUUID() ,"text note","note") , adapter);
        lv.setAdapter(adapter);
        adapter.add(ex);

        ex = new ExpandableView(this,new cTask(UUID.randomUUID() ,"task","test",false),adapter);
        adapter.add(ex);
        adapter.notifyDataSetChanged();

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
        try {
            jsonManager.writeJSON();
            noteMaster.clear();
        } catch (cJsonManagerException e) {
            e.logException();
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


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                n++;
                String s = String.valueOf(n);
                cTextNote textNote = new cTextNote(UUID.randomUUID(), "title " + s, "message " + s);
                noteMaster.addNote(textNote);
                break;
            case R.id.btn2:
                //lv.requestLayout();
                break;
        }

    }

}
