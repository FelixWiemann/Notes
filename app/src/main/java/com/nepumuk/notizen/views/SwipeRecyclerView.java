package com.nepumuk.notizen.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.views.adapters.SwipableRecyclerAdapter;

import java.util.ArrayList;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;

public class SwipeRecyclerView<T extends StorageObject> extends NestedRecyclerView {

    private static final String LOG_TAG = "NOTESRECYCLER";

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(layoutManager);
        helperCallback = new SwipeHelperCallback(SwipeHelperCallback.NO_BUTTON, SwipeHelperCallback.NO_BUTTON);
        // set default adapter
        adapter = new SwipableRecyclerAdapter<>(new ArrayList<T>(), 0, false, R.layout.swipable_left, R.layout.swipable_empty);
        this.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helperCallback);
        itemTouchHelper.attachToRecyclerView(this);
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
