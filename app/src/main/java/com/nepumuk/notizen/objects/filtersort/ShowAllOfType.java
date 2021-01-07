package com.nepumuk.notizen.objects.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.objects.SortableObject;

public class ShowAllOfType<T extends SortableObject> extends ViewFilter<T> {

    protected final String canonicalClassName;

    public ShowAllOfType(Class <?> clazz){
        super();
        this.canonicalClassName = clazz.getCanonicalName();
    }

    @Override
    public boolean filter(@NonNull T toFilter) {
        // make sure nothing is null & the given class in the constructor matches the class of the toFilter
        return canonicalClassName.equals(toFilter.getClass().getCanonicalName());
    }
}
