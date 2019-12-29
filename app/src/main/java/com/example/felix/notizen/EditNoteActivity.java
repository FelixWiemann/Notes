package com.example.felix.notizen;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.views.fragments.EditNoteViewModel;
import com.example.felix.notizen.views.fragments.NoteDisplayFragment;
import com.example.felix.notizen.views.fragments.NoteDisplayFragmentFactory;
import com.example.felix.notizen.views.fragments.NoteDisplayHeaderFragment;
import com.example.felix.notizen.views.fragments.SaveDataFragment;
import com.example.felix.notizen.views.fragments.SaveDataFragmentListener;

import java.util.UUID;

public class EditNoteActivity extends AppCompatActivity implements SaveDataFragmentListener {

    /**
     * original data that was sent to this activity,
     * stored as STRING to avoid the cloning thingy
     */
    private String originalData;

    /**
     * TAG of the Activity
     */
    private static final String LOG_TAG = "EDIT_NOTE_ACTIVITY";

    /**
     * view model containing the data displayed in this activity
     */
    private EditNoteViewModel mViewModel;

    /**
     * state if the content has been changed
     */
    private boolean wasChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // content view
        setContentView(R.layout.activity_edit_note);
        // load the data from intent
        loadData();
        // init UI components
        Button btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        // setup fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NoteDisplayFragment headerFragment = new NoteDisplayHeaderFragment();
        fragmentTransaction.add(R.id.fragementHeader, headerFragment);
        NoteDisplayFragment fragmentContent = NoteDisplayFragmentFactory.generateFragment(mViewModel.getValue());
        fragmentTransaction.add(R.id.fragementHolder, fragmentContent);
        fragmentTransaction.commit();
    }

    /**
     * loads the data from the intent and initializes the view model
     */
    private void loadData(){
        Intent intent = getIntent();
        DatabaseStorable data = null;
        try {
            data = StoragePackerFactory.storableFromIntent(intent);
        } catch (ClassNotFoundException e) {
            Log.e(LOG_TAG, "exception unpacking data" ,e);
        }
        if (data == null){
            data = new cTextNote(UUID.randomUUID(),"" , "");
        }
        originalData = data.getDataString();
        mViewModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        mViewModel.setNote(data);
        mViewModel.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                wasChanged = true;
            }
        });
    }

    /**
     * saves the data and exits the activity to the caller
     * setting the result of the currently stored data in the view model
     */
    private void save(){
        Intent result = StoragePackerFactory.addToIntent(new Intent(),mViewModel.getValue());
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    /**
     * overrides default behaviour
     * to suppress the back button if the data displayed have been changed
     *
     * if nothing has changed, super.onBackPressed is called
     */
    @Override
    public void onBackPressed(){
        // if it was changed and the original deviates from the currently stored values
        if (wasChanged && !originalData.equals(mViewModel.getValue().getDataString())){
            // ask for save
            SaveDataFragment fragment = new SaveDataFragment();
            fragment.setListener(this);
            fragment.show(getSupportFragmentManager(),LOG_TAG);
            // and do nothing until user has made his choice
            return;
        }
        super.onBackPressed();
    }


    @Override
    public void discardAndExit() {
        super.onBackPressed();
    }

    @Override
    public void saveAndExit() {
        save();
    }

    @Override
    public void cancelExit() {
        // do nothing, as cancel was called
    }
}
