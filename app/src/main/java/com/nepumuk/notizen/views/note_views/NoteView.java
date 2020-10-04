package com.nepumuk.notizen.views.note_views;

import android.content.Context;
import android.widget.TextView;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.notes.TextNote;

/**
 * Created by Felix on 11.11.2018.
 */

public class NoteView extends NoteDisplayView<TextNote> {

    public NoteView(Context context) {
        super(context,R.layout.note_view);
    }

    @Override
    public void onExpand() {
    }

    @Override
    public void onShrink() {
    }
}
