package com.nepumuk.notizen.views.note_views;

import android.content.Context;
import androidx.annotation.CallSuper;
import android.util.AttributeSet;
import android.util.Log;

import com.nepumuk.notizen.utils.OnUpdateCallback;
import com.nepumuk.notizen.objects.StorageObject;

public abstract class NoteDisplayView<T extends StorageObject>
        extends AbstractAdditionalView {

    private static final String LOG_TAG = "NoteDisplayView";

    OnUpdateCallback parentView;

    /**
     * the content that is being displayed in an instance of cNoteDisplayView.
     * The Type depends on the type of note to be displayed
     */
    private T content;

    public NoteDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoteDisplayView(Context context, AttributeSet attrs, int resourceId) {
        super(context, attrs, resourceId);
    }

    public NoteDisplayView(Context context, int resourceId) {
        super(context, resourceId);
    }

    public NoteDisplayView(Context context, AttributeSet attrs, int defStyle, int resourceId) {
        super(context, attrs, defStyle, resourceId);
    }

    public void setNoteToDisplay(T noteToDisplay){
        this.content = noteToDisplay;
    }

    public String getTitle(){
        return content.getTitle();
    }

    public void setParentView(OnUpdateCallback parentView){
        this.parentView = parentView;
    }


    public T getContent(){
        return content;
    }

    /**
     * onExpand() gets called, when the view is expanded to it's bigger size
     */
    public abstract void onExpand();

    /**
     * onShrink gets called, when the view is shrinked to it's original, smaller size
     */
    public abstract void onShrink();
}
