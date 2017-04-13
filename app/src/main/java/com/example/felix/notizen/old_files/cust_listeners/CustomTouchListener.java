package com.example.felix.notizen.old_files.cust_listeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Felix "nepumuk" Wiemann on 13.02.2016
 * as part of Notizen
 *
 */
public class CustomTouchListener extends OnSwipeTouchListener implements AdapterView.OnItemClickListener, GestureDetector.OnDoubleTapListener {
    private static final String LOG_TAG = "CustomTouchListener" ;
    View v;
    public Context c;

    @SuppressWarnings("unused")
    public CustomTouchListener(Context c ,View v) {
        super(c);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        this.v=v;
    }

    public CustomTouchListener(Context context) {
        super(context);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        c=context;

    }

    @Override
    public void onSwipeRight(float diffRight) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onSwipeLeft(float diffLeft) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

    }

    @Override
    public void onSwipeUp(float diffUp) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

    }

    @Override
    public void onSwipeDown(float diffDown) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}
