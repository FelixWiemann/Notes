package com.nepumuk.notizen.objects.filtersort;

import com.nepumuk.notizen.objects.tasks.BaseTask;
import com.nepumuk.notizen.objects.SortableObject;
import com.nepumuk.notizen.objects.StorageObject;

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
    public static final Comparator<SortableObject> SortByTitleAscending = new Comparator<SortableObject>() {
        @Override
        public int compare(SortableObject t1, SortableObject t2) {
            // invert sort result descending
            return SortByTitleDescending.compare(t1, t2) * -1;
        }
    };

    public static final Comparator<SortableObject> SortByTitleDescending = new Comparator<SortableObject>() {
        @Override
        public int compare(SortableObject t1, SortableObject t2) {
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

    public static final Comparator<StorageObject> SortByType = new Comparator<StorageObject>() {
        @Override
        public int compare(StorageObject t1, StorageObject t2) {
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

    public static final Comparator<BaseTask> SortTasksDone = new Comparator<BaseTask>() {
        @Override
        public int compare(BaseTask t1, BaseTask t2) {
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
