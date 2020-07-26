package com.nepumuk.notizen.objects.filtersort;

import android.support.annotation.NonNull;

import com.nepumuk.notizen.objects.Task.cBaseTask;

public class FilterHideDone extends ViewFilter<cBaseTask> {
    /**
     * return true, if the object needs to be displayed, false otherwise
     * default behaviour is to show all existing ones.
     *
     * @param toFilter Object that shall be decided
     * @return true, if it should be shown, false if filtered out
     */
    @Override
    public boolean filter(@NonNull cBaseTask toFilter) {
        return !toFilter.isDone();
    }
}
