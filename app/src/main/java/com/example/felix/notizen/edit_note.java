package com.example.felix.notizen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.StoragePackerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class edit_note extends AppCompatActivity {

    private EditText etTitle;
    private EditText etMessage;
    private Button btSave;

    private DatabaseStorable data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        etTitle = findViewById(R.id.etTitle);
        etMessage = findViewById(R.id.etMessage);
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
        etTitle.setText(data.getType());
        etMessage.setText(data.getDataString());
    }

    private void save(){
        finish();
    }


}
