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

import static java.lang.String.format;

/**
 * TODO: document your custom view class.
 */
public class ExpandableView extends LinearLayout implements View.OnClickListener {

    private int aSizeUnExpanded = 10;
    private int aSizeExpanded = 60;
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

    public ExpandableView(Context context){//, cIdObject content) {
        super(context);
        log.log("ExpandableView(context)",logLevel);
        init(null, 0);
        //int i = this.indexOfChild(vS);
        //vS = new cViewSelector(context,content);
        //this.addView(vS,i);
    }
    public ExpandableView(Context context, cIdObject object, Adapter parent) {
        super(context);
        log.log("ExpandableView(context)",logLevel);
        content = object;
        init(null, 0);
        parentAdapter = (cExpandableViewAdapter) parent;


    }


    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        log.log("exView context. attrs",logLevel);
        init(attrs, 0);

    }

    public ExpandableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        log.log("exView con, attrs, style",logLevel);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        log.log("init exView",logLevel);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ExpandableView, defStyle, 0);
        log.log("loading params",logLevel);
        // load expanded/unexpanded height
        aSizeUnExpanded = a.getInt(R.styleable.ExpandableView_aSizeUnExpanded, 10);
        aSizeExpanded = a.getInt(R.styleable.ExpandableView_aSizeExpanded,60);
        sizeType = a.getInt(R.styleable.ExpandableView_aSize, 0);

        abstractAdditionalView = cAbstractAdditionalViewFactory.getView(getContext(),content);
        ((abstractionInterface)abstractAdditionalView).initView(attrs,defStyle);
        View v = findViewById(R.id.abstractView);
        int index = this.indexOfChild(v);
        this.removeView(v);
        this.addView(abstractAdditionalView,index);

        a.recycle();
        // inflate layout
        log.log("inflating layout",logLevel);
        inflateLayout(getContext());
        bt.setOnClickListener(this);
        setHeight(aSizeUnExpanded);
        if (sizeType == EV_SIZE_WRAP_CONTENT){
            setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        }else if(sizeType == EV_SIZE_CUST){
            setHeight(aSizeExpanded);
        }


        //this.setOnClickListener(this);
    }

    private void setHeight(int newHeight){
        log.log("set height: "+Integer.toString(newHeight),logLevel);
        //measure(ViewGroup.LayoutParams.MATCH_PARENT,newHeight);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,newHeight);
        customListView root = (customListView) getParent();
        if (root!=null) {

            ((customListView) getParent()).setLayoutParams(layoutParams);
            root.measure(root.getMeasuredWidth() ,root.getMeasuredHeight());
        }
        //setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,newHeight));
        //this.setLayoutParams(layoutParams);
        requestLayout();
        //this.setMinimumHeight(newHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        log.log("onDraw",logLevel);
    }

    private void inflateLayout(Context context){
        log.log("inflating",logLevel);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.expandable_view_layout, this);
        tv = (TextView) this.findViewById(R.id.title_text);
        bt = (Button) this.findViewById(R.id.expand_button);
    }

    public void setContent(cIdObject content){
        tv.setText(content.getTitle());

        this.invalidate();
    }

    @Override
    public void onMeasure(int width, int height){
        super.onMeasure(width, height);
    }

    @Override
    public void onClick(View v) {
        log.log("onClick",logLevel);
        Log.d("onclick",Integer.toString(this.getLayoutParams().height));
        if (this.getLayoutParams().height==aSizeExpanded){
            setHeight(aSizeUnExpanded);
            this.setBackgroundColor(Color.RED);
        }else{
            setHeight(aSizeExpanded);
            this.setBackgroundColor(Color.BLUE);
        }
        //parentAdapter.notifyDataSetChanged();
    }
}