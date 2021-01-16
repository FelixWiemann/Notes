package com.nepumuk.notizen.core.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.objects.SortableObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * a {@link FilterQueue} is a queue for filters to be executed after each other.
 * added filters are guaranteed to be executed in order.
 * @param <T>
 */
public class FilterQueue<T extends SortableObject> extends ViewFilter<T>{

    protected LinkedHashSet<ViewFilter<T>> filters;

    public FilterQueue () {
        super();
        filters = new LinkedHashSet<>();
    }

    /**
     * adds a filter and returns the current instance
     * @param newFilter
     * @return
     */
    public FilterQueue<T> appendFilter(ViewFilter<T> newFilter){
        filters.add(newFilter);
        return this;
    }

    /**
     * DO NOT CALL
     * @param toFilter Object that shall be decided
     */
    @Override
    public boolean filter(@NonNull T toFilter) {
        throw new IllegalArgumentException("Don't use the FilterQueue's filter!");
    }


    /**
     * filter a list into the given lists, display and hide.
     *
     * based on the current filter set, each item in the toFilter list is being
     * put into the display or hide list.
     *
     * @param toFilter list that needs to be filtered
     * @param keep items that passed the filter
     * @param discard items that where filtered out
     */
    @Override
    public void filter(List<T> toFilter, List<T> keep, List<T> discard){
        ArrayList<T> toFilterTmp = new ArrayList<>(toFilter);
        ArrayList<T> keepTmp = new ArrayList<>(keep);
        for (ViewFilter<T> filter: filters) {
            filter.filter(toFilterTmp,keepTmp,discard);
            toFilterTmp.clear();
            toFilterTmp.addAll(keepTmp);
            keepTmp.clear();
        }
        keep.addAll(toFilterTmp);
    }

}
