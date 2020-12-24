package com.nepumuk.notizen.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.utils.LayoutHelper;

/**
 * SwipableView wraps the main view that shall be shown to the user.
 * upon swiping to the right or left the proper view is being shown.
 *
 * TODO abstract the swipable view to be used in with other swipe actions as well *
 */
public class SwipableView extends RelativeLayout{

    private static final String LOG_TAG = "SWIPABLEVIEW";
    /**
     * background view on the left
     */
    @Nullable
    private View BackgroundLeft;
    /**
     * background view on the right
     */
    @Nullable
    private View BackgroundRight;
    /**
     * main view to be wrapped
     */
    public View MainView;

    private RelativeLayout Placeholder;

    private boolean fromCompoundAdapter = false;
    private int swipeMenuLeft = -1;
    private int swipeMenuRight = -1;
   // OnTouchListener MiddleClick;

    public SwipableView(Context context) {
        super(context);
        init();
    }

    /**
     * <p>creates a new SwipableView based on given context.
     * </p><p>
     * </p><p>set fromCompoundAdapter to true if it is created from within {@link com.nepumuk.notizen.views.adapters.CompoundAdapter} or shall be used in one
     * </p><p>if either swipeMenuLeft or swipeMenuRight are set to {@link R.layout#swipable_empty}, the menu is ignored
     * @param context
     * @param fromCompoundAdapter
     * @param swipeMenuLeft
     * @param swipeMenuRight
     */
    public SwipableView(Context context, boolean fromCompoundAdapter, @LayoutRes int swipeMenuLeft, @LayoutRes int swipeMenuRight) {
        super(context);
        this.fromCompoundAdapter = fromCompoundAdapter;
        this.swipeMenuLeft = swipeMenuLeft;
        this.swipeMenuRight = swipeMenuRight;
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
        View swipeParent = mInflater.inflate(R.layout.swipe_action_view, this);
        Placeholder = swipeParent.findViewById(R.id.layout_to_be_swiped);
        Placeholder.setClickable(false);
        RelativeLayout parent = swipeParent.findViewById(R.id.parent);

        if (swipeMenuLeft != R.layout.swipable_empty) {
            BackgroundLeft = mInflater.inflate(swipeMenuLeft, parent, false);
            RelativeLayout.LayoutParams paramLeft = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            paramLeft.addRule(ALIGN_PARENT_START, TRUE);
            paramLeft.addRule(CENTER_VERTICAL, TRUE);
            parent.addView(BackgroundLeft, paramLeft);
            BackgroundLeft.post(() -> LayoutHelper.LayoutHelper.updateSize(BackgroundLeft.getWidth(), LayoutHelper.LayoutHelper.getSwipableRightSize()));
        }
        if (swipeMenuRight != R.layout.swipable_empty) {
            BackgroundRight = mInflater.inflate(swipeMenuRight, parent, false);
            RelativeLayout.LayoutParams paramRight = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            paramRight.addRule(ALIGN_PARENT_END, TRUE);
            paramRight.addRule(CENTER_VERTICAL, TRUE);
            parent.addView(BackgroundRight, paramRight);
            BackgroundRight.post(() -> LayoutHelper.LayoutHelper.updateSize(LayoutHelper.LayoutHelper.getSwipableLeftSize(), BackgroundRight.getWidth()));
        }
        // init visibility
        showBackground(0);
    }

    public void setOnClickListeners(OnClickListener left, OnClickListener right, OnTouchListener middle){
        if (BackgroundLeft!=null) {
            BackgroundLeft.setOnClickListener(left);
        }
        if (BackgroundRight!=null) {
            BackgroundRight.setOnClickListener(right);
        }
       // MiddleClick = middle;
       // if (!fromCompoundAdapter && MainView != null) Placeholder.setOnTouchListener(MiddleClick);
    }

    /**
     * set the main view that shall be always shown
     * @param mainView center view that can be swiped to show the background views
     */
    public void setMainView(View mainView){
        Placeholder.removeView(MainView);
        MainView = mainView;
        if (!fromCompoundAdapter) {
           // MainView.setOnTouchListener(MiddleClick);
            mainView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Placeholder.addView(MainView);
        }
    }

    /**
     * if pos is negative -> siwped to the left, right is visible
     * if pos is positive -> swiped to the right, left is visible
     * if pos = 0 -> hide all
     * @param pos based on which visibility is decided
     */
    public void showBackground(float pos){
        if (pos<0 && BackgroundRight != null){
            BackgroundRight.setVisibility(VISIBLE);
            BackgroundRight.setAlpha(pos/BackgroundRight.getWidth());
        }else if(pos>0 && BackgroundLeft != null){
            BackgroundLeft.setVisibility(VISIBLE);
            BackgroundLeft.setAlpha(pos/BackgroundLeft.getWidth());
        }else{
            if (BackgroundRight!= null) {
                BackgroundRight.setAlpha(0);
                BackgroundRight.setVisibility(INVISIBLE);
            }
            if (BackgroundLeft!= null) {
                BackgroundLeft.setAlpha(0);
                BackgroundLeft.setVisibility(INVISIBLE);
            }
        }
    }
}
