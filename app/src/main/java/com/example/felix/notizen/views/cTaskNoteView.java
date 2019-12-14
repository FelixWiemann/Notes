package com.example.felix.notizen.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Task.cBaseTask;

import java.util.List;

/**
 * Created by Felix on 11.11.2018.
 */

public class cTaskNoteView extends cNoteDisplayView<cTaskNote> {

    RecyclerView noteViewContainer;

    RecyclerAdapter adapter;

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


    private class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView message;
        CheckBox done;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_view);
            message = itemView.findViewById(R.id.content_view);
            done = itemView.findViewById(R.id.task_view_cb);
        }

        void bind(final cBaseTask toBind){
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

    /**
     * gets called, after the super is initialized.
     * out your code to initialize the child view in here!
     */
    @Override
    public void onInitialization() {
        noteViewContainer = findViewById(R.id.lvContentHolder);
        view = findViewById(R.id.NestedScrollView);
        adapter = new RecyclerAdapter(content.getTaskList());
        noteViewContainer.setAdapter(adapter);
        noteViewContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        updateData();

    }

    private class RecyclerAdapter extends RecyclerView.Adapter<NoteViewHolder>{
        List<cBaseTask> taskList;

        RecyclerAdapter(List<cBaseTask> tasklist){
            taskList = tasklist;
        }

        @NonNull
        @Override
        public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View toBeHold = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_view, viewGroup, false);
            return new NoteViewHolder(toBeHold);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteViewHolder viewHolder, int i) {
            viewHolder.bind(taskList.get(i));
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }
    }

    /**
     * first clears all views and then adds all tasks of the content
     */
    private void updateData(){
        adapter.taskList = content.getTaskList();
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount()>1) {
            requestNewLayout(adapter.getItemCount() * 120 + 75);
        }
    }
}
