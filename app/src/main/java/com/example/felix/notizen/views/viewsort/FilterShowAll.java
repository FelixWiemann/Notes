package com.example.felix.notizen.views.viewsort;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

public class FilterShowAll extends ViewFilter {

    @Override
    public boolean filter(DatabaseStorable toFilter) {
        // as all should be shown, just return true
        return true;
    }
}
