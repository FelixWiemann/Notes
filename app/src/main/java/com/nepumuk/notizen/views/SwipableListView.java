package com.nepumuk.notizen.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.nepumuk.notizen.Utils.DBAccess.DatabaseStorable;
import com.nepumuk.notizen.views.adapters.cSwipableViewAdapter;

/**
 * Created by Felix on 21.06.2018.
 */
@Deprecated
public class SwipableListView extends SortableListView {

    private static final String TAG = "SwipableListView";
    private OnLongPressListener onLongPressListener;


    // TODO header is adapter responsibility
    private NoteListViewHeaderView headerView;

    public SwipableListView(Context context) {
        super(context);
        init();
    }

    public SwipableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        this.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        adapter = new cSwipableViewAdapter();
        this.setAdapter(adapter);
        Log.i(TAG, "SwipableListView init");
        headerView = new NoteListViewHeaderView(this.getContext());
        this.addHeaderView(headerView);
        // swipe handling should be done by the view and not the list view
        this.setOnTouchListener(new OnTouchListener() {
            final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
                    //customListView.this.onScroll(e1, e2, distanceX, distanceY);
                    return false;
                }

                @Override
                public boolean onDown(MotionEvent motionEvent) {
                    return SwipableListView.this.onDown(motionEvent);
                }

                @Override
                public void onLongPress(MotionEvent motionEvent) {
                    SwipableListView.this.onLongPress(motionEvent);


                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    if (e1 == null || e2 == null){
                        return false;
                    }
                    return SwipableListView.this.onFling(e1,e2,velocityX,velocityY);
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

    private static final String ANIM_X_VAR = "mainX";

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
            // decide if it can be handled by the list view item itself, therefore no need to worry about header views...
            onLongPressListener.onLongPress((DatabaseStorable) adapter.getItem(pointToPosition((int)event.getX(),(int)event.getY())-getHeaderViewsCount()));
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
        // if position is a header view, don't swipe
        if (this.pointToPosition((int) e1.getX(), (int) e1.getY()) < getHeaderViewsCount()) {
            return false;
        }
        if (Math.abs(e1.getY() - e2.getY()) > REL_FLING_MAX_OFF_PATH)
            return false;

        int direction = 0;

        if (e1.getX() - e2.getX() > REL_FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > REL_FLING_THRESHOLD_VELOCITY) {
            // to allow swipe to the left, set direction to -1
            direction = 0;
        } else if (e2.getX() - e1.getX() > REL_FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > REL_FLING_THRESHOLD_VELOCITY) {
            direction = 1;
        }
        currentlyAnimated = getChildViewAtMotionEventPosition(e1);
        if (currentlyAnimated != null) {
            xBeforeAnimated = currentlyAnimated.getTranslationX();
            ObjectAnimator animation = ObjectAnimator.ofFloat(currentlyAnimated, ANIM_X_VAR, 130f * direction);
            animation.setDuration(400);
            animation.start();
        }
        return false;
    }

    @Override
    public boolean performClick(){
        onDown(null);
        return super.performClick();
    }

    public void setOnLongPressListener(OnLongPressListener onLongPressListener) {
        this.onLongPressListener = onLongPressListener;
    }

    public void update(int size) {
        headerView.update(size);
    }
}
