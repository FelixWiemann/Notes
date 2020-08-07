package com.nepumuk.notizen.objects.storable_factory;


import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.objects.notes.TextNote;

import java.util.UUID;

/**
 * {@link DefaultStorableStrategy} that returns a new TextNote with no title and message
 */
public class DefaultTextNoteStrategy implements DefaultStorableStrategy {
    @Override
    public DatabaseStorable createDefault() {
        return new TextNote(UUID.randomUUID(),"","");
    }
}
