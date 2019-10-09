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
 */
public class SwipableView extends RelativeLayout {

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
    protected View MainView;

    public SwipableView(Context context) {
        super(context);
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
    public void init (){
        // inflate from resources
        BackgroundLeft = LayoutInflater.from(this.getContext()).inflate( R.layout.swipable_left,null);
        BackgroundRight = inflate(this.getContext(), R.layout.swipable_right,null);
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
    }

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
