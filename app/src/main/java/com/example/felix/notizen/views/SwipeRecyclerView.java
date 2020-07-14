package com.example.felix.notizen.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;

import com.example.felix.notizen.NestedRecyclerView;
import com.example.felix.notizen.objects.cStorageObject;
import com.example.felix.notizen.views.adapters.SwipableRecyclerAdapter;

import java.util.ArrayList;

public class SwipeRecyclerView<T extends cStorageObject> extends NestedRecyclerView {

    private static final String TAG = "NOTESRECYCLER";

    /**
     * default must be true, so that we can activate the item touch handler
     */
    private boolean isOnItemTouchActive = true;
    private boolean isNoScrollActive = true;

    private SwipableRecyclerAdapter<T> adapter;


    public SwipeRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public SwipeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        adapter = new SwipableRecyclerAdapter<>(new ArrayList<T>(), 0);
        this.setAdapter(adapter);
        // todo get swipe-menu width from adapter
        ItemTouchHelper.Callback helperCallback = new SwipeHelperCallback(100, SwipeHelperCallback.NO_BUTTON);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helperCallback);
        itemTouchHelper.attachToRecyclerView(this);
        addOnScrollListener(onScrollListener);
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * sets the item touch state.
     * if the item touch state is false, the item touch handler will not be called
     * if it is true, the item touch handler will be called
     * @param ItemTouchState
     */
    public void SetState(boolean ItemTouchState){
        isOnItemTouchActive = ItemTouchState;
    }

    private RecyclerView.OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            isNoScrollActive = newState == SCROLL_STATE_IDLE;
            Log.d(TAG, "new scroll state: " + isNoScrollActive);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };


    public SwipableRecyclerAdapter<T> getAdapter(){
        return adapter;
    }

}
