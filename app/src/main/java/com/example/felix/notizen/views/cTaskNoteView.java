package com.example.felix.notizen.views;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.objects.filtersort.FilterHideDone;
import com.example.felix.notizen.views.adapters.SortableRecyclerAdapter;

/**
 * Created by Felix on 11.11.2018.
 */

public class cTaskNoteView extends cNoteDisplayView<cTaskNote> {

    RecyclerView noteViewContainer;

    SortableRecyclerAdapter<cBaseTask> adapter;

    NestedScrollView view;

    public cTaskNoteView(Context context) {
        super(context,R.layout.task_note_view);
    }

    @Override
    public void onExpand() {
        // TODO margins are overwritten, fix
        noteViewContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onShrink() {
    }

    @Override
    public void update(){
        super.update();
        if (isInitialized()) {
            updateData();
        }
    }

    /**
     * gets called, after the super is initialized.
     * out your code to initialize the child view in here!
     */
    @Override
    public void onInitialization() {
        noteViewContainer = findViewById(R.id.lvContentHolder);
        view = findViewById(R.id.NestedScrollView);
        view.setNestedScrollingEnabled(false);
        adapter = new SortableRecyclerAdapter<>(getContent().getTaskList());
        adapter.filter(new FilterHideDone());
        noteViewContainer.setAdapter(adapter);
        noteViewContainer.setLayoutManager(new LinearLayoutManager(getContext()));

        noteViewContainer.setNestedScrollingEnabled(false);
        updateData();
    }

    /**
     * first clears all views and then adds all tasks of the content
     */
    private void updateData(){
        adapter.replace(getContent().getTaskList());
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount()>1) {
            requestNewLayout(adapter.getItemCount() * 120 + 75);
        }
    }
}
