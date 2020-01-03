package com.example.felix.notizen.views.viewsort;

import com.example.felix.notizen.objects.Task.cBaseTask;

public class FilterHideDone extends ViewFilter<cBaseTask> {
    /**
     * return true, if the object needs to be displayed, false otherwise
     * default behaviour is to show all existing ones.
     *
     * @param toFilter Object that shall be decided
     * @return true, if it should be shown, false if filtered out
     */
    @Override
    public boolean filter(cBaseTask toFilter) {
        return !toFilter.isDone();
    }
}
