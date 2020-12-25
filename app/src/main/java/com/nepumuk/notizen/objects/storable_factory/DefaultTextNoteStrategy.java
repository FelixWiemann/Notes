package com.nepumuk.notizen.objects.storable_factory;

import com.nepumuk.notizen.objects.notes.TextNote;

import java.util.UUID;

/**
 * {@link DefaultStorableStrategy} that returns a new TextNote with no title and message
 */
public class DefaultTextNoteStrategy implements DefaultStorableStrategy<TextNote> {
    public static final String DEFAULT_MESSAGE = "";

    private static final DefaultTextNoteStrategy strategy = new DefaultTextNoteStrategy();

    @Override
    public TextNote createDefault() {
        return new TextNote(UUID.randomUUID(),DEFAULT_TITLE,DEFAULT_MESSAGE);
    }

    public static TextNote create(){
        return strategy.createDefault();
    }
}
