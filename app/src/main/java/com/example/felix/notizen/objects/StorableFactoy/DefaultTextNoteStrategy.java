package com.example.felix.notizen.objects.StorableFactoy;


import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.objects.Notes.cTextNote;

import java.util.UUID;

/**
 * {@link DefaultStorableStrategy} that returns a new TextNote with no title and message
 */
public class DefaultTextNoteStrategy implements DefaultStorableStrategy {
    @Override
    public DatabaseStorable createDefault() {
        return new cTextNote(UUID.randomUUID(),"","");
    }
}
