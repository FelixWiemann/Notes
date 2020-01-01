package com.example.felix.notizen.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ViewHolderInterface<T> extends RecyclerView.ViewHolder {

    public ViewHolderInterface(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(T toBind);

}
