package com.nepumuk.notizen.core.views.adapters.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;

/**
 * recycler view holder to bind typed Objects
 *
 * implement a new view holder of this kind to be displayed in recycler views.
 *
 * @param <T> type the view holder shall bind to
 */
public abstract class ViewHolderInterface<T extends DatabaseStorable> extends RecyclerView.ViewHolder {

    public ViewHolderInterface(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(T toBind);

    /**
     * set to true, if you detected a click to one of this instance's children
     * if so, the parent recycler view may look at this state and decide whether to show perform it's onItemTouch action
     */
    protected boolean wasClickHandledByChildView = false;


    public boolean wasChildClicked() {
        return wasClickHandledByChildView;
    }

    public void resetChildClickedState() {
        wasClickHandledByChildView = false;
    }
}
