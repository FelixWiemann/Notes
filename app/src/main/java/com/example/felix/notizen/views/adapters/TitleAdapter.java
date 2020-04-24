package com.example.felix.notizen.views.adapters;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.felix.notizen.objects.cStorageObject;
import com.example.felix.notizen.views.adapters.ViewHolders.TitleViewHolder;
import com.example.felix.notizen.views.adapters.ViewHolders.ViewHolderInterface;

import java.util.List;

public class TitleAdapter extends BaseRecyclerAdapter<cStorageObject> {

    public TitleAdapter(List<cStorageObject> itemList) {
        super(itemList);
    }

    @NonNull
    @Override
    public ViewHolderInterface<cStorageObject> onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {

        ViewHolderInterface viewHolderInterface = new TitleViewHolder(viewGroup);
        return viewHolderInterface;
    }
}
