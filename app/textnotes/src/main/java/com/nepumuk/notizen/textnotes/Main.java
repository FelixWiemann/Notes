package com.nepumuk.notizen.textnotes;

import com.nepumuk.notizen.core.objects.Migration;
import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderFactory;
import com.nepumuk.notizen.core.views.fragments.NoteDisplayFragmentFactory;
import com.nepumuk.notizen.textnotes.objects.TextNote;

public class Main {

    public static void initModule(){
        ViewHolderFactory.registerNewViewHolder(R.layout.note_view, TextNote.class, TextNoteViewHolder.class);
        Migration.addMigrationService("com.nepumuk.notizen.objects.notes.TextNote",new TextNoteMigrationService(),1);
        NoteDisplayFragmentFactory.addMapping(TextNote.class, TextNoteFragment.class);
    }
}
