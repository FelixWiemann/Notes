package com.nepumuk.notizen.objects.storable_factory;

import com.nepumuk.notizen.objects.notes.TaskNote;
import com.nepumuk.notizen.utils.db_access.DatabaseStorable;

import java.util.ArrayList;
import java.util.UUID;

public class DefaultTaskNoteStrategy implements DefaultStorableStrategy {
    private static final DefaultTaskNoteStrategy strategy = new DefaultTaskNoteStrategy();

    @Override
    public DatabaseStorable createDefault() {
        return new TaskNote(UUID.randomUUID(),"",new ArrayList<>());
    }

    public static DatabaseStorable create(){
        return strategy.createDefault();
    }
}
