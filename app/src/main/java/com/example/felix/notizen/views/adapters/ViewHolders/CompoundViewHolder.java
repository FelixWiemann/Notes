package com.example.felix.notizen.views.adapters.ViewHolders;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.felix.notizen.objects.cStorageObject;

import java.util.HashMap;

public class CompoundViewHolder<T extends cStorageObject> extends ViewHolderInterface<T> {

    private HashMap<Class,ViewHolderInterface<T>> interfaces;


    public CompoundViewHolder(@NonNull View itemView) {
        super(itemView);
        interfaces = new HashMap<>();
    }

    @Override
    public void bind(T toBind) {
        for (ViewHolderInterface<T> vhf: interfaces.values()) {
            vhf.bind(toBind);
        }
    }

    public void addViewHolderInterface(ViewHolderInterface<T> viewHolderInterface, Class clazz){
        interfaces.put(clazz, viewHolderInterface);
    }

    public ViewHolderInterface<T> getViewHolder(Class clazz){
        return interfaces.get(clazz);
    }
}
