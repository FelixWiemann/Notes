package com.nepumuk.notizen.tasks.filtersort;

import com.nepumuk.notizen.core.filtersort.Sorter;
import com.nepumuk.notizen.tasks.objects.BaseTask;

public class SortProvider {
    /**
     * sorter for sorting by task done state
     * </p><p> compares using {@link Boolean#compare(boolean, boolean)}
     * </p><p>
     * </p><p> Note: this comparator imposes orderings that are inconsistent with equals.
     */
    public static final Sorter<BaseTask> SortTasksDone =
            (t1, t2) -> Boolean.compare(t1.isDone(), t2.isDone());

}
