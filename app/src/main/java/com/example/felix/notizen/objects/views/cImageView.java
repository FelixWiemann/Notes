package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.widget.ImageView;

import com.example.felix.notizen.R;

public class cImageView extends cNoteDisplayView {

    public cImageView(Context context) {
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

    /**
     * get the expanded Size based on Note Type or custom implementation
     *
     * @return size that shall be expanded to
     */
    @Override
    public int getExpandedSize() {
        return 500;
    }
}
