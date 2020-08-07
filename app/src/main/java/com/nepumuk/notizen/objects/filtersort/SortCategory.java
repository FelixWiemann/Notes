package com.nepumuk.notizen.objects.filtersort;

/**
 * category on which the objects can be sorted on
 *
 * T is the expected return values type
 * @param <T>
 */
public class SortCategory<T>{
    public static final SortCategory<String> TITLE = new SortCategory<>();
    public static final SortCategory<String> TEXT = new SortCategory<>();
    public static final SortCategory<Long> CREATION_TIME = new SortCategory<>();
    public static final SortCategory<Long> LAST_CHANGE_TIME = new SortCategory<>();
    public static final SortCategory<Long> TASK_DONE_TIME = new SortCategory<>();
    public static final SortCategory<Boolean> TASK_DONE_STATE = new SortCategory<>();
}

