package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.felix.notizen.R;

public class cAbstractAdditionalView extends LinearLayout {

    public cAbstractAdditionalView(Context context) {
        super(context);
        init(null, 0);
    }

    public cAbstractAdditionalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public cAbstractAdditionalView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        /*final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.cAbstractAdditionalView, defStyle, 0);
        a.recycle();*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
