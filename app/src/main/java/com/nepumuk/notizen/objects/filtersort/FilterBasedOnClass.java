package com.nepumuk.notizen.objects.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.objects.SortableObject;

public class FilterBasedOnClass extends ViewFilter {

    private final Class clazz;

    public FilterBasedOnClass(Class clazz){
        super();
        this.clazz = clazz;
    }

    @Override
    public boolean filter(@NonNull SortableObject toFilter) {
        // make sure nothing is null & the given class in the constructor matches the class of the toFilter
        return toFilter.getClass().getCanonicalName().equalsIgnoreCase(clazz.getCanonicalName());
    }
}
