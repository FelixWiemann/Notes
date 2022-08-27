package com.nepumuk.notizen.core.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.objects.StorageObject;

import java.util.Hashtable;

public class ShowAllOfType<T extends StorageObject> extends ViewFilter<T> {

    public static Hashtable<Integer, ShowAllOfType<StorageObject>> availableFilters = new Hashtable<>();

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
