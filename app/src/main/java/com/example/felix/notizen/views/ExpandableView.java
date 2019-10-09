package com.example.felix.notizen.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.felix.notizen.R;
import com.example.felix.notizen.Utils.OnUpdateCallback;
import com.example.felix.notizen.objects.cStorageObject;

/**
 * ExpandableView can be expanded and made smaller again by the press of a button.
 * It holds an underlying view for displaying content.
 *
 * The button and a title is being shown.
 * on pressing the button, the underlying view is notified of the size change.
 */
public class ExpandableView extends LinearLayout implements OnUpdateCallback {

    /**
     * expansion state of an Expandable View
     */
    enum ExpandState{
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
        public int State;
        ExpandState(int state){
            State = state;
        }
    }

    private int aSizeUnExpanded = 200;
    private int aSizeExpanded = 300;
    private int sizeType = 0;
    private static final int EV_SIZE_WRAP_CONTENT = 1;
    private static final int EV_SIZE_CUST = 2;

    private TextView tvTitleView;
    private Button bt;

    ExpandState currentState = ExpandState.FIRSTINFLATE;

    /**
     * content view of the note
     */
    private cNoteDisplayView noteDisplayView;

    public ExpandableView(Context context){
        super(context);
        init(null, 0, null);
    }
    public ExpandableView(Context context, cStorageObject object) {
        super(context);
        init(null, 0, object);
    }
    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, null);
    }

    public ExpandableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle, null);
    }

    /**
     * get the ID of the object that is being displayed in this view
     * @return String id of the object
     */
    public String getObjectId(){
        return noteDisplayView.content.getId();
    }

    public cStorageObject getObject(){
        return noteDisplayView.content;
    }

    private void init(AttributeSet attrs, int defStyle, cStorageObject object) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ExpandableView, defStyle, 0);
        // load expanded/unexpanded height
        aSizeUnExpanded = a.getInt(R.styleable.ExpandableView_aSizeUnExpanded, 200);
        sizeType = a.getInt(R.styleable.ExpandableView_aSize, 0);
        a.recycle();
        noteDisplayView = cNoteDisplayViewFactory.getView(getContext(),object);
        noteDisplayView.setParentView(this);
        noteDisplayView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        // get the max size
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("global layout listener", "onGlobalLayout: " + getHeight() + " " + ExpandableView.this);
                if (currentState == ExpandState.FIRSTINFLATE) {
                    aSizeExpanded = getHeight();
                    if (aSizeExpanded <= aSizeUnExpanded){
                        aSizeExpanded = aSizeUnExpanded;
                    }
                    currentState = ExpandState.EXPANDED;
                    invertShrink();
                }
            }
        });
        // inflate layout
        inflateLayout(getContext());
        LinearLayout v = findViewById(R.id.abstractViewWrapper);
        v.addView(noteDisplayView,0);
        v.invalidate();
        noteDisplayView.onPostInflate();
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                invertShrink();
            }
        });
    }

    /**
     * inverts the shrinking state
     */
    private void invertShrink(){
        if (currentState == ExpandState.EXPANDED){
            setHeight(aSizeUnExpanded);
            currentState = ExpandState.SHRINKED;
            noteDisplayView.onShrink();
        }else {
            setHeight(aSizeExpanded);
            currentState = ExpandState.EXPANDED;
            noteDisplayView.onExpand();
        }
        // TODO animate
        bt.setRotationX((currentState.State*180)%360);
    }

    private void setHeight(int newHeight){
        Log.d("height","set height: "+ newHeight);
        getLayoutParams().height = newHeight;
        requestLayout();
    }

    /**
     * inflate the layout of the expandable view and init the UI-Elements
     * @param context
     */
    private void inflateLayout(Context context){
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.expandable_view_layout, this);
        bt = this.findViewById(R.id.expand_button);
        tvTitleView = findViewById(R.id.titleText);
        tvTitleView.setText(noteDisplayView.getTitle());
    }

    @Override
    public void update() {
        if (tvTitleView != null) {
            tvTitleView.setText(noteDisplayView.getTitle());
        }
    }
}