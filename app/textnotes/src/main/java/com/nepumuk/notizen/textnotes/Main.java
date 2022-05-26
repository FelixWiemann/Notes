package com.nepumuk.notizen.textnotes;

import com.nepumuk.notizen.core.filtersort.TextFilter;
import com.nepumuk.notizen.core.objects.Migration;
import com.nepumuk.notizen.core.utils.ShortCutHelper;
import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderFactory;
import com.nepumuk.notizen.core.views.fragments.NoteDisplayFragmentFactory;
import com.nepumuk.notizen.textnotes.objects.TextNote;

public class Main {

    public static void initModule(){
        ViewHolderFactory.registerNewViewHolder(R.layout.note_view, TextNote.class, TextNoteViewHolder.class);
        Migration.addMigrationService("com.nepumuk.notizen.objects.notes.TextNote",new TextNoteMigrationService(),1);
        NoteDisplayFragmentFactory.addMapping(TextNote.class, TextNoteFragment.class);
        ShortCutHelper.registerShortcut(new DefaultTextNoteStrategy(),ShortCutHelper.ID_NEW_TEXT_NOTE,"TextNote");
        TextFilter.addMapping(TextNote.class, (object, text) -> {
            // text in title
            if(object.getTitle().toLowerCase().contains(text)) return true;
            // text in message
            if(object.getMessage().toLowerCase().contains(text)) return true;
            return false;
        });

        //AppDataBaseHelper.registerDatabase(TextNoteDatabase.class);
        //AppDataBaseHelper.getInstance().getDatabase(TextNoteDatabase.class).getTextNoteDao().createOrUpdate(new TextNote(UUID.randomUUID().toString(), "testtitle", "testmessage"));
    }
}
