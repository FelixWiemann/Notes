package com.example.felix.notizen;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.StoragePackerFactory;
import com.example.felix.notizen.objects.cIdObject;
import com.example.felix.notizen.views.fragments.EditNoteViewModel;
import com.example.felix.notizen.views.fragments.NoteDisplayFragment;
import com.example.felix.notizen.views.fragments.NoteDisplayFragmentFactory;
import com.example.felix.notizen.views.fragments.NoteDisplayHeaderFragment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class EditNoteActivity extends AppCompatActivity {

    private Button btSave;

    private DatabaseStorable data;

    private EditNoteViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
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
            data = new cTextNote(UUID.randomUUID(),"" , "");
        }
        mViewModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        mViewModel.setNote(data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NoteDisplayFragment fragment = new NoteDisplayHeaderFragment();
        fragmentTransaction.add(R.id.fragementHeader, fragment);
        fragment = NoteDisplayFragmentFactory.generateFragment(data);
        fragmentTransaction.add(R.id.fragementHolder, fragment);
        fragmentTransaction.commit();
    }

    private void save(){
        Intent result = StoragePackerFactory.addToIntent(new Intent(),data);
        setResult(Activity.RESULT_OK, result);
        finish();
    }


}
