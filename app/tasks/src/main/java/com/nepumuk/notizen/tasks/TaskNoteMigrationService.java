package com.nepumuk.notizen.tasks;

import com.nepumuk.notizen.core.objects.Migration;
import com.nepumuk.notizen.core.objects.MigrationService;
import com.nepumuk.notizen.tasks.objects.TaskNote;

public class TaskNoteMigrationService implements MigrationService {

    final String CLASS_NAME_V1 = "com.nepumuk.notizen.objects.notes.TaskNote";
    final String CLASS_NAME_V2 = "com.nepumuk.notizen.tasks.objects.TaskNote";

    final String CLASS_NAME_TASK_V1 = "com.nepumuk.notizen.objects.tasks.Task";
    final String CLASS_NAME_TASK_V2 = "com.nepumuk.notizen.tasks.objects.Task";

    @Override
    public Migration.MigrationObject migrate(Migration.MigrationObject object) {
        TaskNote taskNote = new TaskNote();
        int targetVersion = taskNote.getVersion();
        if (object.Version < targetVersion){
            // from version 1 to ?
            object.dataType = object.dataType.replace(CLASS_NAME_V1,CLASS_NAME_V2);
            object.dataString = object.dataString.replace(CLASS_NAME_TASK_V1,CLASS_NAME_TASK_V2);
            // update to target version
            object.Version = targetVersion;
        }
        return object;
    }
}
