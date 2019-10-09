package com.example.felix.notizen.views;

import android.content.Context;

import com.example.felix.notizen.R;

import com.example.felix.notizen.objects.Notes.cTaskNote;

/**
 * Created by Felix on 06.10.2018.
 */

public class cTaskView extends cNoteDisplayView<cTaskNote>{

    public cTaskView(Context context) {
        super(context, R.layout.task_view);
    }

    @Override
    public void onExpand() {
    }

    @Override
    public void onShrink() {

    }

    /**
     * gets called, after the super is initialized.
     * out your code to initialize the child view in here!
     */
    @Override
    public void onInitialization() {

    }

}
