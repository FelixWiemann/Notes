package com.example.felix.notizen.objects.filtersort;

import android.support.annotation.NonNull;

import com.example.felix.notizen.objects.cSortableObject;

public class FilterShowAll<T extends cSortableObject> extends ViewFilter<T> {

    @Override
    public boolean filter(@NonNull T toFilter) {
        // as all should be shown, just return true
        return true;
    }
}
