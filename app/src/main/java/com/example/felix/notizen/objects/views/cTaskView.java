package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Adapter;
import android.widget.CheckBox;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.objects.cIdObject;

/**
 * Created by Felix on 06.10.2018.
 */

public class cTaskView extends cNoteDisplayView{

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

    /**
     * get the expanded Size based on Note Type or custom implementation
     *
     * @return size that shall be expanded to
     */
    @Override
    public int getExpandedSize() {
        return 350;
    }
}
