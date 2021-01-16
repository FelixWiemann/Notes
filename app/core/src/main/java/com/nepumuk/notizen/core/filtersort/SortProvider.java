package com.nepumuk.notizen.core.filtersort;

import com.nepumuk.notizen.core.objects.SortableObject;
import com.nepumuk.notizen.core.objects.StorageObject;

public class SortProvider {

    /**<p>
     * Sorter for sorting the title descending
     * </p><p> Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
     * </p><p>
     * </p><p> Note: this comparator imposes orderings that are inconsistent with equals.
     */
    public static final Sorter<SortableObject> SortByTitleDescending =
            (t1, t2) -> (t2.getSortable(SortCategory.TITLE)).compareToIgnoreCase((t1.getSortable(SortCategory.TITLE)));

    /**
     * sorter for sorting the title ascending
     *
     * reverses {@link SortProvider#SortByTitleDescending}
     * </p><p>
     * </p><p> Note: this comparator imposes orderings that are inconsistent with equals.
     */
    public static final Sorter<SortableObject> SortByTitleAscending =
            (t1,t2)->(SortByTitleDescending.compare(t2,t1));

    /**
     * sorter for sorting by type
     * </p><p>compares the canonical names of the given objects
     * </p><p>
     * </p><p> Note: this comparator imposes orderings that are inconsistent with equals.
     */
    public static final Sorter<StorageObject> SortByType =
            (t1, t2) -> t1.getClass().getCanonicalName().compareTo(t2.getClass().getCanonicalName());


}
