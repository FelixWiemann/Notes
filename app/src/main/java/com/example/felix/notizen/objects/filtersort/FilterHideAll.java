package com.example.felix.notizen.objects.filtersort;

import android.support.annotation.NonNull;

import com.example.felix.notizen.objects.cSortableObject;

public class FilterHideAll<T extends cSortableObject> extends ViewFilter<T> {

    @Override
    public boolean filter(@NonNull T toFilter) {
        // as all should be hidden, just return false
        return false;
    }
}
