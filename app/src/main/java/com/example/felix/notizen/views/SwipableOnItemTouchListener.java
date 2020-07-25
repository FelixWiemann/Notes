package com.example.felix.notizen.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SwipableOnItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    private View.OnTouchListener onTouchListener;

    /**
     * state whether we want to intercept the touch for the item itself
     */
    boolean intercept = false;
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
        if (!intercept) {
            intercept = e.getAction() == MotionEvent.ACTION_DOWN;
            called = false;
        }
        if(intercept) {
            boolean action = e.getAction() == MotionEvent.ACTION_UP;
            boolean time = e.getEventTime() - e.getDownTime() < 55;
            boolean distance = getMotionDistance(e) < 1;
            intercept = (action || time) && distance;
            Log.d("RecyclerView.SimpleOnItemTouchListener","action " + action + " event time " + (e.getEventTime() - e.getDownTime()) + " distance " + getMotionDistance(e));
        }
        Log.d("RecyclerView.SimpleOnItemTouchListener","onInterceptTouchEvent " + intercept);
        return intercept && !((SwipeRecyclerView) rv).isItemSwipeMenuActive;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        super.onTouchEvent(rv, e);
        intercept = false;
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
