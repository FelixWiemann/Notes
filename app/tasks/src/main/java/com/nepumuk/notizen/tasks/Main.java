package com.nepumuk.notizen.tasks;

import com.nepumuk.notizen.core.objects.Migration;
import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderFactory;
import com.nepumuk.notizen.core.views.fragments.NoteDisplayFragmentFactory;
import com.nepumuk.notizen.tasks.objects.BaseTask;
import com.nepumuk.notizen.tasks.objects.TaskNote;

public class Main {
    public static void initModule(){
        ViewHolderFactory.registerNewViewHolder(R.layout.task_view, BaseTask.class, TaskViewHolder.class);
        ViewHolderFactory.registerNewViewHolder(R.layout.task_note_view, TaskNote.class, TaskNoteViewHolder.class);
        NoteDisplayFragmentFactory.addMapping(TaskNote.class, TaskNoteFragment.class);
        Migration.addMigrationService("com.nepumuk.notizen.objects.notes.TaskNote",new TaskNoteMigrationService(),1);
    }
}
