package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.felix.notizen.Utils.Logger.cNoteLogger;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.cIdObject;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static java.lang.String.format;

/**
 * TODO: document your custom view class.
 */
public class ExpandableView extends LinearLayout implements View.OnClickListener {

    private int aSizeUnExpanded = 500;
    private int aSizeExpanded = 600;
    private int sizeType = 0;
    private final int EV_SIZE_WRAP_CONTENT = 1;
    private final int EV_SIZE_CUST = 2;
    // set log level of ExpandableView independently of application to reduce log entries while debugging
    private int logLevel = cNoteLogger.mDebugLevelDebug;
    private cNoteLogger log = cNoteLogger.getInstance();

    private TextView tv;
    private Button bt;

    private cAbstractAdditionalView abstractAdditionalView;

    private cExpandableViewAdapter parentAdapter;

    private cIdObject content;

    public ExpandableView(Context context){
        super(context);
        init(null, 0);
    }
    public ExpandableView(Context context, cIdObject object, Adapter parent) {
        super(context);
        content = object;
        init(null, 0);
        parentAdapter = (cExpandableViewAdapter) parent;
    }
    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ExpandableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        log.log("init exView",logLevel);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ExpandableView, defStyle, 0);
        log.log("loading params",logLevel);
        // load expanded/unexpanded height
        aSizeUnExpanded = a.getInt(R.styleable.ExpandableView_aSizeUnExpanded, 200);
        aSizeExpanded = a.getInt(R.styleable.ExpandableView_aSizeExpanded,200);
        sizeType = a.getInt(R.styleable.ExpandableView_aSize, 0);

        abstractAdditionalView = cAbstractAdditionalViewFactory.getView(getContext(),content);
        ((abstractionInterface)abstractAdditionalView).initView(attrs,defStyle);
        View v = findViewById(R.id.abstractViewWrapper);
        int index = this.indexOfChild(v);
        this.removeView(v);
        this.addView(abstractAdditionalView,index);

        a.recycle();
        // inflate layout
        log.log("inflating layout",logLevel);
        inflateLayout(getContext());
        bt.setOnClickListener(this);
    }

    private void setHeight(int newHeight){
        Log.d("height","set height: "+Integer.toString(newHeight));
        getLayoutParams().height = newHeight;
        //abstractAdditionalView.getLayoutParams().width = getLayoutParams().width-50;
        //abstractAdditionalView.requestLayout();
        requestLayout();
    }

    private void inflateLayout(Context context){
        log.log("inflating",logLevel);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.expandable_view_layout, this);
        bt = (Button) this.findViewById(R.id.expand_button);
    }

    int count = 0;

    @Override
    public void onClick(View v) {
        log.log("onClick",logLevel);
        int height = getHeight();
        if (count ==1){
            setHeight(height/2);
            count = 0;
        }else {
            setHeight(height*2);
            count = 1;
        }
        bt.setRotationX((count*180)%360);
    }
}