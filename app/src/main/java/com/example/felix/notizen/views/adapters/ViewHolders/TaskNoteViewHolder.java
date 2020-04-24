package com.example.felix.notizen.views.adapters.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.objects.filtersort.FilterShowAll;
import com.example.felix.notizen.views.adapters.SortableRecyclerAdapter;

public class TaskNoteViewHolder extends ViewHolderInterface<cTaskNote> {

    private RecyclerView task;


    public TaskNoteViewHolder(@NonNull View itemView) {
        super(itemView);
        task = itemView.findViewById(R.id.lvContentHolder);
    }

    @Override
    public void bind(cTaskNote toBind) {
        // TODO bind task list of task note

        SortableRecyclerAdapter<cBaseTask> adapter = (SortableRecyclerAdapter<cBaseTask>) task.getAdapter();
        if (adapter == null) {
            adapter = new SortableRecyclerAdapter<>(toBind.getTaskList());
            adapter.filter(new FilterShowAll());
            task.setAdapter(adapter);
            task.setLayoutManager(new LinearLayoutManager(task.getContext()));

        }
        adapter.replace(toBind.getTaskList());
        adapter.notifyDataSetChanged();

    }
}
