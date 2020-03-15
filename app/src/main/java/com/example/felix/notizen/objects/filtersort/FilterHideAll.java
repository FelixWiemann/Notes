package com.example.felix.notizen.objects.filtersort;

import android.support.annotation.NonNull;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

public class FilterHideAll<T extends DatabaseStorable> extends ViewFilter<T> {

    @Override
    public boolean filter(@NonNull T toFilter) {
        // as all should be hidden, just return false
        return false;
    }
}
