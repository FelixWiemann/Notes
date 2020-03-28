package com.example.felix.notizen.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.views.adapters.SwipableRecyclerAdapter;

public class NotesRecyclerView extends RecyclerView {

    private static final String TAG = "NOTESRECYCLER";


    public NotesRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public NotesRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NotesRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        ItemTouchHelper.Callback helperCallback = new NotesTouchHelperCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helperCallback);
        itemTouchHelper.attachToRecyclerView(this);
    }

    /**
     * handles the on touch event on the recycler view
     * @param event
     * @return
     */
    private boolean onItemTouch(MotionEvent event){
        View childView = this.findChildViewUnder(event.getX(), event.getY());
        int index = this.getChildAdapterPosition(childView);
        cBaseTask task = (cBaseTask) ((SwipableRecyclerAdapter)getAdapter()).getItem(index);
        // TODO call correct fragment/activity to edit the item that is currently displayed
        //callEditTaskFragment(task);
        // handle the click on the view
        return false;
    }
}
