package com.example.felix.notizen.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.cSortableObject;
import com.example.felix.notizen.views.adapters.SortableAdapter;
import com.example.felix.notizen.views.adapters.cSwipableViewAdapter;
import com.example.felix.notizen.views.viewsort.ViewFilter;

import java.util.Comparator;

public class SortableListView extends ListView {

    protected SortableAdapter adapter;

    public SortableListView(Context context) {
        super(context);
        init();
    }

    public SortableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SortableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SortableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        adapter = new cSwipableViewAdapter();
        this.setAdapter(adapter);
    }

    public void add(DatabaseStorable storable){
        adapter.add(storable);
        adapter.notifyDataSetChanged();
    }

    public void remove(DatabaseStorable storable){
        adapter.remove(storable);
        adapter.notifyDataSetChanged();
    }

    /**
     * gets the view that is being pointed at by the motion event
     * does not work with header viewss
     * @param event
     * @return
     */
    protected View getChildViewAtMotionEventPosition(MotionEvent event){
        if (event == null){
            return null;
        }
        return getChildViewAtMotionEventPosition((int)event.getX(),(int)event.getY());
    }

    /**
     * gets the view that is being pointed at by the motion event
     * does not work with header viewss
     * @param x coordinate
     * @param y coordinate
     * @return
     */
    protected View getChildViewAtMotionEventPosition(int x, int y){
        return this.getChildAt(this.pointToPosition(x,y));
    }


    public void filter(ViewFilter filter) {
        adapter.filter(filter);
    }

    public void sort(Comparator<DatabaseStorable> category){
        adapter.sort(category);
    }

}
