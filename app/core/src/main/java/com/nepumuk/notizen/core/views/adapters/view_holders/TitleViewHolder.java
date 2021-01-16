package com.nepumuk.notizen.core.views.adapters.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.R;
import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.utils.ResourceManger;

public class TitleViewHolder<T extends StorageObject> extends ViewHolderInterface<T> {

    private final TextView title;

    @Keep
    public TitleViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titleText);
    }

    @Override
    public void bind(T toBind) {
        title.setText(toBind.getTitle());
        title.setContentDescription(ResourceManger.getString(R.string.content_note_message) + " " + toBind.getTitle());
    }
}
