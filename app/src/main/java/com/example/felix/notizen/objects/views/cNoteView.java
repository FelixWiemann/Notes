package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.widget.TextView;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Notes.cTextNote;

/**
 * Created by Felix on 11.11.2018.
 */

public class cNoteView extends cNoteDisplayView<cTextNote> {

    TextView messageView;

    public cNoteView(Context context) {
        super(context,R.layout.note_view);
    }

    @Override
    public void onExpand() {
        content.setMessage(content.getMessage()+" updated \n");
    }

    @Override
    public void onShrink() {
        content.setTitle(content.getTitle() + "new title");
    }

    @Override
    public void update(){
        super.update();
        if (isInitialized()) {
            messageView.setText(this.content.getMessage());
        }
    }

    /**
     * gets called, after the super is initialized.
     * out your code to initialize the child view in here!
     */
    @Override
    public void onInitialization() {
        messageView = findViewById(R.id.note_view_tv);
        messageView.setText(this.content.getMessage());
    }

    /**
     * get the expanded Size based on Note Type or custom implementation
     *
     * @return size that shall be expanded to
     */
    @Override
    public int getExpandedSize() {
        return 350;
    }
}
