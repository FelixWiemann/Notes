package com.nepumuk.notizen.views.note_views;

import android.content.Context;
import androidx.annotation.CallSuper;
import android.util.AttributeSet;

import com.nepumuk.notizen.utils.OnUpdateCallback;
import com.nepumuk.notizen.objects.StorageObject;

public abstract class NoteDisplayView<T extends StorageObject>
        extends AbstractAdditionalView implements OnUpdateCallback {

    OnUpdateCallback parentView;

    /**
     * the content that is being displayed in an instance of cNoteDisplayView.
     * The Type depends on the type of note to be displayed
     */
    private T content;

    /**
     * flag that gets set to true, after everything has been properly initialized
     * this includes a proper layout inflation, setting the content, etc.
     * everything should now be properly set and there should be no fear for null pointers
     */
    private boolean isInitialized = false;

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
        this.content.setOnChangeListener(this);
    }

    public void onPostInflate() {
        onInitialization();
        isInitialized = true;
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
     *
     * @return whether the view is properly initialized
     */
    public boolean isInitialized(){
        return isInitialized;
    }

    /**
     * update the parent view to make sure all the latest info is shown
     */
    @Override
    @CallSuper
    public void update(){
        if (parentView != null) {
            parentView.update();
        }
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
     * put your code to initialize the child view in here!
     * this could e.g. be getting all the proper views from the layout and setting the initial valuess
     */
    public abstract void onInitialization();


    @Override
    public void requestNewLayout(int newExpandedSize) {
        parentView.requestNewLayout(newExpandedSize);
    }

}
