package com.nepumuk.notizen.views.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.views.ExpandableView;
import com.nepumuk.notizen.views.adapters.view_holders.CompoundViewHolder;
import com.nepumuk.notizen.views.adapters.view_holders.ViewHolderInterface;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Keep
public class CompoundAdapter<T extends StorageObject> extends SortableRecyclerAdapter<T> {

    private static final String LOG_TAG = "CompoundAdapter";
    /**
     * List of adapters and the view they need to fill
     */
    private final HashMap<BaseRecyclerAdapter<T>, Integer> recyclerAdapters;

    private final int ViewId;

    public CompoundAdapter(List<T> itemList, int CompoundViewLayoutId) {
        super(itemList, 0);
        recyclerAdapters = new HashMap<>();
        ViewId = CompoundViewLayoutId;
    }

    /**
     * registers the given adapter and associates it with the view ID it shall manage
     * @param adapter
     * @param viewId
     */
    public void registerAdapter(BaseRecyclerAdapter<T> adapter, Integer viewId){
        recyclerAdapters.put(adapter, viewId);
    }

    @NonNull
    @Override
    public ViewHolderInterface<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {

        // inflate configured compound ViewGroup
        ExpandableView compoundView = (ExpandableView) LayoutInflater.from(viewGroup.getContext()).inflate(ViewId,viewGroup,false);
        // create compound view holder
        CompoundViewHolder<T> compoundVH = new CompoundViewHolder<>(compoundView);
        TreeMap<BaseRecyclerAdapter<T>, Integer> sorted = new TreeMap<>(recyclerAdapters);
        for (BaseRecyclerAdapter<T> adapter : sorted.keySet()) {
            try {
                // for each adapter, create the view based on the given type
                ViewHolderInterface<T> adapterDefVH = adapter.onCreateViewHolder(compoundView, type);
                // finally add it to the compound VH
                compoundVH.addViewHolderInterface(adapterDefVH,adapterDefVH.getClass());
                // replace the view associated with the current adapter with the one the adapter inflated
                ((ViewGroup)compoundView.findViewById(recyclerAdapters.get(adapter))).addView(adapterDefVH.itemView);
                // remove the placeholder view
//                compoundView.removeViewAt(compoundView.indexOfChild(compoundView.findViewById(recyclerAdapters.get(adapter))));
            }
            catch (Exception ex) {
                Log.e(LOG_TAG, "failed to create ViewHolder for " + adapter.getClass().getSimpleName(), ex);
            }
        }
        return compoundVH;
    }

    @Override
    public void clear() {
        super.clear();
        for (BaseRecyclerAdapter<T> adapter:recyclerAdapters.keySet()) {
            adapter.clear();
        }
    }

    @Override
    public void remove(T object) {
        super.remove(object);
        for (BaseRecyclerAdapter<T> adapter:recyclerAdapters.keySet()) {
            adapter.remove(object);
        }
    }

    @Override
    public void replace(List<T> newList) {
        super.replace(newList);
        for (BaseRecyclerAdapter<T> adapter:recyclerAdapters.keySet()) {
            adapter.replace(newList);
        }
    }

    /**
     * adds all items from the list to the adapter
     *
     * @param toAdd list of items to Add
     */
    @Override
    void addAll(List<T> toAdd) {
        super.addAll(toAdd);
        for (BaseRecyclerAdapter<T> adapter:recyclerAdapters.keySet()) {
            adapter.addAll(toAdd);
        }
    }

    /**
     * add an object to the adapter to be displayed
     * <p>
     * will not be filtered or sorted afterwards!
     *
     * @param toAdd storable that is to be added
     */
    @Override
    public void add(T toAdd) {
        super.add(toAdd);
        for (BaseRecyclerAdapter<T> adapter:recyclerAdapters.keySet()) {
            adapter.add(toAdd);
        }
    }
}
