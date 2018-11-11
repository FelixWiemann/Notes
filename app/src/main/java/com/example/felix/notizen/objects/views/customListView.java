package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Felix on 21.06.2018.
 */


public class customListView extends ListView {
    public customListView(Context context) {
        super(context);
    }

    public customListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public customListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*@SuppressLint("WrongConstant")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Log.d("customListView","onMeasure");
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, 0));

        int childHeight = getMeasuredHeight() - (getListPaddingTop() + getListPaddingBottom() +  getVerticalFadingEdgeLength() * 2);

        // on a first run let's have a space for at least one child so it'll trigger remeasurement
        int fullHeight = getListPaddingTop() + getListPaddingBottom() + childHeight*(getCount());

        int newChildHeight = 0;
        for (int x = 0; x<getChildCount(); x++ ){
            View childAt = getChildAt(x);

            if (childAt != null) {
                int height = childAt.getHeight();
                newChildHeight += height;
            }
        }

        //on a second run with actual items - use proper size
        if (newChildHeight != 0)
            fullHeight = getListPaddingTop() + getListPaddingBottom() + newChildHeight;

       super.onMeasure();
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }
*/

    public void setListViewHeightBasedOnChildren() {
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = MeasureSpec.makeMeasureSpec(this.getWidth(), MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, this);
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        this.getLayoutParams().height = totalHeight + (this.getDividerHeight() * (listAdapter.getCount() - 1));
        this.requestLayout();
    }
}
