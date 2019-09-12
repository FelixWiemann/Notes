package com.example.felix.notizen.views.viewsort;

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
            return ((String)t1.getSortable(SortCategory.TITLE)).compareToIgnoreCase(((String)t2.getSortable(SortCategory.TITLE)));
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
            return ((String)t2.getSortable(SortCategory.TITLE)).compareToIgnoreCase(((String)t1.getSortable(SortCategory.TITLE)));
        }
    };

}
