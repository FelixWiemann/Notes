package com.nepumuk.notizen.textnotes;


import com.nepumuk.notizen.core.objects.storable_factory.DefaultStorableStrategy;
import com.nepumuk.notizen.textnotes.objects.TextNote;

import java.util.UUID;

/**
 * {@link DefaultStorableStrategy} that returns a new TextNote with no title and message
 */
public class DefaultTextNoteStrategy implements DefaultStorableStrategy<TextNote> {
    public static final String DEFAULT_MESSAGE = "";

    private static final DefaultTextNoteStrategy strategy = new DefaultTextNoteStrategy();

    @Override
    public TextNote createDefault() {
        return new TextNote(UUID.randomUUID(), DefaultStorableStrategy.DEFAULT_TITLE,DEFAULT_MESSAGE);
    }

    public static TextNote create(){
        return strategy.createDefault();
    }
}
