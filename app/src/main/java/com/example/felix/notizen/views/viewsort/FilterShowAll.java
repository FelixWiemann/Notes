package com.example.felix.notizen.views.viewsort;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

public class FilterShowAll implements ViewFilter {

    static FilterShowAll instance = new FilterShowAll();

    public static FilterShowAll getInstance(){
        return instance;
    }

    @Override
    public boolean filter(DatabaseStorable toFilter) {
        // as all should be shown, just return true
        return true;
    }
}
