package com.nepumuk.notizen.core.views;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.nepumuk.notizen.core.utils.LayoutHelper;
import com.nepumuk.notizen.core.views.adapters.view_holders.CompoundViewHolder;
import com.nepumuk.notizen.core.views.adapters.view_holders.SwipableViewHolder;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;
import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;
import static androidx.recyclerview.widget.ItemTouchHelper.RIGHT;
import static com.nepumuk.notizen.core.views.SwipeHelperCallback.BUTTON_STATE.GONE;

/**
 * following tutorial https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28#ed30
 *
 * Code example: https://github.com/FanFataL/swipe-controller-demo/blob/master/app/src/main/java/pl/fanfatal/swipecontrollerdemo/SwipeController.java
 *
 *
 */
public class SwipeHelperCallback extends ItemTouchHelper.Callback {

    private static final String LOG_TAG = "NOTES_TOUCH_HELPER";

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
     * buttons width
     */
    private int LEFT_BUTTONS_WIDTH;
    /**
     * buttons width
     */
    private int RIGHT_BUTTONS_WIDTH;

    /**
     * no buttons added
     */
    public final static int NO_BUTTON = -1;

    private boolean swipeBack = false;

    private Canvas currentCanvas;
    private RecyclerView.ViewHolder currentViewHolder;
    private float currentY;
    private RecyclerView currentRecyclerView;
    private boolean isResettable;


    public SwipeHelperCallback(int LEFT_BUTTONS_WIDTH, int RIGHT_BUTTONS_WIDTH) {
        super();

        setButtonWidths(LEFT_BUTTONS_WIDTH,RIGHT_BUTTONS_WIDTH);
        LayoutHelper.LayoutHelper.registerSwipableChangeListener(this::setButtonWidths);
    }

    public void setButtonWidths(int LEFT_BUTTONS_WIDTH, int RIGHT_BUTTONS_WIDTH){
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
                for (RecyclerView.ViewHolder holder : getChildToDrawBasedOnType(viewHolder,dX)) {
                    super.onChildDraw(c, recyclerView, holder, dX, dY, actionState, isCurrentlyActive);
                }
            } else {
                // gone, we need the touch listener to undo the swiped state
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        // button state is gone, draw anyways
        if (currentButtonState == GONE) {
            for (RecyclerView.ViewHolder holder : getChildToDrawBasedOnType(viewHolder,dX)) {
                super.onChildDraw(c, recyclerView, holder, dX, dY, actionState, isCurrentlyActive);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(final Canvas c,
                                  final RecyclerView recyclerView,
                                  final RecyclerView.ViewHolder viewHolder,
                                  final float dX, final float dY,
                                  final int actionState, final boolean isCurrentlyActive) {

        // setting the touch listener for detecting swipe boundaries and see whether button needs to be shown
        recyclerView.setOnTouchListener((v, event) -> {
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
                    setTouchUpListener(c, recyclerView, viewHolder, dY, actionState, isCurrentlyActive);
                }
            }
            // activate or deactivate the item touch handler of the recycler view depending on button states
            // if they are visible we need to suppress the recycler on item touch
            ((SwipeRecyclerView) recyclerView).SetState(currentButtonState != GONE);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchUpListener(final Canvas c,
                                    final RecyclerView recyclerView,
                                    final RecyclerView.ViewHolder viewHolder,
                                    final float dY,
                                    final int actionState, final boolean isCurrentlyActive) {

        currentCanvas = c;
        currentViewHolder = viewHolder;
        currentY = dY;
        currentRecyclerView = recyclerView;
        isResettable = true;
        // undo touch handler
        recyclerView.setOnTouchListener((v, event) -> {
            if ((event.getAction() == MotionEvent.ACTION_DOWN)){
                resetSwipeState(actionState,isCurrentlyActive);
            }
            return false;
        });
    }

    public void resetSwipeState( int actionState, boolean isCurrentlyActive){
        if(isResettable) {
            // reset swipe state and button state
            swipeBack = false;
            currentButtonState = GONE;
            // finally reset child draw position to X-Origin (we only where detecting the X-Dir for swiping)
            for (RecyclerView.ViewHolder holder : getChildToDrawBasedOnType(currentViewHolder, 0F)) {
                SwipeHelperCallback.this.onChildDraw(currentCanvas, currentRecyclerView, holder, 0F, currentY, actionState, isCurrentlyActive);
            }
        }
        isResettable = false;
    }

    private RecyclerView.ViewHolder[] getChildToDrawBasedOnType(final RecyclerView.ViewHolder viewHolder,
                                                              final float dX){
        if (viewHolder instanceof SwipableViewHolder) {
            SwipableViewHolder swipableHolder = (SwipableViewHolder) viewHolder;
            swipableHolder.setBackgroundVisibility(dX);
            return new RecyclerView.ViewHolder[]{swipableHolder.viewHolderInterface};
        }
        else if(viewHolder instanceof CompoundViewHolder && ((CompoundViewHolder) viewHolder).getViewHolder(SwipableViewHolder.class)!= null) {
            return ((CompoundViewHolder) viewHolder).getViewHolders(dX);
        }else {
            // draw child on those directions
            return new RecyclerView.ViewHolder[]{viewHolder};
        }
    }
}
