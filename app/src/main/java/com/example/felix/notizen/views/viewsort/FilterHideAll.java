package com.example.felix.notizen.views.viewsort;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

public class FilterHideAll extends ViewFilter {

    private static FilterHideAll instance = new FilterHideAll();

    public static FilterHideAll getInstance(){
        return instance;
    }

    @Override
    public boolean filter(DatabaseStorable toFilter) {
        // as all should be hidden, just return false
        return false;
    }
}
