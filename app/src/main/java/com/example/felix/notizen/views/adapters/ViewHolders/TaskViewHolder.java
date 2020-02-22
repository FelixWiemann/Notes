package com.example.felix.notizen.views.adapters.ViewHolders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Task.cBaseTask;

/**
 * ViewHolder that holds a view representing a TaskNote
 */
public class TaskViewHolder extends ViewHolderInterface<cBaseTask>{

    private TextView title;
    private TextView message;
    private CheckBox done;


    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title_view);
        message = itemView.findViewById(R.id.content_view);
        done = itemView.findViewById(R.id.task_view_cb);
    }

    @Override
    public void bind(final cBaseTask toBind){
        // make sure the old one is not updated, if it was bound already...
        done.setOnCheckedChangeListener(null);
        // finally we cna bind all the other stuff
        title.setText(toBind.getTitle());
        message.setText(toBind.getText());
        // first set the checked status
        done.setChecked(toBind.isDone());
        // before adding the listener to avoid unclear state
        done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toBind.setDone(isChecked);
            }
        });
    }

}
