package com.nepumuk.notizen.core.objects;

import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;

import java.util.HashMap;

/**
 * class for migrating data from the database.
 * this should be called after reading the database content to verify that everything is up to date
 */
public class Migration {

    private final HashMap<String, HashMap<Integer,MigrationService>> serviceMap;

    private static Migration migration = new Migration();

    private Migration() {
        super();
        serviceMap = new HashMap<>();
    }

    /**
     * add a migration service for the given type and version to migrate from.
     *
     * when migrating objects, the {@link Migration} will look for any services associated with the current object version to call them.
     * for different versions of the same object you might want to add different migration services to handle different object structures.
     * however you can still add the same service for different versions, if it can handle multiple version updates itself
     *
     * @param type type description of the objects to be migrated. usually {@link Class#getCanonicalName()} of the object in the database
     * @param service to handle the actual migration
     * @param fromVersion the version of the object to me migrated the service shall be used for
     */
    public static void addMigrationService(String type, MigrationService service, int fromVersion){
        HashMap<Integer,MigrationService> versionMap = migration.serviceMap.get(type);
        if (versionMap == null){
            versionMap = new HashMap<>();
        }
        versionMap.put(fromVersion,service);
        migration.serviceMap.put(type,versionMap);
    }

    /**
     * migrate the given object.
     * the information of the object will be updated by the migration service associated to its type and version.
     * after that the object can be used to create the actual, now migrated, object.
     * @param object to be migrated.
     * @return migrated object
     * @throws IllegalArgumentException if no migration services where registered matching the type and version information of the object prior to the migration call
     */
    public static MigrationObject migrate(MigrationObject object){
        HashMap<Integer,MigrationService> versionMap = migration.serviceMap.get(object.dataType);
        if (versionMap==null) {
            throw new IllegalArgumentException("No MigrationService found for " + object.dataType);
        }
        MigrationService service = versionMap.get(object.Version);
        if (service == null){
            throw new IllegalArgumentException("No MigrationService found for " + object.dataType + " for migrating from version " + object.Version);
        }
        return service.migrate(object);
    }

    /**
     * helper object for migrating data from the database
     * this object will be updated by every {@link MigrationService} based on the manipulation of each service
     */
    public static class MigrationObject{

        /**
         * type description of the object to be migrated. usually {@link Class#getCanonicalName()} of the object in the database
         */
        public String dataType;
        /**
         * data string of the object. usually {@link DatabaseStorable#getDataString()} of the object in the database
         */
        public String dataString;
        /**
         * version information of the object to be migrated, usually {@link DatabaseStorable#getVersion()} of the object in the database
         */
        public int Version;

        /**
         * creates a object containing all info to be migrated.
         * this object will be updated by every {@link MigrationService} based on the manipulation of each service
         * @param dataType type description of the object to be migrated. usually {@link Class#getCanonicalName()} of the object in the database
         * @param dataString data string of the object. usually {@link DatabaseStorable#getDataString()} of the object in the database
         * @param fromVersion version information of the object to be migrated, usually {@link DatabaseStorable#getVersion()} of the object in the database
         */
        public MigrationObject(String dataType, String dataString, int fromVersion) {
            super();
            this.dataType = dataType;
            this.dataString = dataString;
            this.Version = fromVersion;
        }

    }

}
