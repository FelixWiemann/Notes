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
public abstract class FilterQueue <T extends SortableObject> extends ViewFilter<T> {

    protected LinkedHashSet<ViewFilter<T>> filters;

    public FilterQueue() {
        super();
        filters = new LinkedHashSet<>();
    }

    /**
     * adds a filter and returns the current instance
     * @param newFilter
     * @return
     */
    public <F extends FilterQueue<T>> F appendFilter(ViewFilter<T> newFilter){
        filters.add(newFilter);
        return (F) this;
    }

    /**
     * calls each filter based added to this FilterQueue.
     * based on the implementation of the inherited class, it will be decided whether to keep or discard the item
     * If after a call to {@link FilterQueue#filter(List, List, List)} the item is in the keep list, it will be returned as "keep"
     * @param toFilter Object that shall be decided
     */
    @Override
    public boolean filter(@NonNull T toFilter) {
        ArrayList<T> filterList = new ArrayList<>();
        filterList.add(toFilter);
        ArrayList<T> keep = new ArrayList<>();
        ArrayList<T> discard = new ArrayList<>();
        filter(filterList,keep,discard);
        return keep.contains(toFilter);
    }

    /**
     * filter a list into the given lists, display and hide.
     *
     * based on the current filter set, each item in the toFilter list is being
     * put into the display or hide list.
     *
     * Classes inheriting from {@link FilterQueue} needs to have a own implementation, otherwise we get loop with {@link FilterQueue#filter(SortableObject)}
     * 
     * @param toFilter list that needs to be filtered
     * @param keep items that passed the filter
     * @param discard items that where filtered out
     *
     */
    public abstract void filter(List<T> toFilter, List<T> keep, List<T> discard);

}
