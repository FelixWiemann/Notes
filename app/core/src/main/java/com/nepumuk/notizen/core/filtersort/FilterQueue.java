package com.nepumuk.notizen.core.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.objects.SortableObject;

import java.util.LinkedHashSet;

/**
 * a {@link FilterQueue} is a queue for filters to be executed after each other.
 * added filters are guaranteed to be executed in order.
 * @param <T>
 */
public class FilterQueue <T extends SortableObject> extends ViewFilter<T> {

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
     * DO NOT CALL
     * @param toFilter Object that shall be decided
     */
    @Override
    public boolean filter(@NonNull T toFilter) {
        throw new IllegalArgumentException("Don't use the FilterQueue's filter!");
    }
}
