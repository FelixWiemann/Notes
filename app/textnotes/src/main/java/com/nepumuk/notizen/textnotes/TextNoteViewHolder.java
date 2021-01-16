package com.nepumuk.notizen.textnotes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderInterface;
import com.nepumuk.notizen.textnotes.objects.TextNote;
import com.nepumuk.notizen.core.utils.ResourceManger;

public class TextNoteViewHolder extends ViewHolderInterface<TextNote> {

    private final TextView message ;

    @Keep
    public TextNoteViewHolder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.note_view_tv);
    }

    @Override
    public void bind(TextNote toBind) {
        message.setText(toBind.getMessage());
        message.setContentDescription(ResourceManger.getString(R.string.content_note_message) + " " + toBind.getTitle() + ": " + toBind.getMessage());
    }
}
