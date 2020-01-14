package com.example.felix.notizen.views.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.views.adapters.ViewHolders.ViewHolderFactory;
import com.example.felix.notizen.views.adapters.ViewHolders.ViewHolderInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerAdapter<T extends DatabaseStorable> extends RecyclerView.Adapter<ViewHolderInterface<T>> {

    private static final String TAG = "RECYCLER_ADAPTER";
    ArrayList<T> itemList;

    BaseRecyclerAdapter(List<T> itemList) {
        super();
        this.itemList = new ArrayList<>();
        this.itemList.addAll(itemList);
    }

    @NonNull
    @Override
    public ViewHolderInterface<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View toBeHold = LayoutInflater.from(viewGroup.getContext()).inflate(type, viewGroup, false);
        try {
            return ViewHolderFactory.getNewViewHolderInstance(type, toBeHold);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e(TAG, "onCreateViewHolder: failed to create ViewHolder", e);
        }
        throw new RuntimeException("sth went wrong");
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInterface<T> viewHolder, int i) {
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

    @CallSuper
    public ArrayList<T> getAllObjects(){
        return itemList;
    }

    @CallSuper
    public void replace(List<T> newList){
        clear();
        addAll(newList);
    }

    /**
     * get the item stored at the given index
     * @param index of the item to retrieve
     * @return item stored at index
     */
    public T getItem(int index){
        return itemList.get(index);
    }

    /**
     * adds all items from the list to the adapter
     * @param toAdd list of items to Add
     */
    void addAll(List<T> toAdd){
        itemList.addAll(toAdd);
        notifyDataSetChanged();
    }

    /**
     * add an object to the adapter to be displayed
     *
     * will not be filtered or sorted afterwards!
     *
     * @param toAdd storable that is to be added
     */
    public void add(T toAdd){
        itemList.add(toAdd);
        notifyDataSetChanged();
    }

    /**
     * clears the adapter from all it's content,
     * filters and sorting stays the same
     */
    @CallSuper
    public void clear(){
        itemList.clear();
        notifyDataSetChanged();
    }

    /**
     * permanently removes a Storable from the adapter
     * @param object to be removed
     */
    @CallSuper
    public void remove(T object){
        itemList.remove(object);
    }

}
