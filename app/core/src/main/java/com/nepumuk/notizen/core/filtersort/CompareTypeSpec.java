package com.nepumuk.notizen.core.filtersort;

import com.nepumuk.notizen.core.objects.SortableObject;

public interface CompareTypeSpec<T extends SortableObject>{
    int compareTypeSpec(T t1, T T2);
}
