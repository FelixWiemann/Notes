package com.nepumuk.notizen.views.adapters.view_holders;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.tasks.BaseTask;
import com.nepumuk.notizen.utils.ResourceManger;

/**
 * ViewHolder that holds a view representing a TaskNote
 */
public class TaskViewHolder extends ViewHolderInterface<BaseTask>{

    private final TextView title;
    private final TextView message;
    private final CheckBox done;


    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title_view);
        message = itemView.findViewById(R.id.content_view);
        done = itemView.findViewById(R.id.task_view_cb);
    }

    @Override
    public void bind(final BaseTask toBind){
        // make sure the old one is not updated, if it was bound already...
        done.setOnCheckedChangeListener(null);
        // finally we cna bind all the other stuff
        title.setText(toBind.getTitle());
        title.setContentDescription(ResourceManger.getString(R.string.content_task_title) + " " + toBind.getTitle());
        message.setText(toBind.getText());
        message.setContentDescription(ResourceManger.getString(R.string.content_task_message) + " " + toBind.getTitle() + ": " + toBind.getText());
        // first set the checked status
        done.setChecked(toBind.isDone());
        done.setContentDescription(ResourceManger.getString(R.string.content_task_status) + " " + toBind.getTitle() + ": " + toBind.getStateDescription());
        // before adding the listener to avoid unclear state
        done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toBind.setDone(isChecked);
            }
        });
    }

}
