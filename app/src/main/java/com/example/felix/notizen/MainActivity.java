package com.example.felix.notizen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.example.felix.notizen.BackEnd.DBAccess.cDBMaster;
import com.example.felix.notizen.BackEnd.Logger.cNoteLogger;
import com.example.felix.notizen.BackEnd.Logger.cNoteLoggerException;
import com.example.felix.notizen.BackEnd.cContextManager;
import com.example.felix.notizen.BackEnd.cContextManagerException;
import com.example.felix.notizen.FrontEnd.Notes.cImageNote;
import com.example.felix.notizen.FrontEnd.Notes.cTextNote;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        String path = this.getApplicationContext().getFilesDir().getPath();
        cNoteLogger logger = cNoteLogger.getInstance();

        cContextManager cm = cContextManager.getInstance();
        try {
            cm.setUp(this.getApplicationContext());
        } catch (cContextManagerException e) {
            e.printStackTrace();
        }
        logger.init(path,5);
        cTextNote textNote= new cTextNote(UUID.randomUUID(),"title","message");
        cImageNote imageNote = new cImageNote(UUID.randomUUID(), "t", "none");
        logger.logInfo(textNote.aTYPE);
        logger.logInfo(imageNote.aTYPE);
        /*cDBMaster master  = cDBMaster.getInstance();
        if (master ==null){
            master = cDBMaster.getInstance();
        }
        master.init();
        master.insert(textNote);
        master.insert(imageNote);
        master.delete(textNote);
        master.delete(imageNote);
*/
        imageNote.deleteNote();
        textNote.deleteNote();
        try {
            logger.flush();
        } catch (cNoteLoggerException e) {
            e.printStackTrace();
        }


    }

}
