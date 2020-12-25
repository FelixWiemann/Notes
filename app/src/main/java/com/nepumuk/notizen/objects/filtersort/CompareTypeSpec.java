package com.nepumuk.notizen.objects.filtersort;

import com.nepumuk.notizen.objects.SortableObject;

public interface CompareTypeSpec<T extends SortableObject>{
    int compareTypeSpec(T t1, T T2);
}
