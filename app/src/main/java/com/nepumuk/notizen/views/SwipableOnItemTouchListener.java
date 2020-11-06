package com.nepumuk.notizen.views;

import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SwipableOnItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    private final OnItemTouchListener onTouchListener;
    private static final String LOG_TAG="SimpleOnItemTouch";

    GestureDetector gestureDetector;
    public SwipableOnItemTouchListener(RecyclerView parent, OnItemTouchListener onTouchListener) {
        super();
        this.onTouchListener = onTouchListener;
        gestureDetector = new GestureDetector(parent.getContext(),new OnGestureDetector());
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }


    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        super.onTouchEvent(rv, e);
    }

    public interface OnItemTouchListener{
        boolean onTouch(MotionEvent e);
    }
    private class OnGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return onTouchListener.onTouch(e);
        }
    }
}
