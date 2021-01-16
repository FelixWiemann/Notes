package com.nepumuk.notizen.textnotes;

import com.nepumuk.notizen.core.objects.Migration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextNoteMigrationServiceTest {
    @Test
    public void testMigrationFromV1(){
        // given
        String currentContent = "SOME INVALID CONTENT";
        Migration.MigrationObject object = new Migration.MigrationObject("com.nepumuk.notizen.objects.notes.TextNote",currentContent,1);
        String targetType = "com.nepumuk.notizen.textnotes.objects.TextNote";
        int targetVersion = 2;
        TextNoteMigrationService migrationService = new TextNoteMigrationService();
        // when
        migrationService.migrate(object);
        // then
        assertEquals(targetVersion, object.Version);
        assertEquals(object.dataType, targetType);
        assertEquals(object.dataString, currentContent);
    }
}