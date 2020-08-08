package com.nepumuk.notizen.views.note_views;

import android.content.Context;
import android.widget.TextView;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.notes.TextNote;

/**
 * Created by Felix on 11.11.2018.
 */

public class NoteView extends NoteDisplayView<TextNote> {

    TextView messageView;

    public NoteView(Context context) {
        super(context,R.layout.note_view);
    }

    @Override
    public void onExpand() {
    }

    @Override
    public void onShrink() {
    }

    @Override
    public void update(){
        super.update();
        if (isInitialized()) {
            messageView.setText(this.getContent().getMessage());
        }
    }

    /**
     * gets called, after the super is initialized.
     * out your code to initialize the child view in here!
     */
    @Override
    public void onInitialization() {
        messageView = findViewById(R.id.note_view_tv);
        messageView.setText(this.getContent().getMessage());
    }

}
