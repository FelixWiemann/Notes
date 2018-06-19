package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.objects.cIdObject;

/**
 * TODO: document your custom view class.
 */
public class cViewSelector extends LinearLayout {

    public cViewSelector(Context context,cIdObject content) {
        super(context);
        setContent(content);
        init(null, 0);
    }

    public cViewSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public cViewSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        //inflate(R.layout.testlayout3);
        //inflate(R.layout.testlayout3);
        /*LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.testlayout3, (ViewGroup) this.getRootView());*/

    }

    private void inflate(int pId){
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(pId, (ViewGroup) this.getRootView());
        getRootView().invalidate();
        //this.getRootView().invalidate();
    }

    public void setContent(cIdObject pObject){
        if (pObject.aTYPE == cIdObject.aTYPE){
            inflate( R.layout.testlayout1);
        }
        else if (pObject.aTYPE == cTask.aTYPE){
            inflate(R.layout.testlayout2);
        }
        else {
            inflate( R.layout.testlayout2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
