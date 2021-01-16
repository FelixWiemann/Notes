package com.nepumuk.notizen.textnotes;

import com.nepumuk.notizen.core.objects.Migration;
import com.nepumuk.notizen.core.objects.MigrationService;
import com.nepumuk.notizen.textnotes.objects.TextNote;

/**
 * migrates a Text Note to the current latest version of text note
 */
public class TextNoteMigrationService implements MigrationService {

    final String CLASS_NAME_V1 = "com.nepumuk.notizen.objects.notes.TextNote";
    final String CLASS_NAME_V2 = "com.nepumuk.notizen.textnotes.objects.TextNote";

    @Override
    public Migration.MigrationObject migrate(Migration.MigrationObject object) {
        TextNote textNote = new TextNote();
        int targetVersion = textNote.getVersion();
        if (object.Version <targetVersion){
            // from version 1 to ?
            object.dataType = object.dataType.replace(CLASS_NAME_V1,CLASS_NAME_V2);
            // update to target version
            object.Version = targetVersion;
        }
        return object;
    }
}
