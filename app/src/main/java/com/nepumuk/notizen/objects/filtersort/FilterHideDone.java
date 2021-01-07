package com.nepumuk.notizen.objects.filtersort;

import androidx.annotation.NonNull;

import com.nepumuk.notizen.objects.SortableObject;
import com.nepumuk.notizen.objects.tasks.BaseTask;

public class FilterHideDone<T extends SortableObject> extends ViewFilter<T> {
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

    @Override
    public boolean filter(@NonNull T toFilter) {
        if (toFilter instanceof BaseTask) {
            // if we run here but we have a BaseTask return the result of that check
            return filter((BaseTask) toFilter);
        }
        return true; // not of Type BaseTask -> do not hide
    }
}
