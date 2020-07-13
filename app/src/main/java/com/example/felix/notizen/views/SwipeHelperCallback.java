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
import static com.example.felix.notizen.views.SwipeHelperCallback.BUTTON_STATE.GONE;

/**
 * following tutorial https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28#ed30
 *
 * Code example: https://github.com/FanFataL/swipe-controller-demo/blob/master/app/src/main/java/pl/fanfatal/swipecontrollerdemo/SwipeController.java
 *
 *
 */
public class SwipeHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "NOTES_TOUCH_HELPER";

    enum BUTTON_STATE {
        GONE, LEFT, RIGHT
    }

    /**
     * state of the buttons
     */
    private BUTTON_STATE currentButtonState = GONE;


    // dragging not supported
    private final static int DRAG_FLAGS = 0;
    /**
     * movement flags depending on buttons added
     */
    private int MOVEMENT_FLAGS;
    /**
     * buttons width set in constructor
     */
    private final int LEFT_BUTTONS_WIDTH;
    /**
     * buttons width set in constructor
     */
    private final int RIGHT_BUTTONS_WIDTH;

    /**
     * no buttons added
     */
    public final static int NO_BUTTON = -1;

    private boolean swipeBack = false;


    public SwipeHelperCallback(int LEFT_BUTTONS_WIDTH, int RIGHT_BUTTONS_WIDTH) {
        super();
        this.LEFT_BUTTONS_WIDTH = LEFT_BUTTONS_WIDTH;
        this.RIGHT_BUTTONS_WIDTH = RIGHT_BUTTONS_WIDTH;
        MOVEMENT_FLAGS = 0;
        if (LEFT_BUTTONS_WIDTH != NO_BUTTON){
            MOVEMENT_FLAGS |= RIGHT; // swiping right means we need to show the left buttons
        }
        if (RIGHT_BUTTONS_WIDTH != NO_BUTTON){
            MOVEMENT_FLAGS |= LEFT;
        }
    }

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
            swipeBack = currentButtonState != GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // when swiping
        if (actionState == ACTION_STATE_SWIPE) {
            // and no buttons gone
            if (currentButtonState != GONE) {
                // determine button state direction
                if (currentButtonState == BUTTON_STATE.LEFT) dX = Math.max(dX, LEFT_BUTTONS_WIDTH);
                if (currentButtonState == BUTTON_STATE.RIGHT) dX = Math.min(dX, -RIGHT_BUTTONS_WIDTH);
                super.onChildDraw(c, recyclerView, getChildToDrawBasedOnType(viewHolder,dX), dX, dY, actionState, isCurrentlyActive);
            } else {
                // gone, we need the touch listener to undo the swiped state
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
        // button state is gone, draw anyways
        if (currentButtonState == GONE) {
            super.onChildDraw(c, recyclerView, getChildToDrawBasedOnType(viewHolder,dX), dX, dY, actionState, isCurrentlyActive);
        }
        Log.d(TAG, "onChildDraw, action state " + actionState);
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
                    if (LEFT_BUTTONS_WIDTH != NO_BUTTON && dX > LEFT_BUTTONS_WIDTH){
                        currentButtonState = BUTTON_STATE.LEFT;
                    }
                    else if (RIGHT_BUTTONS_WIDTH != NO_BUTTON &&  dX < - RIGHT_BUTTONS_WIDTH){
                        currentButtonState = BUTTON_STATE.RIGHT;
                    }
                    // we need to show buttons
                    if (currentButtonState != GONE) {
                        // make sure to be able to undo stuff
                        setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                }
                // activate or deactivate the item touch handler of the recycler view depending on button states
                // if they are visible we need to suppress the recycler on item touch
                ((SwipeRecyclerView) recyclerView).SetState(currentButtonState == GONE);
                Log.d(TAG, "onTouch: setTouchListener, button state gone: " + (currentButtonState == GONE) + ",Motion event: " + event.getAction());
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
                if ((event.getAction() == MotionEvent.ACTION_DOWN)){
                    // reset swipe state and button state
                    swipeBack = false;
                    currentButtonState = GONE;
                    // finally reset child draw position to X-Origin (we only where detecting the X-Dir for swiping)
                    SwipeHelperCallback.this.onChildDraw(c, recyclerView, getChildToDrawBasedOnType(viewHolder,0F), 0F, dY, actionState, isCurrentlyActive);
                }
                Log.d(TAG, "onTouch: setTouchUpListener: Motion event: " + event.getAction());
                return false;
            }
        });
    }

    private RecyclerView.ViewHolder getChildToDrawBasedOnType(final RecyclerView.ViewHolder viewHolder,
                                                              final float dX){
        if (viewHolder instanceof SwipableViewHolder){
            SwipableViewHolder swipableHolder = (SwipableViewHolder) viewHolder;
            swipableHolder.setBackgroundVisibility(dX);
            return swipableHolder.viewHolderInterface;
        }else {
            // draw child on those directions
            //Log.d(TAG, "getChildToDrawBasedOnType: not swipable holder " + viewHolder.getClass().getCanonicalName());
            return viewHolder;
        }
    }
}
