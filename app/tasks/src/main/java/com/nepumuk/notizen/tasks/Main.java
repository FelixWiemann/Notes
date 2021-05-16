package com.nepumuk.notizen.tasks;

import com.nepumuk.notizen.core.filtersort.TextFilter;
import com.nepumuk.notizen.core.objects.Migration;
import com.nepumuk.notizen.core.utils.ShortCutHelper;
import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderFactory;
import com.nepumuk.notizen.core.views.fragments.NoteDisplayFragmentFactory;
import com.nepumuk.notizen.tasks.objects.BaseTask;
import com.nepumuk.notizen.tasks.objects.DefaultTaskNoteStrategy;
import com.nepumuk.notizen.tasks.objects.Task;
import com.nepumuk.notizen.tasks.objects.TaskNote;

import static com.nepumuk.notizen.core.filtersort.TextFilter.SEP;

public class Main {
    public static void initModule(){
        ViewHolderFactory.registerNewViewHolder(R.layout.task_view, BaseTask.class, TaskViewHolder.class);
        ViewHolderFactory.registerNewViewHolder(R.layout.task_note_view, TaskNote.class, TaskNoteViewHolder.class);
        NoteDisplayFragmentFactory.addMapping(TaskNote.class, TaskNoteFragment.class);
        Migration.addMigrationService("com.nepumuk.notizen.objects.notes.TaskNote",new TaskNoteMigrationService(),1);
        ShortCutHelper.registerShortcut(new DefaultTaskNoteStrategy(),ShortCutHelper.ID_NEW_TASK_NOTE,"TaskNote");
        TextFilter.addMapping(TaskNote.class, object -> {
            StringBuilder builder = new StringBuilder(object.getTitle()).append(SEP);
            for (BaseTask task: object.getTaskList()) {
                builder.append(task.getTitle()).append(SEP).append(task.getText()).append(SEP);
            }
            return builder.toString();
        });
        TextFilter.addMapping(BaseTask.class, object -> object.getText() + SEP + object.getTitle());
        TextFilter.addMapping(Task.class, object -> object.getText() + SEP + object.getTitle());
    }
}
