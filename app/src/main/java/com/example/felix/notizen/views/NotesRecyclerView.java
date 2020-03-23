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
    private GestureDetector gestureDetector;


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
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            /**
             * Notified when a tap occurs with the down {@link MotionEvent}
             * that triggered it. This will be triggered immediately for
             * every down event. All other events should be preceded by this.
             *
             * @param e The down motion event.
             */
            @Override
            public boolean onDown(MotionEvent e) {
                Log.d(TAG, "onDown");
                return false;
            }

            /**
             * The user has performed a down {@link MotionEvent} and not performed
             * a move or up yet. This event is commonly used to provide visual
             * feedback to the user to let them know that their action has been
             * recognized i.e. highlight an element.
             *
             * @param e The down motion event
             */
            @Override
            public void onShowPress(MotionEvent e) {
                Log.d(TAG, "onShowPress");
            }

            /**
             * Notified when a tap occurs with the up {@link MotionEvent}
             * that triggered it.
             *
             * @param e The up motion event that completed the first tap
             * @return true if the event is consumed, else false
             */
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                Log.d(TAG, "onSingleTapUp");
                return false;
            }

            /**
             * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
             * current move {@link MotionEvent}. The distance in x and y is also supplied for
             * convenience.
             *
             * @param e1        The first down motion event that started the scrolling.
             * @param e2        The move motion event that triggered the current onScroll.
             * @param distanceX The distance along the X axis that has been scrolled since the last
             *                  call to onScroll. This is NOT the distance between {@code e1}
             *                  and {@code e2}.
             * @param distanceY The distance along the Y axis that has been scrolled since the last
             *                  call to onScroll. This is NOT the distance between {@code e1}
             *                  and {@code e2}.
             * @return true if the event is consumed, else false
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d(TAG, "onScroll");
                return false;
            }

            /**
             * Notified when a long press occurs with the initial on down {@link MotionEvent}
             * that trigged it.
             *
             * @param e The initial on down motion event that started the longpress.
             */
            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress");
            }

            /**
             * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
             * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
             * the x and y axis in pixels per second.
             *
             * @param e1        The first down motion event that started the fling.
             * @param e2        The move motion event that triggered the current onFling.
             * @param velocityX The velocity of this fling measured in pixels per second
             *                  along the x axis.
             * @param velocityY The velocity of this fling measured in pixels per second
             *                  along the y axis.
             * @return true if the event is consumed, else false
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG, "onFling");
                return false;
            }
        });
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder1) {
                Log.d(TAG, "onMove");
                return false;
            }

            @Override
            public void onSwiped(@NonNull ViewHolder viewHolder, int i) {
                Log.d(TAG, "onSwiped");
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(this);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });


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
        //TODO callEditTaskFragment(task);
        // handle the click on the view
        return false;
    }

    interface OnItemSingleTapListener{
        void onSingleTap(DatabaseStorable storable, MotionEvent event);
    }

}
