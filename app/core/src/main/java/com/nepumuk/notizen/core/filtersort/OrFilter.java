package com.nepumuk.notizen.core.filtersort;

import com.nepumuk.notizen.core.objects.SortableObject;

import java.util.ArrayList;
import java.util.List;

public class OrFilter<T extends SortableObject> extends FilterQueue<T>{

    /**
     * filter a list into the given lists, display and hide.
     *
     * based on the current filter set, each item in the toFilter list is being
     * put into the display or hide list.
     * an item must pass one of the filters to be considered for the keep list
     *
     * @param toFilter list that needs to be filtered
     * @param keep items that passed the filter
     * @param discard items that where filtered out
     */
    @Override
    public void filter(List<T> toFilter, List<T> keep, List<T> discard){
        ArrayList<T> toFilterTmp = new ArrayList<>(toFilter);
        ArrayList<T> discardTmp = new ArrayList<>(discard);
        for (ViewFilter<T> filter: filters) {
            filter.filter(toFilterTmp,keep,discardTmp);
            toFilterTmp.clear();
            toFilterTmp.addAll(discardTmp);
            discardTmp.clear();
        }
        discard.addAll(toFilterTmp);
    }
}
