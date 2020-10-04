package com.nepumuk.notizen.views.note_views;

import android.content.Context;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.notes.ImageNote;

public class ImageNoteView extends NoteDisplayView<ImageNote> {

    public ImageNoteView(Context context) {
        super(context,R.layout.image_view);
    }

    @Override
    public void onExpand() {
    }

    @Override
    public void onShrink() {
    }



}
