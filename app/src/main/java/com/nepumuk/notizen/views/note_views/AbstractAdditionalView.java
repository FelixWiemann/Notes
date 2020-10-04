package com.nepumuk.notizen.views.note_views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class AbstractAdditionalView extends LinearLayout{

    public AbstractAdditionalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractAdditionalView(Context context, AttributeSet attrs, int resourceId) {
        super(context, attrs);
        inflateChildView(resourceId);
    }

    public AbstractAdditionalView(Context context, int resourceId){
        super(context);
        inflateChildView(resourceId);
    }

    public AbstractAdditionalView(Context context, AttributeSet attrs, int defStyle, int resourceId) {
        super(context, attrs, defStyle);
        inflateChildView(resourceId);
    }

    private void inflateChildView(int resourceId){
        LayoutInflater mInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert mInflater != null;
        mInflater.inflate(resourceId, this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
