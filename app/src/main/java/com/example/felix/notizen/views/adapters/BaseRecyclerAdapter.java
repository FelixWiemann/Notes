package com.example.felix.notizen.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolderInterface<T>> {

    private static final String TAG = "RECYCLER_ADAPTER";
    private List<T> itemList;

    public BaseRecyclerAdapter(List<T> itemList) {
        super();
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolderInterface onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View toBeHold = LayoutInflater.from(viewGroup.getContext()).inflate(type, viewGroup, false);
        try {
            return ViewHolderFactory.getNewViewHolderInstance(type, toBeHold);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e(TAG, "onCreateViewHolder: failed to create ViewHolder", e);
        }
        throw new RuntimeException("sth went wrong");
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInterface viewHolder, int i) {
        viewHolder.bind(itemList.get(i));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType (int position){
        return ViewHolderFactory.getTypeForClass(itemList.get(position).getClass());
    }

    public List<T> getItemList(){
        return itemList;
    }

    public void setItemList(List<T> newList){
        itemList = newList;
    }
}
