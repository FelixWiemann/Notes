package com.example.felix.notizen.objects.filtersort;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.objects.cSortableObject;

import java.util.Comparator;

public class SortProvider {

    /**
     * Comparator for sorting the title ascending
     */
    public static final Comparator<cSortableObject> SortByTitleAscending = new Comparator<cSortableObject>() {
        @Override
        public int compare(cSortableObject t1, cSortableObject t2) {
            if (t1 == null){
                return -1;
            }
            if (t2 == null){
                return 1;
            }
            return (t1.getSortable(SortCategory.TITLE)).compareToIgnoreCase((t2.getSortable(SortCategory.TITLE)));
        }
    };

    public static final Comparator<cSortableObject> SortByTitleDescending = new Comparator<cSortableObject>() {
        @Override
        public int compare(cSortableObject t1, cSortableObject t2) {
            if (t1 == null){
                return 1;
            }
            if (t2 == null){
                return -1;
            }
            return (t2.getSortable(SortCategory.TITLE)).compareToIgnoreCase((t1.getSortable(SortCategory.TITLE)));
        }
    };

    public static final Comparator<DatabaseStorable> SortByType = new Comparator<DatabaseStorable>() {
        @Override
        public int compare(DatabaseStorable cSortableObject, DatabaseStorable t1) {
            return cSortableObject.getClass().getCanonicalName().compareTo(t1.getClass().getCanonicalName());
        }
    };

    public static final Comparator<cBaseTask> SortTasksDone = new Comparator<cBaseTask>() {
        @Override
        public int compare(cBaseTask t1, cBaseTask t2) {
            return Boolean.compare(t1.isDone(), t2.isDone());
        }
    };

}
