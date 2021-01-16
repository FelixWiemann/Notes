package com.nepumuk.notizen.core.views.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderFactory;
import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerAdapter<T extends StorageObject> extends RecyclerView.Adapter<ViewHolderInterface<T>> implements Comparable<BaseRecyclerAdapter<T>>{

    private static final String TAG = "RECYCLER_ADAPTER";
    ArrayList<T> itemList;

    public final int sortOrder;

    public BaseRecyclerAdapter(List<T> itemList, int SortOrder) {
        super();
        this.itemList = new ArrayList<>();
        this.itemList.addAll(itemList);
        sortOrder = SortOrder;
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

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(BaseRecyclerAdapter o) {
        return sortOrder - o.sortOrder;
    }
}
