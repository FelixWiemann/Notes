package com.nepumuk.notizen.views.adapters.view_holders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.notes.TextNote;

public class TextNoteViewHolder extends ViewHolderInterface<TextNote> {

    TextView message ;

    public TextNoteViewHolder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.note_view_tv);
    }

    @Override
    public void bind(TextNote toBind) {
        message.setText(toBind.getMessage());
    }
}
