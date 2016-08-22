package com.example.felix.notizen.activities;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.felix.notizen.R;
import com.example.felix.notizen.cust_views.CameraPreview;

/**
 * TODO documentation
 */
public class act_newPhotoNote extends AppCompatActivity {
    CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraPreview.closeCamera();
                finish();
            }
        });
        cameraPreview = (CameraPreview) findViewById(R.id.cameraPreview);
        cameraPreview.setCamera(Camera.open());
        // cameraPreview = new CameraPreview(this, Camera.open());
        cameraPreview.bringToFront();


    }

    @Override
    public void onPause(){
        super.onPause();
        cameraPreview.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();
        cameraPreview.onResume();
    }

}
