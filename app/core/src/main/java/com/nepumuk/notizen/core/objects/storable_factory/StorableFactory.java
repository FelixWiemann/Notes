package com.nepumuk.notizen.core.objects.storable_factory;

import android.content.Intent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nepumuk.notizen.core.objects.IdObject;
import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.core.objects.Migration;
import com.nepumuk.notizen.core.objects.UnpackingDataError;
import com.nepumuk.notizen.core.objects.UnpackingDataException;

import java.util.HashMap;

/**
 * class for serialization and deserialisatzion of Storables.
 * TODO create own exception to encapsulate the JACKSON behavior totally
 */
public class StorableFactory {

    private static final String INTENT_NAME_NOTE_ID = "INTENT_NAME_NOTE_ID";
    private static final String INTENT_NAME_NOTE_DATA = "INTENT_NAME_NOTE_DATA";
    private static final String INTENT_NAME_NOTE_TYPE = "INTENT_NAME_NOTE_TYPE";
    private static final String INTENT_NAME_NOTE_VERSION = "INTENT_NAME_NOTE_VERSION";
    static HashMap<String, DefaultStorableStrategy<DatabaseStorable>> strategyMap = new HashMap<>();
    //private static DefaultStorableStrategy defaultStrategy = new DefaultTextNoteStrategy();

    public static void registerDefaultStorableStrategy(String ShortCutVariableName,DefaultStorableStrategy strategy){
        strategyMap.put(ShortCutVariableName,strategy);
    }

    public static DatabaseStorable create(String ShortCutVariableName){
        if (strategyMap.containsKey(ShortCutVariableName)) {
            return strategyMap.get(ShortCutVariableName).createDefault();
        }
        // if this exception comes, check if case-sensitive error
        // or argument was not added with registerShortcut
        throw new IllegalArgumentException("Cannot create DataBaseStorable of type " + ShortCutVariableName);
    }


    /**
     * creates a storable from the given information
     *
     * @param ID uuid-string of the object to be created
     * @param Type class name of the object to be created
     * @param Data json data to be read and put into the new storable
     * @param Version version number of the current data. used for migrating between different versions of the object
     * @return DatabaseStorable object that has been created from the given data
     * @throws UnpackingDataException the given type is not valid
     * @throws UnpackingDataError if there was an issue with the
     */
    public static StorageObject createFromData(String ID, String Type, String Data, int Version) throws UnpackingDataException {
        Throwable lEx;
        try {
            // try to create data
            return createfromdata(ID,Type,Data,Version);
        }catch (UnpackingDataException|UnpackingDataError ex){
            lEx = ex;
        }
        // creation was not successfull, try migration
        Migration.MigrationObject object = new Migration.MigrationObject(Type,Data,Version);
        try{
            object = Migration.migrate(object);
            return createfromdata(ID,object.dataType,object.dataString,object.Version);
        }catch (IllegalArgumentException|UnpackingDataException ex){
            ex.addSuppressed(lEx);
            throw new UnpackingDataException(ex);
        }
    }

    private static StorageObject createfromdata(String ID, String Type, String Data, int Version)throws UnpackingDataException{
        try {
            Class<?> clazz = Class.forName(Type);
            StorageObject object;
            ObjectMapper mapper = new ObjectMapper();
            try {
                object = mapper.readValue(Data, mapper.getTypeFactory().constructType(clazz));

            } catch (JsonProcessingException e) {
                throw new UnpackingDataError("he data in the database is corrupt or we did not properly transform versions of stored data on upgrade",e);
            }
            return object;
        }catch (ClassNotFoundException ex){
            // TODO better exception handling
            //  wrong data was given in both cases of the exception...
            throw new UnpackingDataException(ex);
        } catch (ClassCastException ex){
            throw new UnpackingDataError("Wrong Class Type", ex);
        }
    }

    /**
     * adds the storable to the intent to be used in another area
     * @param intentToAddTo intent that the data shall be added to
     * @param storable the storable to be added to the intent
     * @return Intent that the storable has been added to
     */
    public static Intent addToIntent(Intent intentToAddTo, DatabaseStorable storable){
        if (storable != null){
            intentToAddTo.putExtra(INTENT_NAME_NOTE_ID,storable.getId());
            intentToAddTo.putExtra(INTENT_NAME_NOTE_DATA,storable.getDataString());
            intentToAddTo.putExtra(INTENT_NAME_NOTE_TYPE,storable.getType());
            intentToAddTo.putExtra(INTENT_NAME_NOTE_VERSION,storable.getVersion());
        }
        return intentToAddTo;
    }

    /**
     * creates a storable from the given intent.
     * the intent needs to have a storable inside, otherwise null is returned
     *
     * @param intent containing the storable
     * @return storable created from intent
     * @throws UnpackingDataException the given type is not valid
     * @throws UnpackingDataError if there was an issue with the
     */
    public static IdObject storableFromIntent(Intent intent) throws UnpackingDataException {
        if (intent == null){
            return null;
        }
        if (intent.hasExtra(INTENT_NAME_NOTE_ID)){
            return createFromData(intent.getStringExtra(INTENT_NAME_NOTE_ID),
                    intent.getStringExtra(INTENT_NAME_NOTE_TYPE),
                    intent.getStringExtra(INTENT_NAME_NOTE_DATA),
                    intent.getIntExtra(INTENT_NAME_NOTE_VERSION,1));

        }
        return null;
    }
}
