package com.nepumuk.notizen.views.adapters.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.filtersort.FilterHideDone;
import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.tasks.BaseTask;
import com.nepumuk.notizen.utils.ResourceManger;
import com.nepumuk.notizen.views.adapters.SortableRecyclerAdapter;

public class TaskNoteViewHolder extends ViewHolderInterface<TaskNote> {

    private RecyclerView task;


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
