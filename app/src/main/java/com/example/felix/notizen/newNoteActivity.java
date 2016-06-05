package com.example.felix.notizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


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

    private void init() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        //createdNote=new Note();
        edtNoteContent = (EditText) findViewById(R.id.edTNoteText);
        edTNoteName = (EditText) findViewById(R.id.edtNoteName);
        cbNoteIsDone = (CheckBox) findViewById(R.id.cBTaskDone);
    }

    @Override
    protected void onPause() {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        createdNote.setNoteText((edtNoteContent.getText().toString()));
        createdNote.setNoteName((edTNoteName.getText().toString()));
        // Prepare data intent
        createdNote.updateDB(new SQLManagerContract(this));
        Intent data = new Intent();
        data.putExtra(INTENT_NEW_NOTE_ACTIVITY_PARCEL, createdNote);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
        super.onPause();
    }

    @Override
    public void finish() {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        createdNote.setNoteText((edtNoteContent.getText().toString()));
        createdNote.setNoteName((edTNoteName.getText().toString()));
        // Prepare data intent
        Intent data = new Intent();
        data.putExtra(INTENT_NEW_NOTE_ACTIVITY_PARCEL, createdNote);
        data.putExtra(Notizen_uebersicht.EDIT_NOTE_ADAPTER_POSITION, AdapterPositionNote);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        super.finish();
    }


    public void save(View view) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        finish();
    }
}
