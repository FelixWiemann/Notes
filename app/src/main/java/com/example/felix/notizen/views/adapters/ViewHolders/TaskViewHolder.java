package com.example.felix.notizen.views.adapters.ViewHolders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Task.cBaseTask;

public class TaskViewHolder extends ViewHolderInterface<cBaseTask>{

    TextView title;
    TextView message;
    CheckBox done;


    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title_view);
        message = itemView.findViewById(R.id.content_view);
        done = itemView.findViewById(R.id.task_view_cb);
    }

    @Override
    public void bind(final cBaseTask toBind){
        title.setText(toBind.getTitle());
        message.setText(toBind.getText());
        done.setChecked(toBind.isDone());
        done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toBind.setDone(isChecked);
            }
        });
    }

}
