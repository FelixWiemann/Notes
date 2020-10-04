package com.nepumuk.notizen.views.adapters.view_holders;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.notes.TextNote;
import com.nepumuk.notizen.utils.ResourceManger;

public class TextNoteViewHolder extends ViewHolderInterface<TextNote> {

    private final TextView message ;

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
