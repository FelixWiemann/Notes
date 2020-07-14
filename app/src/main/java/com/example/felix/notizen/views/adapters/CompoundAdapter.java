package com.example.felix.notizen.views.adapters;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.views.adapters.ViewHolders.CompoundViewHolder;
import com.example.felix.notizen.views.adapters.ViewHolders.ViewHolderInterface;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class CompoundAdapter<T extends DatabaseStorable> extends SortableRecyclerAdapter<T> {

    private static final String LOG_TAG = "CompoundAdapter";
    /**
     * List of adapters and the view they need to fill
     */
    HashMap<BaseRecyclerAdapter<T>, Integer> recyclerAdapters;

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
        ViewGroup compoundView = (ViewGroup) LayoutInflater.from(viewGroup.getContext()).inflate(ViewId,viewGroup,false);
        // create compound view holder
        CompoundViewHolder compoundVH = new CompoundViewHolder<>(compoundView);
        TreeMap<BaseRecyclerAdapter<T>, Integer> sorted = new TreeMap<>(recyclerAdapters);
        for (BaseRecyclerAdapter adapter : sorted.keySet()) {
            try {
                // for each adapter, create the view based on the given type
                ViewHolderInterface adapterDefVH = adapter.onCreateViewHolder(compoundView, type);
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
}
