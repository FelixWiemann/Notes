package com.example.felix.notizen.views;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.felix.notizen.views.adapters.ViewHolders.SwipableViewHolder;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

/**
 * following tutorial https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28#ed30
 *
 * Code example: https://github.com/FanFataL/swipe-controller-demo/blob/master/app/src/main/java/pl/fanfatal/swipecontrollerdemo/SwipeController.java
 *
 *
 */
public class NotesTouchHelperCallback extends ItemTouchHelper.Callback {

    enum BUTTON_STATE {
        GONE, LEFT, RIGHT
    }

    private static final String TAG = "NOTES_TOUCH_HELPER";

    private final static int DRAG_FLAGS = 0;
    // TODO make dependant whether buttons on right or left are added
    private final static int MOVEMENT_FLAGS = LEFT | RIGHT;
    private BUTTON_STATE currentButtonState = BUTTON_STATE.GONE;
    // TODO make dependant on size of the buttons added left/right
    private final static int LEFT_BUTTONS_WIDTH = 100;
    private final static int RIGHT_BUTTONS_WIDTH = 100;

    private RecyclerView.ViewHolder currentlySwiped;


    private boolean swipeBack = false;

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(DRAG_FLAGS, MOVEMENT_FLAGS);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        // drag/drop not supported
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {}

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = currentButtonState != BUTTON_STATE.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // when swiping
        if (actionState == ACTION_STATE_SWIPE) {
            // and no buttons gone
            if (currentButtonState != BUTTON_STATE.GONE) {
                // determine button state direction
                if (currentButtonState == BUTTON_STATE.LEFT) dX = Math.max(dX, LEFT_BUTTONS_WIDTH);
                if (currentButtonState == BUTTON_STATE.RIGHT) dX = Math.min(dX, -RIGHT_BUTTONS_WIDTH);
                super.onChildDraw(c, recyclerView, getChildToDrawBasedOnType(viewHolder,dX), dX, dY, actionState, isCurrentlyActive);
            } else {
                // not gone, we need the touch listener to undo the swiped state
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
        // button state is gone, draw anyways
        if (currentButtonState == BUTTON_STATE.GONE) {
            super.onChildDraw(c, recyclerView, getChildToDrawBasedOnType(viewHolder,dX), dX, dY, actionState, isCurrentlyActive);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(final Canvas c,
                                  final RecyclerView recyclerView,
                                  final RecyclerView.ViewHolder viewHolder,
                                  final float dX, final float dY,
                                  final int actionState, final boolean isCurrentlyActive) {

        // setting the touch listener for detecting swipe boundaries and see whether button needs to be shown
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // check whether swipe was cancelled or done
                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if (swipeBack){
                    // check if we are past boundaries and set correct state
                    if (dX > LEFT_BUTTONS_WIDTH){
                        currentButtonState = BUTTON_STATE.LEFT;
                    }
                    else if (dX < - RIGHT_BUTTONS_WIDTH){
                        currentButtonState = BUTTON_STATE.RIGHT;
                    }
                    // we need to show buttons
                    if (currentButtonState != BUTTON_STATE.GONE) {
                        // make sure to be able to undo stuff
                        setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        // and make the items not clickable so we can handle the buttons instead
                        setItemsClickable(recyclerView, false);
                    }
                }

                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchUpListener(final Canvas c,
                                    final RecyclerView recyclerView,
                                    final RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY,
                                    final int actionState, final boolean isCurrentlyActive) {
        // undo touch handler
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // when touching down make default onTouchListener, as we can't remove an existing touch listener
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    // swiping buttons have been handled, items should be clickable again
                    setItemsClickable(recyclerView, true);
                    // reset swipe state and button state
                    swipeBack = false;
                    currentButtonState = BUTTON_STATE.GONE;
                    // finally reset child draw position to X-Origin (we only where detecting the X-Dir for swiping)
                    NotesTouchHelperCallback.this.onChildDraw(c, recyclerView, getChildToDrawBasedOnType(viewHolder,0F), 0F, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    /**
     * helper function to change clickable-state of all children of recyclerView
     * @param recyclerView
     * @param isClickable
     */
    private void setItemsClickable(RecyclerView recyclerView,
                                   boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private RecyclerView.ViewHolder getChildToDrawBasedOnType(final RecyclerView.ViewHolder viewHolder,
                                                              final float dX){
        if (viewHolder instanceof SwipableViewHolder){
            SwipableViewHolder swipableHolder = (SwipableViewHolder) viewHolder;
            swipableHolder.setBackgroundVisibility(dX);
            return swipableHolder.viewHolderInterface;
        }else {
            // draw child on those directions
            Log.d(TAG, "getChildToDrawBasedOnType: not swipable holder " + viewHolder.getClass().getCanonicalName());
            return viewHolder;
        }
    }
}
