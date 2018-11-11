package com.example.felix.notizen.objects.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.felix.notizen.R;
import com.example.felix.notizen.objects.Notes.cTextNote;

/**
 * Created by Felix on 11.11.2018.
 */

public class cNoteView extends cAbstractAdditionalView implements abstractionInterface {

    private cTextNote note;

    public cNoteView(Context context) {
        super(context);
    }
    public cNoteView(Context context, cTextNote object) {
        super(context);
        note = object;
    }

    @Override
    public void initView(AttributeSet attrs, int defStyle) {
        LayoutInflater mInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.note_view, this);
        ((TextView)findViewById(R.id.note_view_tv)).setText(note.getTitle());
    }

    @Override
    public void setExtended(boolean isExtended) {

    }
}
