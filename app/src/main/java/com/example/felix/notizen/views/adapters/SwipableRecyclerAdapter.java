package com.example.felix.notizen.views.adapters;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.views.SwipableView;
import com.example.felix.notizen.views.adapters.ViewHolders.SwipableViewHolder;
import com.example.felix.notizen.views.adapters.ViewHolders.ViewHolderInterface;

import java.util.List;

public class SwipableRecyclerAdapter<T extends DatabaseStorable> extends SortableRecyclerAdapter<T> {

    public SwipableRecyclerAdapter(List<T> itemList) {
        super(itemList);
    }

    @NonNull
    @Override
    public ViewHolderInterface<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        SwipableView view = new SwipableView(viewGroup.getContext(), true);
        ViewHolderInterface<T> holder = super.onCreateViewHolder(viewGroup, type);
        return new SwipableViewHolder<>(view, holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInterface<T> viewHolder, int i) {
        viewHolder.bind(itemList.get(i));
    }

}
