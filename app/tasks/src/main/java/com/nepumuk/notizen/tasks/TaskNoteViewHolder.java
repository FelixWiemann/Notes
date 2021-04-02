package com.nepumuk.notizen.tasks;

import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nepumuk.notizen.core.utils.ResourceManger;
import com.nepumuk.notizen.core.views.NestedRecyclerView;
import com.nepumuk.notizen.core.views.adapters.SortableRecyclerAdapter;
import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderInterface;
import com.nepumuk.notizen.tasks.filtersort.FilterHideDone;
import com.nepumuk.notizen.tasks.objects.BaseTask;
import com.nepumuk.notizen.tasks.objects.TaskNote;

public class TaskNoteViewHolder extends ViewHolderInterface<TaskNote> {

    private final NestedRecyclerView taskRecyclerView;

    @Keep
    public TaskNoteViewHolder(@NonNull View itemView) {
        super(itemView);
        taskRecyclerView = itemView.findViewById(R.id.lvContentHolder);
    }

    @Override
    public void bind(TaskNote toBind) {
        SortableRecyclerAdapter<BaseTask> adapter = (SortableRecyclerAdapter<BaseTask>) taskRecyclerView.getAdapter();
        if (adapter == null) {
            adapter = new SortableRecyclerAdapter<>(toBind.getTaskList(), 0);
            taskRecyclerView.setAdapter(adapter);
            adapter.filter(new FilterHideDone());
            taskRecyclerView.setLayoutManager(new LinearLayoutManager(taskRecyclerView.getContext()));
        }
        adapter.replace(toBind.getTaskList());
        adapter.notifyDataSetChanged();
        taskRecyclerView.setContentDescription(ResourceManger.getString(R.string.content_note_tasks) + " " + toBind.getTitle());
    }
}
