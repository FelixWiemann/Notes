package com.nepumuk.notizen.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class SwipableOnItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    private View.OnTouchListener onTouchListener;
    private static final String TAG="RecyclerView.SimpleOnItemTouch";
    private float tapDistance ;
    private boolean initialized = false;

    /**
     * state whether we want to intercept the touch for the item itself
     */
    boolean observe = false;

    boolean analyzeTouch = false;
    /**
     * interlock to only call once the edit note activity
     */
    boolean called = false;

    public SwipableOnItemTouchListener(View.OnTouchListener onTouchListener) {
        super();
        this.onTouchListener = onTouchListener;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        ViewConfiguration vc = ViewConfiguration.get(rv.getContext());
        boolean action = false;
        boolean time = false;
        boolean distance = false;
        boolean childIntercept = false;
        boolean inter = false;
        if (!initialized) {
            int mSlop = vc.getScaledTouchSlop();
            float scale = rv.getContext().getResources().getDisplayMetrics().density;
            tapDistance = 10; //(int) (mSlop * scale + 0.5f);
            initialized = true;
        }
        if(observe) {
            View childUnderTouch = rv.findChildViewUnder(e.getX(), e.getY());
            if (childUnderTouch instanceof ViewGroup) {
                // TODO does not get properly dispatched if only one task in the list
                childIntercept = childUnderTouch.dispatchTouchEvent(e);
            }
            action = e.getAction() == MotionEvent.ACTION_MOVE;
            time = e.getEventTime() - e.getDownTime() < ViewConfiguration.getTapTimeout();
            distance = getMotionDistance(e) < tapDistance;
            // intercept touch event, if we detect an up, touch has been shorter that tap timeout, motion distance had been smaller then tap distance and the child did not capture the touch itself
            observe = !(!action || !time || !distance || childIntercept);
            inter = !action && time  && distance &&!childIntercept && !((SwipeRecyclerView) rv).isItemSwipeMenuActive;
            //analyzeTouch = (action || time) && distance && !childIntercept;

        }
        if (!observe) {
            observe = e.getAction() == MotionEvent.ACTION_DOWN ;
            called = false;
        }
       if (inter) onTouchEvent(rv, e);
        return inter && !((SwipeRecyclerView) rv).isItemSwipeMenuActive;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        super.onTouchEvent(rv, e);
        observe = false;
        if (!called) {
            called = true;
            onTouchListener.onTouch(rv, e);
        }
    }

    double getMotionDistance(MotionEvent e){
        if(e.getHistorySize() == 0) {
            return 0;
        }
        return Math.sqrt(Math.pow(e.getHistoricalX(0)-e.getX(), 2) + Math.pow(e.getHistoricalY(0)-e.getY(), 2));
    }
}
