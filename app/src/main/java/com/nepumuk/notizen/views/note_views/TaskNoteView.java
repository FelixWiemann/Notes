package com.nepumuk.notizen.views.note_views;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.views.NestedRecyclerView;
import com.nepumuk.notizen.objects.notes.TaskNote;

/**
 * Created by Felix on 11.11.2018.
 */

public class TaskNoteView extends NoteDisplayView<TaskNote> {

    NestedRecyclerView noteViewContainer;

    public TaskNoteView(Context context) {
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

}
