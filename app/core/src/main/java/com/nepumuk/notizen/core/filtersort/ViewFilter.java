package com.nepumuk.notizen.core.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.objects.SortableObject;

import java.util.List;


/**
 * filter class that can be used to filter items
 */
public abstract class ViewFilter <T extends SortableObject>{
    /**
     * return true, if the object needs to be displayed, false otherwise
     * default behaviour is to show all existing ones.
     *
     * @param toFilter Object that shall be decided
     * @return true, if it should be shown, false if filtered out
     */
    public abstract boolean filter(@NonNull T toFilter);

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
    public void filter(List<T> toFilter, List<T> keep, List<T> discard){
        for (T storable: toFilter) {
            if (filter(storable)){
                keep.add(storable);
            }else{
                discard.add(storable);
            }
        }
    }



}
