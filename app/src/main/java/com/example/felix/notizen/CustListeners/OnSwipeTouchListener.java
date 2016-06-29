package com.example.felix.notizen.CustListeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Felix "nepumuk" Wiemann on 24.05.2016
 * as part of Notizen
 */

public abstract class OnSwipeTouchListener implements View.OnTouchListener {

    private static final String LOG_TAG = "OnSwipeTouchListener";
    private GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context c) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    @Override
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return gestureDetector.onTouchEvent(motionEvent);
    }

    //TODO abstract different Touch gestures

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 30;
        private static final int SWIPE_VELOCITY_THRESHOLD = 30;

        @Override
        public boolean onDown(MotionEvent e) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            return true;
        }

        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight(diffX);
                            return true;
                        } else {
                            onSwipeLeft(diffX);
                            return true;
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeDown(diffY);
                            return true;
                        } else {
                            onSwipeUp(diffY);
                            return true;
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return false;
        }
    }

    public abstract void onSwipeRight(float diffRight);

    public abstract void onSwipeLeft(float diffLeft) ;

    public abstract void onSwipeUp(float diffUp) ;

    public abstract void onSwipeDown(float diffDown) ;
}