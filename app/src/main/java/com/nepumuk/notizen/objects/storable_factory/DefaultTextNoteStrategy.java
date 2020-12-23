package com.nepumuk.notizen.objects.storable_factory;


import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.objects.notes.TextNote;

import java.util.UUID;

/**
 * {@link DefaultStorableStrategy} that returns a new TextNote with no title and message
 */
public class DefaultTextNoteStrategy implements DefaultStorableStrategy {

    private static final DefaultTextNoteStrategy strategy = new DefaultTextNoteStrategy();

    @Override
    public DatabaseStorable createDefault() {
        return new TextNote(UUID.randomUUID(),"","");
    }

    public static DatabaseStorable create(){
        return strategy.createDefault();
    }
}
