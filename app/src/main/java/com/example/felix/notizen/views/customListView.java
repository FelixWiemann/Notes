package com.example.felix.notizen.views;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.Utils.DBAccess.cDBDataHandler;
import com.example.felix.notizen.Utils.Logger.cNoteLogger;
import com.example.felix.notizen.views.viewsort.FilterBasedOnClass;
import com.example.felix.notizen.views.viewsort.ViewFilter;

/**
 * Created by Felix on 21.06.2018.
 */


public class customListView extends ListView {

    // TODO move databaseinteractions to the adapter
    private cExpandableViewAdapter adapter = new cExpandableViewAdapter();

    private cDBDataHandler handler;

    public customListView(Context context) {
        super(context);
        init();
    }

    public customListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public customListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void add(DatabaseStorable storable){
        adapter.add(storable);
        adapter.notifyDataSetChanged();
    }

    public void init(){
        this.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        handler = new cDBDataHandler();
        this.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(customListView.super.getContext(), "long click", Toast.LENGTH_LONG).show();
                DatabaseStorable storable = adapter.getItem(position);
                adapter.remove(storable);
                handler.delete(storable);

                adapter.notifyDataSetChanged();
                return true;
            }
        });
        this.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(customListView.super.getContext(), "on item selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(customListView.super.getContext(), "on nothing selected", Toast.LENGTH_LONG).show();
            }
        });
        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(customListView.super.getContext(), "short click", Toast.LENGTH_LONG).show();
            }
        });
        this.setAdapter(adapter);
        cNoteLogger.getInstance().logInfo("customListview init");
    }

    public void filter(ViewFilter filter) {
        adapter.filter(filter);
    }
}
