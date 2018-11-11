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

public class cTaskView extends cAbstractAdditionalView implements abstractionInterface{
    private cTask task;

    public cTaskView(Context context) {
        super(context);
    }

    public cTaskView(Context context, cTask object) {
        super(context);
        task = object;
    }

    @Override
    public void initView(AttributeSet attrs, int defStyle) {
        LayoutInflater mInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.task_view, this);
        CheckBox taskviewCheckBox = (CheckBox) findViewById(R.id.task_view_cb);
        taskviewCheckBox.setText(task.getText());
    }

}
