package com.nepumuk.notizen.objects.filtersort;

import android.support.annotation.NonNull;

import com.nepumuk.notizen.objects.tasks.BaseTask;

public class FilterHideDone extends ViewFilter<BaseTask> {
    /**
     * return true, if the object needs to be displayed, false otherwise
     * default behaviour is to show all existing ones.
     *
     * @param toFilter Object that shall be decided
     * @return true, if it should be shown, false if filtered out
     */
    @Override
    public boolean filter(@NonNull BaseTask toFilter) {
        return !toFilter.isDone();
    }
}
