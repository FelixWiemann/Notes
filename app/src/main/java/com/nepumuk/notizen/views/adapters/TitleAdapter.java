package com.nepumuk.notizen.views.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.cStorageObject;
import com.nepumuk.notizen.views.adapters.view_holders.TitleViewHolder;
import com.nepumuk.notizen.views.adapters.view_holders.ViewHolderInterface;

import java.util.List;

public class TitleAdapter extends BaseRecyclerAdapter<cStorageObject> {

    public TitleAdapter(List<cStorageObject> itemList, int SortOrder) {
        super(itemList, SortOrder);
    }

    @NonNull
    @Override
    public ViewHolderInterface<cStorageObject> onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View titleView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.title_view,viewGroup,false);
        return (ViewHolderInterface) new TitleViewHolder(titleView);
    }
}
