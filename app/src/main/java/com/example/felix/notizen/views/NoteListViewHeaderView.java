package com.example.felix.notizen.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.felix.notizen.R;

public class NoteListViewHeaderView extends RelativeLayout {
    public NoteListViewHeaderView(Context context) {
        super(context);
        init();
    }

    public NoteListViewHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NoteListViewHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public NoteListViewHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(this.getContext()).inflate(R.layout.list_header, this);
        ((TextView) getRootView().findViewById(R.id.header)).setText(R.string.list_view_header_text);
        update(0);
    }

    public void update(int numberOfNotes){
        ((TextView) findViewById(R.id.amount)).setText(getResources().getQuantityString(R.plurals.notes,numberOfNotes,numberOfNotes));
    }


}
