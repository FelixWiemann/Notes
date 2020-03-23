package com.example.felix.notizen.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.felix.notizen.R;

/**
 * SwipableView wraps the main view that shall be shown to the user.
 * upon swiping to the right or left the proper view is being shown.
 *
 * TODO abstract the swipable view to be used in with other swipe actions as well
 *
 * TODO setMainX -> is what is beeing animated; swipable list view ist der böse
 *
 */
public class SwipableView extends RelativeLayout{

    private static final String TAG = "SWIPABLEVIEW";
    /**
     * background view on the left
     */
    private View BackgroundLeft;
    /**
     * background view on the right
     */
    private View BackgroundRight;
    /**
     * main view to be wrapped
     */
    public View MainView;

    private boolean inRecycler = false;

    public SwipableView(Context context) {
        super(context);
        init();
    }

    public SwipableView(Context context, boolean inRecycler) {
        super(context);
        this.inRecycler = inRecycler;
        init();

    }

    public SwipableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SwipableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * initialize the swipable
     */
    public void init () {
        // inflate from resources
        BackgroundLeft = LayoutInflater.from(this.getContext()).inflate(R.layout.swipable_left, null);
        BackgroundRight = inflate(this.getContext(), R.layout.swipable_right, null);
        // set params
        RelativeLayout.LayoutParams params_left = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params_left.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params_left.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        BackgroundLeft.setBackgroundColor(getResources().getColor(R.color.colorDeleteBackground));
        params_left.leftMargin = 10;
        // add the view
        this.addView(BackgroundLeft, params_left);

        RelativeLayout.LayoutParams params_right = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params_right.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params_right.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        params_right.rightMargin = 10;
        this.addView(BackgroundRight, params_right);

        BackgroundRight.setVisibility(INVISIBLE);
        BackgroundLeft.setVisibility(INVISIBLE);

        if (inRecycler) {
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return SwipeDetector.onTouchEvent(event);
                }
            });
        }
    }


    private GestureDetector SwipeDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
        /**
         * Notified when a tap occurs with the down {@link MotionEvent}
         * that triggered it. This will be triggered immediately for
         * every down event. All other events should be preceded by this.
         *
         * @param e The down motion event.
         */
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "ondown");
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

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
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



    public void setOnClickListeners(OnClickListener left, OnClickListener right){
        BackgroundLeft.setOnClickListener(left);
        BackgroundRight.setOnClickListener(right);
    }

    /**
     * set the main view that shall be always shown
     * @param mainView center view that can be swiped to show the background views
     */
    public void setMainView(View mainView){
       MainView = mainView;
       mainView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
       this.removeView(mainView);
       this.addView(mainView);
    }

    /**
     * setter for animation for swiping/moving the main view out of the way
     * @param newX new x position of the main view
     */
    public void setMainX(float newX){
        if (MainView != null){
            MainView.setX(newX);
        }
        // show the background views based on position
        showBackground(newX);
    }

    /**
     * if pos is negative -> siwped to the left, right is visible
     * if pos is positive -> swiped to the right, left is visible
     * if pos = 0 -> hide all
     * @param pos based on which visibility is decided
     */
    private void showBackground(float pos){
        if (pos<0){
            BackgroundRight.setVisibility(VISIBLE);
        }else if(pos>0){
            BackgroundLeft.setVisibility(VISIBLE);
        }else{
            BackgroundRight.setVisibility(INVISIBLE);
            BackgroundLeft.setVisibility(INVISIBLE);
        }
    }
}
