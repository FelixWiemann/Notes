package com.example.felix.notizen.views.adapters.ViewHolders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Notes.cTextNote;

public class TextNoteViewHolder extends ViewHolderInterface<cTextNote> {

    TextView message ;

    public TextNoteViewHolder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.note_view_tv);
    }

    @Override
    public void bind(cTextNote toBind) {
        message.setText(toBind.getMessage());
    }
}
