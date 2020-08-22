package com.nepumuk.notizen.views.fabs;

import android.content.Context;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.nepumuk.notizen.R;

/**
 * hides the added FloatingActionButtons and toggles visibility of them at click on itself
 */
public class FabSpawnerFab extends FloatingActionButton {

    private OnClickListener SuperListener;

    FabToggleManager manager;

    private boolean toggled = true;

    public FabSpawnerFab(Context context) {
        super(context);
        init();
    }

    public FabSpawnerFab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FabSpawnerFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * initializes the on click SuperListener to handle the toggling
     */
    private void init(){
        manager = new FabToggleManager();
        SuperListener = null;
        final OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.toggle();
                toggleImage();
                if (SuperListener != null) SuperListener.onClick(view);
            }
        };
        super.setOnClickListener(listener);
    }

    private void toggleImage(){
        if (toggled) {
            this.setImageResource(R.drawable.ic_cancel);
        }else {
            this.setImageResource(R.drawable.ic_create_new_note);
        }
        toggled = !toggled;
    }

    /**
     * overrides default behaviour of the FAB.
     *
     * If the on click SuperListener would have been overwritten, the functionality of this FAB is gone
     * @param l listener to be called on click
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        SuperListener = l;
    }

    /**
     * overrides default behaviour of the FAB.
     *
     * checks whether an OnClickListener has been set
     */
    @Override
    public boolean hasOnClickListeners() {
        return SuperListener==null;
    }

    public void addFabToSpawn(FloatingActionButton fab) {
        manager.addFabToToggle(fab);
    }

    public void removeFabToSpawn(FloatingActionButton fab){
        manager.removeFabToToggle(fab);
    }
}
