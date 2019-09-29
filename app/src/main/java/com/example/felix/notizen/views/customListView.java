package com.example.felix.notizen.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.views.viewsort.ViewFilter;

/**
 * Created by Felix on 21.06.2018.
 */
public class customListView extends ListView {

    private static final String TAG = "customListView";
    private OnLongPressListener onLongPressListener;

    private static final String ANIM_X_VAR = "mainX";

    private cExpandableViewAdapter adapter = new cExpandableViewAdapter();

    public customListView(Context context) {
        super(context);
        init();
    }

    public customListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public customListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void add(DatabaseStorable storable){
        adapter.add(storable);
        adapter.notifyDataSetChanged();
    }

    public void remove(DatabaseStorable storable){
        adapter.remove(storable);
        adapter.notifyDataSetChanged();
    }

    public void init(){
        this.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        this.setAdapter(adapter);
        Log.i(TAG, "customListView init");

        this.setOnTouchListener(new OnTouchListener() {
            final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
                    //customListView.this.onScroll(e1, e2, distanceX, distanceY);
                    return false;
                }

                @Override
                public boolean onDown(MotionEvent motionEvent) {
                    return customListView.this.onDown(motionEvent);
                }

                @Override
                public void onLongPress(MotionEvent motionEvent) {
                    customListView.this.onLongPress(motionEvent);


                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    return customListView.this.onFling(e1,e2,velocityX,velocityY);
                }
            });

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

    }

    private final int REL_FLING_MIN_DISTANCE = (int)(120.0f * getResources().getDisplayMetrics().densityDpi / 160.0f + 0.5);
    private final int REL_FLING_MAX_OFF_PATH = (int)(250.0f * getResources().getDisplayMetrics().densityDpi / 160.0f + 0.5);
    private final int REL_FLING_THRESHOLD_VELOCITY = (int)(100.0f * getResources().getDisplayMetrics().densityDpi / 160.0f + 0.5);
    private final int REL_SWIPE_MIN_DISTANCE = (int)(1.0f * getResources().getDisplayMetrics().densityDpi / 160.0f + 0.5);
    private final int REL_SWIPE_MAX_DISTANCE = (int)(200.0f * getResources().getDisplayMetrics().densityDpi / 160.0f + 0.5);
    private final int REL_SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * getResources().getDisplayMetrics().densityDpi / 160.0f + 0.5);

    private View currentlyAnimated = null;
    private float xBeforeAnimated;

    private boolean onDown(MotionEvent motionEvent){
        SwipableView currentReturn;
        if (currentlyAnimated != null && currentlyAnimated != getChildViewAtMotionEventPosition(motionEvent)){
            currentReturn = (SwipableView) currentlyAnimated;
            currentlyAnimated = null;
            ObjectAnimator animation = ObjectAnimator.ofFloat(currentReturn,ANIM_X_VAR, (0f));
            animation.setDuration(200);
            animation.start();

        }
        return false;
    }

    private void onLongPress(MotionEvent event) {
        if (onLongPressListener!=null){
            onLongPressListener.onLongPress(adapter.getItem(pointToPosition((int)event.getX(),(int)event.getY())));
        }

    }

    private void onScroll(MotionEvent e1, MotionEvent e2, float distx, float disty){
        int direction = 0;

        if(Math.abs(e1.getX() - e2.getX()) > REL_SWIPE_MIN_DISTANCE) {
            direction = 1;
        }
        if (e1.getX()-e2.getX() > REL_SWIPE_MAX_DISTANCE){
            direction = 0;
        }

        currentlyAnimated = getChildViewAtMotionEventPosition(e1);
        if (currentlyAnimated !=null){
            xBeforeAnimated = currentlyAnimated.getTranslationX();
            ObjectAnimator animation = ObjectAnimator.ofFloat(currentlyAnimated,ANIM_X_VAR, (e1.getX() - e2.getX())*direction);
            animation.setDuration(100);
            animation.start();
        }

    }

    private boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(e1.getY() - e2.getY()) > REL_FLING_MAX_OFF_PATH)
            return false;

        int direction = 0;

        if(e1.getX() - e2.getX() > REL_FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > REL_FLING_THRESHOLD_VELOCITY) {
            direction = -1;
        }  else if (e2.getX() - e1.getX() > REL_FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > REL_FLING_THRESHOLD_VELOCITY) {
            direction = 1;
        }
        currentlyAnimated = (SwipableView)getChildViewAtMotionEventPosition(e1);
        if (currentlyAnimated !=null){
            xBeforeAnimated = currentlyAnimated.getTranslationX();
            ObjectAnimator animation = ObjectAnimator.ofFloat(currentlyAnimated,ANIM_X_VAR, 130f*direction);
            //ObjectAnimator animation = ObjectAnimator.ofFloat(currentlyAnimated,"translationX", 130f*direction);
            animation.setDuration(400);
            animation.start();
        }
        return false;
    }

    private View getChildViewAtMotionEventPosition(MotionEvent event){
        if (event == null){
            return null;
        }
        return this.getChildAt(this.pointToPosition((int)event.getX(),(int)event.getY()));
    }

    @Override
    public boolean performClick(){
        onDown(null);
        return super.performClick();
    }

    public void filter(ViewFilter filter) {
        adapter.filter(filter);
    }

    public void setOnLongPressListener(OnLongPressListener onLongPressListener) {
        this.onLongPressListener = onLongPressListener;
    }
}
