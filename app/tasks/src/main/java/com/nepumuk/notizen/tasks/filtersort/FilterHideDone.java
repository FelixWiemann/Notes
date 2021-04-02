package com.nepumuk.notizen.tasks.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.core.filtersort.ViewFilter;
import com.nepumuk.notizen.tasks.objects.BaseTask;

public class FilterHideDone extends ViewFilter<BaseTask> {
    /**
     * return true, if the object needs to be displayed, false otherwise
     * default behaviour is to show all existing ones.
     *
     * @param toFilter Object that shall be decided
     * @return true, if it should be shown, false if filtered out
     */
    public boolean filter(@NonNull BaseTask toFilter) {
        return !toFilter.isDone();
    }

}
