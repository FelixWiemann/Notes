package com.nepumuk.notizen.tasks;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderInterface;
import com.nepumuk.notizen.tasks.objects.BaseTask;
import com.nepumuk.notizen.core.utils.ResourceManger;

/**
 * ViewHolder that holds a view representing a TaskNote
 */
public class TaskViewHolder extends ViewHolderInterface<BaseTask> {

    private final TextView title;
    private final TextView message;
    private final CheckBox done;

    @Keep
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
        done.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toBind.setDone(isChecked);
            wasClickHandledByChildView = true;
        });
    }

}
