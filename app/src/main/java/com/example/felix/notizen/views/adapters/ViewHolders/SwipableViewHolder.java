package com.example.felix.notizen.views.adapters.ViewHolders;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.felix.notizen.views.SwipableView;

public class SwipableViewHolder<T> extends ViewHolderInterface<T> {

    public ViewHolderInterface<T> viewHolderInterface;

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
}
