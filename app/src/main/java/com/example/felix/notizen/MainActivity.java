package com.example.felix.notizen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.felix.notizen.BackEnd.JsonManager.cJsonManager;
import com.example.felix.notizen.BackEnd.JsonManager.cJsonManagerException;
import com.example.felix.notizen.BackEnd.Logger.cNoteLogger;
import com.example.felix.notizen.BackEnd.Logger.cNoteLoggerException;
import com.example.felix.notizen.BackEnd.cNoteMaster;
import com.example.felix.notizen.FrontEnd.Notes.cImageNote;
import com.example.felix.notizen.FrontEnd.Notes.cTextNote;
import com.example.felix.notizen.Settings.cSetting;
import com.example.felix.notizen.Settings.cSettingException;

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
        setContentView(R.layout.activity_main);
        settings = cSetting.getInstance();
        try {
            settings.init(this.getApplicationContext());
        } catch (cSettingException e) {
            // cannot log, as logger instance not ready yet
            e.printStackTrace();
        }
        // init vars
        log = cNoteLogger.getInstance();
        //String path = this.getApplicationContext().getFilesDir().getPath();
        // init logger with settings from application
        log.logInfo("onCreate");
        jsonManager = cJsonManager.getInstance();
        noteMaster = cNoteMaster.getInstance();

        cTextNote textNote = new cTextNote(UUID.randomUUID(), "title", "message");
        cImageNote imageNote = new cImageNote(UUID.randomUUID(), "t", "none");
        // test reading available JSON

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
