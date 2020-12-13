package com.nepumuk.notizen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nepumuk.notizen.objects.storable_factory.StorableFactory;
import com.nepumuk.notizen.views.fragments.EditNoteFragment;

public class EditNoteActivity extends AppCompatActivity {

    /**
     * TAG of the Activity
     */
    private static final String LOG_TAG = "EDIT_NOTE_ACTIVITY";

    private EditNoteFragment editNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // content view
        setContentView(R.layout.activity_edit_note);
        // init UI components
        // TODO move buttons to action bar
        Button btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(v -> save());
        Button discardExit = findViewById(R.id.bt_discard_exit);
        discardExit.setOnClickListener(view -> discardAndExit());

        // setup fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        editNoteFragment = new EditNoteFragment();
        fragmentTransaction.add(R.id.edit_note_content, editNoteFragment);
        fragmentTransaction.commit();
    }

    /**
     * saves the data and exits the activity to the caller
     * setting the result of the currently stored data in the view model
     */
    private void save(){
        Intent result = StorableFactory.addToIntent(new Intent(),editNoteFragment.getModel().getValue());
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
        if (!editNoteFragment.saveDialogIfChanged()) return;
        super.onBackPressed();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    public void discardAndExit() {
        super.onBackPressed();
    }


}
