package com.nepumuk.notizen.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.nepumuk.notizen.R;

public class ExpandableView extends RelativeLayout {

    /**
     * expansion state of an ExpandableView
     */
    public enum ExpandState{
        /**
         * is expanded, State = 1
         */
        EXPANDED(1),
        /**
         * is shrinked, State = 0
         */
        SHRINKED(0),
        /**
         * is just inflated, hasn't changed the state yet.
         * State = -1
         */
        FIRSTINFLATE(-1);

        /**
         * state of the enum value
         * 1 = expanded
         * 0 = shrinked
         * -1 =  first time inflated
         */
        public final int State;
        ExpandState(int state){
            State = state;
        }
    }

    ExpandableView.ExpandState currentState = ExpandableView.ExpandState.FIRSTINFLATE;

    private final float aSizeUnExpanded;
    private final float aSizeExpanded;

    private Button expandButton;


    public ExpandableView(Context context) {
        this(context, null);
    }

    public ExpandableView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExpandableView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ExpandableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ExpandableView, defStyleAttr, defStyleRes);
        aSizeExpanded = a.getDimension(R.styleable.ExpandableView_sizeExpanded, 200);
        aSizeUnExpanded = a.getDimension(R.styleable.ExpandableView_sizeUnExpanded, 650);
        a.recycle();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) aSizeUnExpanded));
    }

    public void resetExpandState(){
        currentState = ExpandState.FIRSTINFLATE;
        invertShrink();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        expandButton = findViewById(R.id.expand_button);
        expandButton.setOnClickListener(view -> invertShrink());
        invertShrink();
    }

    /**
     * inverts the shrinking state
     */
    public void invertShrink(){
        if (currentState== ExpandableView.ExpandState.FIRSTINFLATE){
            currentState = ExpandableView.ExpandState.EXPANDED;
        }
        if (currentState == ExpandableView.ExpandState.EXPANDED){
            setHeight(aSizeUnExpanded);
            currentState = ExpandableView.ExpandState.SHRINKED;
        }else {
            setHeight(aSizeExpanded);
            // TODO get expand size of child and limit it to max size depending on Screen size
            currentState = ExpandableView.ExpandState.EXPANDED;
        }
        if (onExpandChangeListener != null) onExpandChangeListener.onExpandChange(currentState);
        // TODO animate
        expandButton.setRotationX((currentState.State*180)%360);
    }

    private void setHeight(float newHeight){
        getLayoutParams().height = (int) newHeight;
        requestLayout();
    }

    OnExpandChangeListener onExpandChangeListener = null;

    /**
     * set a new {@link OnExpandChangeListener} to be called when the expansion state changes
     * @param listener new listener
     */
    public void setOnExpandChangeListener(OnExpandChangeListener listener){
        onExpandChangeListener = listener;
    }

    /**
     * listener for listening to state changes of the expansion state
     */
    public interface OnExpandChangeListener {
        /**
         * called, when the expansion state changes
         * @param newState new expansion state
         */
        void onExpandChange(ExpandableView.ExpandState newState);
    }

}
