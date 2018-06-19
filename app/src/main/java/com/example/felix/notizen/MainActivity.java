package com.example.felix.notizen;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.felix.notizen.Utils.JsonManager.cJsonManager;
import com.example.felix.notizen.Utils.JsonManager.cJsonManagerException;
import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.Utils.Logger.cNoteLoggerException;
import com.example.felix.notizen.Utils.cNoteMaster;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.Settings.cSetting;
import com.example.felix.notizen.Settings.cSettingException;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.objects.cIdObject;
import com.example.felix.notizen.objects.views.ExpandableView;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // must init setting-singleton first to make sure cust views have access to settings before
        // them getting inflated
        settings = cSetting.getInstance();
        try {
            settings.init(this.getApplicationContext());
        } catch (cSettingException e) {
            // cannot log, as logger instance not ready yet
            e.printStackTrace();
        }
        // settings available, now inflate everything
        setContentView(R.layout.activity_main);
        // init vars
        log = cNoteLogger.getInstance();
        log.init();
        //String path = this.getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
        //log.init(path, 100,cNoteLogger.mDebugLevelDebug,1,true);
        //String path = this.getApplicationContext().getFilesDir().getPath();
        // init logger with settings from application
        log.logInfo("onCreate");
        jsonManager = cJsonManager.getInstance();
        noteMaster = cNoteMaster.getInstance();
        /*
        ExpandableView ev = (ExpandableView) findViewById(R.id.id1);
        ev.setContent(new cIdObject("title"));
        ev = (ExpandableView) findViewById(R.id.id2);
        ev.setContent(new cTextNote(UUID.randomUUID(),"textnote","content"));
        ev = (ExpandableView) findViewById(R.id.id3);
        ev.setContent(new cTask(UUID.randomUUID(),"task","cont",false));*/
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ExpandableView ex = new ExpandableView(this,new cIdObject("id object"));
        this.addContentView(ex, layoutParams);
        ex = new ExpandableView(this,new cTask(UUID.randomUUID() ,"id object","test",false));
        this.addContentView(ex, layoutParams);


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

                break;
        }

    }

}
