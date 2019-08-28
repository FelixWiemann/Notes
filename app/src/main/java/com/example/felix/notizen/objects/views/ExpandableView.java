package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Displayable;

import static java.lang.String.format;

/**
 * TODO: document your custom view class.
 */
public class ExpandableView extends LinearLayout implements View.OnClickListener {

    private int aSizeUnExpanded = 500;
    private int sizeType = 0;
    private static final int EV_SIZE_WRAP_CONTENT = 1;
    private static final int EV_SIZE_CUST = 2;
    // set log level of ExpandableView independently of application to reduce log entries while debugging
    private int logLevel = cNoteLogger.DEBUG_LEVEL_DEBUG;
    private cNoteLogger log = cNoteLogger.getInstance();

    private TextView tvTitleView;
    private Button bt;


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

    private void init(AttributeSet attrs, int defStyle, cStorageObject object) {
        log.log("init exView",logLevel);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ExpandableView, defStyle, 0);
        log.log("loading params",logLevel);
        content = object;
        // load expanded/unexpanded height
        aSizeUnExpanded = a.getInt(R.styleable.ExpandableView_aSizeUnExpanded, 200);
        sizeType = a.getInt(R.styleable.ExpandableView_aSize, 0);
        noteDisplayView = cNoteDisplayViewFactory.getView(getContext(),object);
        a.recycle();
        // inflate layout
        log.log("inflating layout",logLevel);
        inflateLayout(getContext());
        LinearLayout v = findViewById(R.id.abstractViewWrapper);
        noteDisplayView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, aSizeUnExpanded));
        v.addView(noteDisplayView,0);
        v.invalidate();
        noteDisplayView.postInflate();
        bt.setOnClickListener(this);

    }

    @Override
    public void onFinishInflate(){
        super.onFinishInflate();
    }

    private void setHeight(int newHeight){
        Log.d("height","set height: "+Integer.toString(newHeight));
        getLayoutParams().height = newHeight;
        //noteDisplayView.getLayoutParams().width = getLayoutParams().width-50;
        //noteDisplayView.requestLayout();
        requestLayout();
    }

    private void inflateLayout(Context context){
        log.log("inflating",logLevel);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.expandable_view_layout, this);
        bt = this.findViewById(R.id.expand_button);

        tvTitleView = findViewById(R.id.titleText);
        tvTitleView.setText(content.getTitle());
    }

    int count = 0;

    @Override
    public void onClick(View v) {
        log.log("onClick",logLevel);
        if (count ==1){
            setHeight(aSizeUnExpanded);
            count = 0;
            noteDisplayView.onShrink();
        }else {
            setHeight(noteDisplayView.getExpandedSize());
            count = 1;
            noteDisplayView.onExpand();
        }
        bt.setRotationX((count*180)%360);
    }
}