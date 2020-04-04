package com.example.felix.notizen.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
    public View BackgroundLeft;
    /**
     * background view on the right
     */
    public View BackgroundRight;
    /**
     * main view to be wrapped
     */
    public View MainView;

    private RelativeLayout Placeholder;

    private boolean inRecycler = false;
    OnTouchListener MiddleClick;

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
        LayoutInflater mInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.swipe_action_view, this);
        Placeholder = findViewById(R.id.layout_to_be_swiped);
        RelativeLayout parent = findViewById(R.id.parent);
        BackgroundLeft = mInflater.inflate(R.layout.swipable_left, parent, false);
        RelativeLayout.LayoutParams paramLeft = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramLeft.addRule(ALIGN_PARENT_START, TRUE);
        paramLeft.addRule(CENTER_VERTICAL, TRUE);
        parent.addView(BackgroundLeft, paramLeft);
        BackgroundLeft.setVisibility(INVISIBLE);
        RelativeLayout.LayoutParams paramRight = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramRight.addRule(ALIGN_PARENT_END, TRUE);
        paramRight.addRule(CENTER_VERTICAL, TRUE);
        BackgroundRight = mInflater.inflate(R.layout.swipable_right, parent, false);
        parent.addView(BackgroundRight, paramRight);
        BackgroundRight.setVisibility(INVISIBLE);
    }

    public void setOnClickListeners(OnClickListener left, OnClickListener right, OnTouchListener middle){
        BackgroundLeft.setOnClickListener(left);
        BackgroundRight.setOnClickListener(right);
        MiddleClick = middle;
        if (inRecycler && MainView != null) MainView.setOnTouchListener(MiddleClick);
    }

    /**
     * set the main view that shall be always shown
     * @param mainView center view that can be swiped to show the background views
     */
    public void setMainView(View mainView){
        Placeholder.removeView(MainView);
        MainView = mainView;
        if (inRecycler) MainView.setOnTouchListener(MiddleClick);
        mainView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Placeholder.addView(MainView);
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
    public void showBackground(float pos){
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
