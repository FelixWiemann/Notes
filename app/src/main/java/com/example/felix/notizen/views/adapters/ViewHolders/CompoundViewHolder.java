package com.example.felix.notizen.views.adapters.ViewHolders;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.felix.notizen.objects.cStorageObject;

import java.util.ArrayList;

public class CompoundViewHolder<T extends cStorageObject> extends ViewHolderInterface<T> {

    private ArrayList<ViewHolderInterface<T>> interfaces;


    public CompoundViewHolder(@NonNull View itemView) {
        super(itemView);
        interfaces = new ArrayList<>();
    }

    @Override
    public void bind(T toBind) {
        for (ViewHolderInterface<T> vhf: interfaces) {
            vhf.bind(toBind);
        }
    }

    public void addViewHolderInterface(ViewHolderInterface viewHolderInterface){
        interfaces.add(viewHolderInterface);
    }
}
