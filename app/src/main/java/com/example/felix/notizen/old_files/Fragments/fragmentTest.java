package com.example.felix.notizen.old_files.Fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.felix.notizen.R;

public class fragmentTest extends AppCompatActivity {

    private static final String LOG_TAG = "fragmentTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
    }
}
