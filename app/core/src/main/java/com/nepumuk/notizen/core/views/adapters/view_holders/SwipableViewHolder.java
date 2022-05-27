package com.nepumuk.notizen.core.views.adapters.view_holders;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.nepumuk.notizen.core.R;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.core.views.SwipableView;
import com.nepumuk.notizen.db.FavouriteRepository;


public class SwipableViewHolder<T extends DatabaseStorable> extends ViewHolderInterface<T> {

    public final ViewHolderInterface<T> viewHolderInterface;

    public SwipableViewHolder(@NonNull View itemView, ViewHolderInterface<T> viewHolderInterface) {
        super(itemView);
        this.viewHolderInterface = viewHolderInterface;
    }

    @Override
    public void bind(T toBind) {
        ((SwipableView)this.itemView).setMainView(viewHolderInterface.itemView);
        viewHolderInterface.bind(toBind);
        Drawable drawable = null;
        if (FavouriteRepository.INSTANCE.exists(toBind.getId())) {
            drawable = ContextCompat.getDrawable(itemView.getContext(),R.drawable.favourite_border);
        }
        Drawable finalDrawable = drawable;
        itemView.setBackground(finalDrawable);
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
