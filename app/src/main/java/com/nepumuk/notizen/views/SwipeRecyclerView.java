package com.nepumuk.notizen.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.views.adapters.SwipableRecyclerAdapter;

import java.util.ArrayList;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;

public class SwipeRecyclerView<T extends StorageObject> extends NestedRecyclerView {

    private static final String TAG = "NOTESRECYCLER";

    /**
     * whether the swipe menu is active
     */
    public boolean isItemSwipeMenuActive = false;

    private SwipableRecyclerAdapter<T> adapter;

    SwipeHelperCallback helperCallback;


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
        // set default adapter
        adapter = new SwipableRecyclerAdapter<>(new ArrayList<T>(), 0, false);
        this.setAdapter(adapter);
        // todo get swipe-menu width from adapter
        helperCallback = new SwipeHelperCallback(100, SwipeHelperCallback.NO_BUTTON);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helperCallback);
        itemTouchHelper.attachToRecyclerView(this);
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * sets the swipe menu state.
     * if the swipe menu state is false, the item touch handler will not be called
     * if it is true, the item touch handler will be called
     * @param SwipeMenuState new state
     */
    public void SetState(boolean SwipeMenuState){
        isItemSwipeMenuActive = SwipeMenuState;
    }

    /**
     * get the adapter in this recycler view
     * @return adapter
     */
    public SwipableRecyclerAdapter<T> getAdapter(){
        return adapter;
    }

    public void resetSwipeState(){
        helperCallback.resetSwipeState(ACTION_STATE_SWIPE,true);
    }

}
