package com.example.felix.notizen.views;

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

    private int aSizeUnExpanded = 500;
    private int sizeType = 0;
    private static final int EV_SIZE_WRAP_CONTENT = 1;
    private static final int EV_SIZE_CUST = 2;

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
        noteDisplayView = cNoteDisplayViewFactory.getView(getContext(),object);
        noteDisplayView.setParentView(this);
        a.recycle();
        // inflate layout
        inflateLayout(getContext());
        LinearLayout v = findViewById(R.id.abstractViewWrapper);
        noteDisplayView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, aSizeUnExpanded));
        v.addView(noteDisplayView,0);
        v.invalidate();
        noteDisplayView.onPostInflate();
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

    }


    private void setHeight(int newHeight){
        Log.d("height","set height: "+Integer.toString(newHeight));
        getLayoutParams().height = newHeight;
        //noteDisplayView.getLayoutParams().width = getLayoutParams().width-50;
        //noteDisplayView.requestLayout();
        requestLayout();
    }

    private void inflateLayout(Context context){
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.expandable_view_layout, this);
        bt = this.findViewById(R.id.expand_button);

        tvTitleView = findViewById(R.id.titleText);
        tvTitleView.setText(noteDisplayView.getTitle());
    }

    int count = 0;

    @Override
    public void update() {
        // TODO make sure inflateLayout has been called before this runs
        tvTitleView.setText(noteDisplayView.getTitle());
    }
}