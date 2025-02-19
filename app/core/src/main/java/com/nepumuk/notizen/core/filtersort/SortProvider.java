package com.nepumuk.notizen.core.filtersort;

import com.nepumuk.notizen.core.objects.StorageObject;

public class SortProvider {

    /**<p>
     * Sorter for sorting the title descending
     * </p><p> Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
     * </p><p>
     * </p><p> Note: this comparator imposes orderings that are inconsistent with equals.
     */
    public static final Sorter<StorageObject> SortByTitleDescending =
            (t1, t2) -> (t2.getSortable(SortCategory.TITLE)).compareToIgnoreCase((t1.getSortable(SortCategory.TITLE)));

    /**
     * sorter for sorting the title ascending
     *
     * reverses {@link SortProvider#SortByTitleDescending}
     * </p><p>
     * </p><p> Note: this comparator imposes orderings that are inconsistent with equals.
     */
    public static final Sorter<StorageObject> SortByTitleAscending =
            (t1,t2)->(SortByTitleDescending.compare(t2,t1));

    /**
     * sorter for sorting by type
     * </p><p>compares the canonical names of the given objects
     * </p><p>
     * </p><p> Note: this comparator imposes orderings that are inconsistent with equals.
     */
    public static final Sorter<StorageObject> SortByType =
            (t1, t2) -> t1.getClass().getCanonicalName().compareTo(t2.getClass().getCanonicalName());

    public static final Sorter<StorageObject> SortByTypeInverted =
            (t1, t2) -> SortByType.compareTypeSpec(t2, t1);


    /**SortByCreateDateAscending
     */
    public static final Sorter<StorageObject> SortByCreateDateAscending =
            (t1, t2) -> Long.compare(t1.getCreationDate(), t2.getCreationDate());

    public static final Sorter<StorageObject> SortByCreateDateDescending=
            (t1, t2) -> Long.compare(t2.getCreationDate(), t1.getCreationDate());

    /**
     */
    public static final Sorter<StorageObject> SortByLastChangeDateAscending =
            (t1, t2) -> Long.compare(t1.getLastChangedDate(),t2.getLastChangedDate());

    /**
     */
    public static final Sorter<StorageObject> SortByLastChangeDateDescending =
            (t1, t2) -> Long.compare(t2.getLastChangedDate(),t1.getLastChangedDate());

}
