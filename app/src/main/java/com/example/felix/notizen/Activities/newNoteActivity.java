package com.example.felix.notizen.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.felix.notizen.Objects.Note;
import com.example.felix.notizen.R;
import com.example.felix.notizen.SQLManagerContract;


/**
 * Created by Felix "nepumuk" Wiemann on 24.05.2016
 * as part of Notizen
 */
public class newNoteActivity extends AppCompatActivity {

    public static final String INTENT_NEW_NOTE_ACTIVITY_PARCEL = "INTENT_NEW_NOTE_ACTIVITY_PARCEL";
    private static final String LOG_TAG = "newNoteActivity" ;


    private Note createdNote;
    private int AdapterPositionNote;
    private EditText edTNoteName;
    private EditText edtNoteContent;
    private CheckBox cbNoteIsDone;

    public newNoteActivity() {
        super();

        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        setContentView(R.layout.activity_new_note);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Intent i = getIntent();
        init();
        int mode = i.getExtras().getInt(Notizen_uebersicht.strRequestCode);
        if (mode == Notizen_uebersicht.REQUEST_CODE_EDIT_NOTE) {
            AdapterPositionNote = i.getExtras().getInt(Notizen_uebersicht.EDIT_NOTE_ADAPTER_POSITION, -1);
            // TODO update note in Database when coming from notification
            createdNote = i.getExtras().getParcelable(INTENT_NEW_NOTE_ACTIVITY_PARCEL);
            if (createdNote != null) {
                edTNoteName.setText(createdNote.getNoteName());
                edtNoteContent.setText(createdNote.getNoteText());
                cbNoteIsDone.setChecked(createdNote.isTaskDone());
            }

        } else if (mode == Notizen_uebersicht.REQUEST_CODE_NEW_NOTE) {
            createdNote = new Note();
            AdapterPositionNote = -1;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_create_new_note, menu);
        return true;
    }

    private void init() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        //createdNote=new Note();
        edtNoteContent = (EditText) findViewById(R.id.edTNoteText);
        edTNoteName = (EditText) findViewById(R.id.edtNoteName);
        cbNoteIsDone = (CheckBox) findViewById(R.id.cBTaskDone);
    }

    @Override
    public void finish() {

        super.finish();
    }


    public void save(View view) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        finish();
    }

    public void OnToolBarItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbarmenucreatenote_cancel:
                finish();
                break;
            case R.id.toolbarmenucreatenote_save:
                Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
                createdNote.setNoteText((edtNoteContent.getText().toString()), createdNote.getNoteText() == edtNoteContent.getText().toString());
                createdNote.setNoteName((edTNoteName.getText().toString()), createdNote.getNoteName() == edTNoteName.getText().toString());
                // Prepare data intent
                createdNote.updateDB(new SQLManagerContract(this));
                Intent data = new Intent();
                data.putExtra(INTENT_NEW_NOTE_ACTIVITY_PARCEL, createdNote);
                if (AdapterPositionNote != -1) {
                    data.putExtra(Notizen_uebersicht.EDIT_NOTE_ADAPTER_POSITION, AdapterPositionNote);
                }
                // Activity finished ok, return the data
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }
}
