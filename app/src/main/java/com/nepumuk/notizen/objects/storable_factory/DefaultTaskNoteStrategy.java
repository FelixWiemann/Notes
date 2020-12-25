package com.nepumuk.notizen.objects.storable_factory;

import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.objects.tasks.BaseTask;

import java.util.ArrayList;
import java.util.UUID;

public class DefaultTaskNoteStrategy implements DefaultStorableStrategy<TaskNote> {
    public static final ArrayList<BaseTask> DEFAULT_LIST = new ArrayList<>();

    private static final DefaultTaskNoteStrategy strategy = new DefaultTaskNoteStrategy();

    @Override
    public TaskNote createDefault() {
        // use default list, but create copy to not have same list all over the place
        return new TaskNote(UUID.randomUUID(),"",new ArrayList<>(DEFAULT_LIST));
    }

    public static TaskNote create(){
        return strategy.createDefault();
    }
}
