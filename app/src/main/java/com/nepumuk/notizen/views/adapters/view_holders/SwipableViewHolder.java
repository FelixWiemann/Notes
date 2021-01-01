package com.nepumuk.notizen.views.adapters.view_holders;

import android.view.View;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.views.SwipableView;

public class SwipableViewHolder<T> extends ViewHolderInterface<T> {

    public final ViewHolderInterface<T> viewHolderInterface;

    public SwipableViewHolder(@NonNull View itemView, ViewHolderInterface<T> viewHolderInterface) {
        super(itemView);
        this.viewHolderInterface = viewHolderInterface;
    }

    @Override
    public void bind(T toBind) {
        ((SwipableView)this.itemView).setMainView(viewHolderInterface.itemView);
        viewHolderInterface.bind(toBind);
    }

    public void setBackgroundVisibility(float position){
        ((SwipableView)this.itemView).showBackground(position);
    }

    @Override
    public boolean wasChildClicked() {
        // the state of the swipable is not really needed, pass it through to it's child viewHolder.
        return viewHolderInterface.wasChildClicked();
    }

    @Override
    public void resetChildClickedState() {
        // the state of the swipable is not really needed, pass it through to it's child viewHolder.
        viewHolderInterface.resetChildClickedState();
    }
}
