package com.nepumuk.notizen.views.adapters.ViewHolders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.cStorageObject;

public class TitleViewHolder<T extends cStorageObject> extends ViewHolderInterface<T> {

    TextView title;

    public TitleViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titleText);
    }

    @Override
    public void bind(T toBind) {
        title.setText(toBind.getTitle());
    }
}
