package com.nepumuk.notizen.views.adapters.view_holders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * recycler view holder to bind typed Objects
 *
 * implement a new view holder of this kind to be displayed in recycler views.
 *
 * @param <T> type the view holder shall bind to
 */
public abstract class ViewHolderInterface<T> extends RecyclerView.ViewHolder {

    public ViewHolderInterface(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(T toBind);

}
