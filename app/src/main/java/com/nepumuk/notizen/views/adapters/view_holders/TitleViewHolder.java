package com.nepumuk.notizen.views.adapters.view_holders;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.StorageObject;
import com.nepumuk.notizen.utils.ResourceManger;

public class TitleViewHolder<T extends StorageObject> extends ViewHolderInterface<T> {

    TextView title;

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
