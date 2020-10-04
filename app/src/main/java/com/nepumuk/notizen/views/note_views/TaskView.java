package com.nepumuk.notizen.views.note_views;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.tasks.Task;

/**
 * Created by Felix on 06.10.2018.
 */

public class TaskView extends NoteDisplayView<Task>{

    public TaskView(Context context) {
        super(context, R.layout.task_view);
    }

    @Override
    public void onExpand() {
    }

    @Override
    public void onShrink() {

    }
}
