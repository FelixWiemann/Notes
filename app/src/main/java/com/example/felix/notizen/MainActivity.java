package com.example.felix.notizen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.felix.notizen.BackEnd.JsonManager.cJsonManager;
import com.example.felix.notizen.BackEnd.Logger.cNoteLogger;
import com.example.felix.notizen.BackEnd.Logger.cNoteLoggerException;
import com.example.felix.notizen.BackEnd.cBaseException;
import com.example.felix.notizen.BackEnd.cContextManager;
import com.example.felix.notizen.BackEnd.cNoteMaster;
import com.example.felix.notizen.FrontEnd.Notes.cImageNote;
import com.example.felix.notizen.FrontEnd.Notes.cTextNote;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cNoteLogger logger = cNoteLogger.getInstance();
        try {
            // delete logs from previous runs
            String path = this.getApplicationContext().getFilesDir().getPath();
            String[] files = this.getApplicationContext().fileList();
            for (String file:files
                 ) {
                if (file.contains("NOTE_APPLICATION_LOG")) {
                    getApplicationContext().deleteFile(file);
                }
            }
            cContextManager cm = cContextManager.getInstance();
            cm.setUp(this.getApplicationContext());
            // init logger
            logger.init(path, cNoteLogger.mDebugLevelInfo,10);
            cTextNote textNote = new cTextNote(UUID.randomUUID(), "title", "message");
            cImageNote imageNote = new cImageNote(UUID.randomUUID(), "t", "none");
            logger.logInfo(textNote.aTYPE);
            logger.logInfo(imageNote.aTYPE);
            // test reading available JSON
            cNoteMaster noteMaster = cNoteMaster.getInstance();
            noteMaster.addNote(textNote);
            cJsonManager reader = cJsonManager.getInstance();
            reader.init(path+"/JSON_DATA.TXT");
            reader.writeJSON();

            reader.read_JSON();

        }catch (cBaseException ex){
            ex.logException();
        }
        finally {
            try {
                logger.flush();
            } catch (cNoteLoggerException e) {
                e.printStackTrace();
            }
        }


    }

}
