package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Displayable;

public abstract class cNoteDisplayView extends cAbstractAdditionalView{

    protected Displayable NOTE;

    public cNoteDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public cNoteDisplayView(Context context, AttributeSet attrs, int resourceId) {
        super(context, attrs, resourceId);
    }

    public cNoteDisplayView(Context context, int resourceId) {
        super(context, resourceId);
    }

    public cNoteDisplayView(Context context, AttributeSet attrs, int defStyle, int resourceId) {
        super(context, attrs, defStyle, resourceId);
    }

    public void setNoteToDisplay(Displayable noteToDisplay){
        this.NOTE = noteToDisplay;
    }

    public void postInflate() {
        onInitialization();
    }

    /**
     * onExpand() gets called, when the view is expanded to it's bigger size
     */
    public abstract void onExpand();

    /**
     * onShrink gets called, when the view is shrinked to it's original, smaller size
     */
    public abstract void onShrink();

    /**
     * gets called, after the super is initialized.
     * out your code to initialize the child view in here!
     */
    public abstract void onInitialization();
}
