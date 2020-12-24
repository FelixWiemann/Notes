package com.nepumuk.notizen.views.adapters;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.views.SwipableView;
import com.nepumuk.notizen.views.adapters.view_holders.SwipableViewHolder;
import com.nepumuk.notizen.views.adapters.view_holders.ViewHolderInterface;

import java.util.List;

public class SwipableRecyclerAdapter<T extends StorageObject> extends SortableRecyclerAdapter<T> {
    private String LOG_TAG = "ADAPTER";


    public OnSwipeableClickListener OnLeftClick;
    public OnSwipeableClickListener OnRightClick;
    public OnSwipeableClickListener OnMiddleClick;


    final private boolean inCompoundAdapter;
    private final int leftSwipeMenu;
    private final int rightSwipeMenu;

    /**
     * <p>creates a new SwipableView based on given context.
     * </p><p>
     * </p><p>set inCompoundAdapter to true if it is created from within {@link com.nepumuk.notizen.views.adapters.CompoundAdapter} or shall be used in one
     * </p><p>if either leftSwipeMenu or rightSwipeMenu are set to {@link R.layout#swipable_empty}, the menu is ignored
     * @param itemList
     * @param SortOrder
     * @param inCompoundAdapter
     * @param leftSwipeMenu
     * @param rightSwipeMenu
     */
    public SwipableRecyclerAdapter(List<T> itemList, int SortOrder, boolean inCompoundAdapter, @LayoutRes int leftSwipeMenu, @LayoutRes int rightSwipeMenu) {
        super(itemList,SortOrder);
        this.inCompoundAdapter = inCompoundAdapter;
        this.leftSwipeMenu = leftSwipeMenu;
        this.rightSwipeMenu = rightSwipeMenu;
    }

    @NonNull
    @Override
    public ViewHolderInterface<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        final SwipableView view = new SwipableView(viewGroup.getContext(), inCompoundAdapter, leftSwipeMenu, rightSwipeMenu);
        view.setOnClickListeners(v -> {
            if (OnLeftClick != null) {
                OnLeftClick.onClick(v, view);
            }
        }, v -> {
            if (OnRightClick != null) {
                OnRightClick.onClick(v, view);
            }
        }, new View.OnTouchListener() {
            /**
             * Called when a touch event is dispatched to a view. This allows listeners to
             * get a chance to respond before the target view.
             *
             * @param v     The view the touch event has been dispatched to.
             * @param event The MotionEvent object containing full information about
             *              the event.
             * @return True if the listener has consumed the event, false otherwise.
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (OnMiddleClick != null) {
                    if (event.getAction() == MotionEvent.ACTION_UP) OnMiddleClick.onClick(v, view);
                }
                return false;
            }
        });
        ViewHolderInterface<T> holder = super.onCreateViewHolder(viewGroup, type);
        return new SwipableViewHolder<>(view, holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInterface<T> viewHolder, int i) {
        viewHolder.bind(itemList.get(i));
    }
}
