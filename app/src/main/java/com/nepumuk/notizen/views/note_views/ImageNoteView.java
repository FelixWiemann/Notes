package com.nepumuk.notizen.views.note_views;

import android.content.Context;
import android.widget.ImageView;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.notes.ImageNote;

public class ImageNoteView extends NoteDisplayView<ImageNote> {

    public ImageNoteView(Context context) {
        super(context,R.layout.image_view);
    }

    @Override
    public void onExpand() {
        ((ImageView) findViewById(R.id.image_view_image)).setImageResource(R.drawable.importance1);
    }

    @Override
    public void onShrink() {
        ((ImageView)findViewById(R.id.image_view_image)).setImageResource(R.drawable.importance2);
    }

    /**
     * gets called, after the super is initialized.
     * out your code to initialize the child view in here!
     */
    @Override
    public void onInitialization() {
        onShrink();
    }

}
