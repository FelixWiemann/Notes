package com.example.felix.notizen;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.views.fragments.EditNoteViewModel;
import com.example.felix.notizen.views.fragments.NoteDisplayFragment;
import com.example.felix.notizen.views.fragments.NoteDisplayFragmentFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class EditNoteActivity extends AppCompatActivity {

    private EditText etTitle;
    private Button btSave;

    private DatabaseStorable data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        etTitle = findViewById(R.id.etTitle);
        btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        Intent intent = getIntent();
        try {
            data = StoragePackerFactory.storableFromIntent(intent);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | IOException | InvocationTargetException | ClassNotFoundException e) {
            // TODO log error
            e.printStackTrace();
        }
        if (data == null){
            data = new cTextNote(UUID.randomUUID(),"new note", "new message");
        }
        EditNoteViewModel mViewModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        mViewModel.setNote(data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NoteDisplayFragment fragment = NoteDisplayFragmentFactory.generateFragment(data);
        fragmentTransaction.add(R.id.fragementHolder, fragment);
        fragmentTransaction.commit();

        etTitle.setText(data.getType());
    }

    private void save(){
        Intent result = StoragePackerFactory.addToIntent(new Intent(),data);
        setResult(Activity.RESULT_OK, result);
        finish();
    }


}
