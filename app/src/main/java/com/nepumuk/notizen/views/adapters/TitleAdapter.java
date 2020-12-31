package com.nepumuk.notizen.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.views.adapters.view_holders.TitleViewHolder;
import com.nepumuk.notizen.views.adapters.view_holders.ViewHolderInterface;

import java.util.List;

public class TitleAdapter<T extends StorageObject> extends BaseRecyclerAdapter<T> {

    public TitleAdapter(List<T> itemList, int SortOrder) {
        super(itemList, SortOrder);
    }

    @NonNull
    @Override
    public ViewHolderInterface<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View titleView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.title_view,viewGroup,false);
        return new TitleViewHolder<>(titleView);
    }
}
