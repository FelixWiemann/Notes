package com.nepumuk.notizen.tasks;

import com.nepumuk.notizen.core.objects.Migration;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskNoteMigrationServiceTest {

    @Test
    public void migrate() {
        // given
        String currentContent = "SOME INVALID CONTENT BUT ALSO CONTAINS TASK REFERENCE com.nepumuk.notizen.objects.tasks.Task BUT ALSO SOMETHING ELSE AND ALSO ANOTHER TASK com.nepumuk.notizen.objects.tasks.Task";
        Migration.MigrationObject object = new Migration.MigrationObject("com.nepumuk.notizen.objects.notes.TaskNote",currentContent,1);
        String targetType = "com.nepumuk.notizen.tasks.objects.TaskNote";
        int targetVersion = 2;
        TaskNoteMigrationService migrationService = new TaskNoteMigrationService();
        // when
        migrationService.migrate(object);
        // then
        assertEquals(targetVersion, object.Version);
        assertEquals(object.dataType, targetType);
        assertNotEquals(object.dataString, currentContent);
        assertTrue(object.dataString.contains("com.nepumuk.notizen.tasks.objects.Task"));
        assertFalse(object.dataString.contains("com.nepumuk.notizen.objects.tasks.Task"));
    }
}