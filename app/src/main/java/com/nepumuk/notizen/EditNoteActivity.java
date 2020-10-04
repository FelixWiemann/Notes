package com.nepumuk.notizen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.objects.UnpackingDataException;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
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
        // TODO move buttons to action bar
        Button btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(v -> save());
        Button discardExit = findViewById(R.id.bt_discard_exit);
        discardExit.setOnClickListener(view -> discardAndExit());

        // setup fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NoteDisplayFragment<StorageObject> headerFragment = new NoteDisplayHeaderFragment();
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
        mViewModel = new ViewModelProvider(this).get(EditNoteViewModel.class);
        mViewModel.setNote(data);
        mViewModel.observe(this, o -> wasChanged = true);
    }

    /**
     * saves the data and exits the activity to the caller
     * setting the result of the currently stored data in the view model
     */
    private void save(){
        Intent result = StorableFactory.addToIntent(new Intent(),mViewModel.getValue());
        setResult(AppCompatActivity.RESULT_OK, result);
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
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof RequiresFabFragment) {
            ((RequiresFabFragment) fragment).registerFabProvider(this);
        }
    }

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
