package com.nepumuk.notizen.core.filtersort;

import androidx.annotation.Keep;

import com.nepumuk.notizen.core.objects.SortableObject;

import java.util.Comparator;

@Keep
public interface Sorter<T extends SortableObject> extends Comparator<T>, CompareTypeSpec<T> {

    int GREATER = 1;
    int SMALLER = -1;
    int EQUALS = 0;

    @Override
    default int compare(T t1, T t2) {
        if (t1 == null && t2 == null){
            // both are null, so they are equal
            return EQUALS;
        }
        if (t1 == null || t1.getClass().getCanonicalName() == null){
            // t1 is null, so t2 is greater
            return GREATER;
        }
        if (t2 == null || t2.getClass().getCanonicalName() == null){
            // t2 is null, so t2 is smaller
            return SMALLER;
        }
        return compareTypeSpec(t1,t2);
    }

}

