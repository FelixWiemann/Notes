package com.nepumuk.notizen.tasks;

import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nepumuk.notizen.core.views.adapters.SortableRecyclerAdapter;
import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderInterface;
import com.nepumuk.notizen.tasks.filtersort.FilterHideDone;
import com.nepumuk.notizen.tasks.objects.BaseTask;
import com.nepumuk.notizen.tasks.objects.TaskNote;
import com.nepumuk.notizen.core.utils.ResourceManger;

public class TaskNoteViewHolder extends ViewHolderInterface<TaskNote> {

    private final RecyclerView task;


    @Keep
    public TaskNoteViewHolder(@NonNull View itemView) {
        super(itemView);
        task = itemView.findViewById(R.id.lvContentHolder);
    }

    @Override
    public void bind(TaskNote toBind) {
        // TODO bind task list of task note

        SortableRecyclerAdapter<BaseTask> adapter = (SortableRecyclerAdapter<BaseTask>) task.getAdapter();
        if (adapter == null) {
            adapter = new SortableRecyclerAdapter<>(toBind.getTaskList(), 0);
            adapter.filter(new FilterHideDone());
            task.setAdapter(adapter);
            task.setLayoutManager(new LinearLayoutManager(task.getContext()));
        }
        adapter.replace(toBind.getTaskList());
        adapter.notifyDataSetChanged();
        task.setContentDescription(ResourceManger.getString(R.string.content_note_tasks) + " " + toBind.getTitle());
    }
}
