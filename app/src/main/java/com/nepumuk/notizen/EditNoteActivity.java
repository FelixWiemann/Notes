package com.nepumuk.notizen;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.objects.UnpackingDataException;
import com.nepumuk.notizen.views.fragments.EditNoteViewModel;
import com.nepumuk.notizen.views.fragments.FabProvider;
import com.nepumuk.notizen.views.fragments.NoteDisplayFragment;
import com.nepumuk.notizen.views.fragments.NoteDisplayFragmentFactory;
import com.nepumuk.notizen.views.fragments.NoteDisplayHeaderFragment;
import com.nepumuk.notizen.views.fragments.RequiresFabFragment;
import com.nepumuk.notizen.views.fragments.SaveDataFragment;
import com.nepumuk.notizen.views.fragments.SaveDataFragmentListener;

import java.util.UUID;

public class EditNoteActivity extends AppCompatActivity implements SaveDataFragmentListener, FabProvider {

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
    private EditNoteViewModel<DatabaseStorable> mViewModel;

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
        fragmentTransaction.add(R.id.fragmentHeader, headerFragment);
        NoteDisplayFragment fragmentContent = NoteDisplayFragmentFactory.generateFragment(mViewModel.getValue());
        fragmentTransaction.add(R.id.fragmentHolder, fragmentContent);
        fragmentTransaction.commit();
        if (fabToBeProvided == null){
            fabToBeProvided = findViewById(R.id.provided_fab);
            fabToBeProvided.hide();
        }
    }

    /**
     * loads the data from the intent and initializes the view model
     */
    private void loadData(){
        Intent intent = getIntent();
        DatabaseStorable data = null;
        try {
            data = StorableFactory.storableFromIntent(intent);
        } catch (UnpackingDataException e) {
            // TODO handle packing error
            Log.e(LOG_TAG, "loadData: ", e);
        }
        if (data == null){
            data = new TextNote(UUID.randomUUID(),"" , "");
        }
        originalData = data.getDataString();
        mViewModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        mViewModel.setNote(data);
        mViewModel.observe(this, new Observer<DatabaseStorable>() {
            @Override
            public void onChanged(@Nullable DatabaseStorable o) {
                wasChanged = true;
            }
        });
    }

    /**
     * saves the data and exits the activity to the caller
     * setting the result of the currently stored data in the view model
     */
    private void save(){
        Intent result = StorableFactory.addToIntent(new Intent(),mViewModel.getValue());
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
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof RequiresFabFragment) {
            ((RequiresFabFragment) fragment).registerFabProvider(this);
        }
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

    FloatingActionButton fabToBeProvided;

    @Override
    public FloatingActionButton getFab() {
        return fabToBeProvided;
    }

}