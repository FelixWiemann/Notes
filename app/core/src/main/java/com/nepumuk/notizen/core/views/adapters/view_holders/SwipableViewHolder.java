package com.nepumuk.notizen.core.views.adapters.view_holders;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.R;
import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.utils.db_access.AppDataBaseHelper;
import com.nepumuk.notizen.core.views.SwipableView;


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
        new Thread(() -> {
            Drawable drawable = null;
            if (AppDataBaseHelper.getInstance().appDataBase.favouriteDAO().findFavourite(((StorageObject)toBind).getIdString())!=null) {
                drawable = itemView.getContext().getDrawable(R.drawable.favourite_border);
            }
            Drawable finalDrawable = drawable;
            // hacky -> make it proper
            ((Activity)itemView.getContext()).runOnUiThread(()->itemView.setBackground(finalDrawable));

        }).start();
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
