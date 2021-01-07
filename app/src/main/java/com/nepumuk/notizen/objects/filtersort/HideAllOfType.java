package com.nepumuk.notizen.objects.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.objects.SortableObject;

public class HideAllOfType<T extends SortableObject> extends ViewFilter<T>{

    protected final String canonicalClassName;

    public HideAllOfType(Class<?> clazz) {
        super();
        canonicalClassName = clazz.getCanonicalName();
    }

    @Override
    public boolean filter(@NonNull T toFilter) {
        return !canonicalClassName.equals(toFilter.getClass().getCanonicalName());
    }
}
