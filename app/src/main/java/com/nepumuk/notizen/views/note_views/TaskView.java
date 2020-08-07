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

    private TextView titleView;
    private TextView messageView;
    private CheckBox checkBox;

    public TaskView(Context context) {
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
        titleView = findViewById(R.id.title_view);
        messageView = findViewById(R.id.content_view);
        checkBox = findViewById(R.id.task_view_cb);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TaskView.this.getContent().setDone(b);
            }
        });
    }


    @Override
    public void update(){
        super.update();
        if (isInitialized()) {
            titleView.setText(getContent().getTitle());
            messageView.setText(getContent().getText());
            checkBox.setChecked(getContent().isDone());
        }
    }

}
