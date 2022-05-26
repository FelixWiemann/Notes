package com.nepumuk.notizen.tasks.objects;


import com.nepumuk.notizen.core.objects.storable_factory.DefaultStorableStrategy;

import java.util.ArrayList;
import java.util.UUID;

public class DefaultTaskNoteStrategy implements DefaultStorableStrategy<TaskNote> {
    public static final ArrayList<BaseTask> DEFAULT_LIST = new ArrayList<>();

    private static final DefaultTaskNoteStrategy strategy = new DefaultTaskNoteStrategy();

    @Override
    public TaskNote createDefault() {
        // use default list, but create copy to not have same list all over the place
        return new TaskNote(UUID.randomUUID().toString(),"",new ArrayList<>(DEFAULT_LIST));
    }

    public static TaskNote create(){
        return strategy.createDefault();
    }
}
