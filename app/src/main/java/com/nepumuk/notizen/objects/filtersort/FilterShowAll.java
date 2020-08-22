package com.nepumuk.notizen.objects.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.objects.SortableObject;

public class FilterShowAll<T extends SortableObject> extends ViewFilter<T> {

    @Override
    public boolean filter(@NonNull T toFilter) {
        // as all should be shown, just return true
        return true;
    }
}
