package com.example.felix.notizen.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.views.viewsort.ViewFilter;

/**
 * Created by Felix on 21.06.2018.
 */


public class customListView extends ListView {

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
        cNoteLogger.getInstance().logInfo("customListView init");

        this.setOnTouchListener(new OnTouchListener() {
            final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

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

    private final int REL_SWIPE_MIN_DISTANCE = (int)(120.0f * getResources().getDisplayMetrics().densityDpi / 160.0f + 0.5);
    private final int REL_SWIPE_MAX_OFF_PATH = (int)(250.0f * getResources().getDisplayMetrics().densityDpi / 160.0f + 0.5);
    private final int REL_SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * getResources().getDisplayMetrics().densityDpi / 160.0f + 0.5);

    private View currentlyAnimated = null;
    private float xBeforeAnimated;

    private boolean onDown(MotionEvent motionEvent){
        final View currentReturn;
        if (currentlyAnimated != null && currentlyAnimated != getChildViewAtMotionEventPosition(motionEvent)){
            currentReturn = currentlyAnimated;
            currentlyAnimated = null;
            ObjectAnimator animation = ObjectAnimator.ofFloat(currentReturn,"translationX", (0f));
            animation.setDuration(200);
            animation.start();

        }
        return false;
    }

    private void onLongPress(MotionEvent motionEvent) {
        // TODO open context menu -> actions delete, edit, etc.
    }

    private boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH)
            return false;

        int direction = 0;

        if(e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE &&
                Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {
            direction = -1;
        }  else if (e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE &&
                Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {
            direction = 1;
        }
        currentlyAnimated = getChildViewAtMotionEventPosition(e1);
        if (currentlyAnimated !=null){
            xBeforeAnimated = currentlyAnimated.getTranslationX();
            ObjectAnimator animation = ObjectAnimator.ofFloat(currentlyAnimated,"translationX", 130f*direction);
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
}
