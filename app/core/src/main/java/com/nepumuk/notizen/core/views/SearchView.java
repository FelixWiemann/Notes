package com.nepumuk.notizen.core.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.nepumuk.notizen.core.R;
import com.nepumuk.notizen.core.utils.ResourceManger;

public class SearchView extends android.widget.SearchView {

    public SearchWatcher watcher;

    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        setQueryHint(ResourceManger.getString(R.string.hint_search_notes));
        setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (watcher!=null){
                    watcher.search(s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (watcher!=null){
                    watcher.search(s);
                }
                return false;
            }
        });

    }

    public interface SearchWatcher{
        void search(String phrase);
    }

}
