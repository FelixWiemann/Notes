package com.nepumuk.notizen.objects.filtersort;

import com.nepumuk.notizen.objects.Task.cBaseTask;
import com.nepumuk.notizen.objects.cSortableObject;
import com.nepumuk.notizen.objects.cStorageObject;

import java.util.Comparator;

public class SortProvider {

    private static final int GREATER = 1;
    private static final int SMALLER = -1;
    private static final int EQUALS = 0;

    /**
     * Comparator for sorting the title ascending
     *
     * Compares its two arguments for order. Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
     */
    public static final Comparator<cSortableObject> SortByTitleAscending = new Comparator<cSortableObject>() {
        @Override
        public int compare(cSortableObject t1, cSortableObject t2) {
            // invert sort result descending
            return SortByTitleDescending.compare(t1, t2) * -1;
        }
    };

    public static final Comparator<cSortableObject> SortByTitleDescending = new Comparator<cSortableObject>() {
        @Override
        public int compare(cSortableObject t1, cSortableObject t2) {
            if (t1 == null && t2 == null){
                // both are null, so they are equal
                return EQUALS;
            }
            if (t1 == null){
                // t1 is null, so t2 is greater
                return GREATER;
            }
            if (t2 == null){
                // t2 is null, so t2 is smaller
                return SMALLER;
            }
            return (t2.getSortable(SortCategory.TITLE)).compareToIgnoreCase((t1.getSortable(SortCategory.TITLE)));
        }
    };

    public static final Comparator<cStorageObject> SortByType = new Comparator<cStorageObject>() {
        @Override
        public int compare(cStorageObject t1, cStorageObject t2) {
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
            return t1.getClass().getCanonicalName().compareTo(t2.getClass().getCanonicalName());
        }
    };

    public static final Comparator<cBaseTask> SortTasksDone = new Comparator<cBaseTask>() {
        @Override
        public int compare(cBaseTask t1, cBaseTask t2) {
            if (t1 == null && t2 == null){
                // both are null, so they are equal
                return EQUALS;
            }
            if (t1 == null){
                // t1 is null, so t2 is greater
                return GREATER;
            }
            if (t2 == null){
                // t2 is null, so t2 is smaller
                return SMALLER;
            }
            return Boolean.compare(t1.isDone(), t2.isDone());
        }
    };

}
