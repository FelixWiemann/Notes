package com.nepumuk.notizen.views.adapters.view_holders;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.views.ExpandableView;

import java.util.HashMap;

/**
 * View Holder to combine many
 * @param <T>
 */
public class CompoundViewHolder<T extends StorageObject> extends ViewHolderInterface<T> {

    public boolean invertWasClicked = false;

    private final HashMap<Class,ViewHolderInterface<T>> interfaces;


    public CompoundViewHolder(@NonNull ExpandableView itemView) {
        super(itemView);
        interfaces = new HashMap<>();
        itemView.setOnExpandChangeListener(newState -> invertWasClicked = true);
    }

    @Override
    public void bind(T toBind) {
        for (ViewHolderInterface<T> vhf: interfaces.values()) {
            vhf.bind(toBind);
        }
        ((ExpandableView)itemView).resetExpandState();
    }

    public void addViewHolderInterface(ViewHolderInterface<T> viewHolderInterface, Class clazz){
        interfaces.put(clazz, viewHolderInterface);
    }

    public ViewHolderInterface<T> getViewHolder(Class clazz){
        return interfaces.get(clazz);
    }

    public ViewHolderInterface[] getViewHolders(float dX){
        ViewHolderInterface[] array = new ViewHolderInterface[interfaces.size()];
        for (int i = 0; i < array.length; i++) {
            if (interfaces.values().toArray()[i] instanceof SwipableViewHolder) {
                SwipableViewHolder swipableHolder = (SwipableViewHolder) interfaces.values().toArray()[i];
                swipableHolder.setBackgroundVisibility(dX);
                array[i] = swipableHolder.viewHolderInterface;
            }else {
                array[i] = (ViewHolderInterface<T>) interfaces.values().toArray()[i];
            }
        }
        return array;
    }
}
