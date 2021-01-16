package com.nepumuk.notizen.core.objects;

/**
 * a Migration service takes an {@link com.nepumuk.notizen.core.objects.Migration.MigrationObject} and migrates it to a specific version.
 * This migration does not necessarily need to be to the latest version.
 * during migration, the migration object will be updated according to the migration.
 */
public interface MigrationService {
    /**
     * migrate the given object based on the scope of the migration service
     * @param object to be migrated
     * @return migrated object
     */
    Migration.MigrationObject migrate(Migration.MigrationObject object);
}
