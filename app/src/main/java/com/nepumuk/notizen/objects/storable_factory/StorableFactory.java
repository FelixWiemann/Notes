package com.nepumuk.notizen.objects.storable_factory;

import android.content.Intent;
import android.os.Bundle;

import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.objects.UnpackingDataError;
import com.nepumuk.notizen.objects.UnpackingDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * class for serialization and deserialisatzion of Storables.
 * TODO create own exception to encapsulate the JACKSON behavior totally
 */
public class StorableFactory {

    private static final String INTENT_NAME_NOTE_ID = "INTENT_NAME_NOTE_ID";
    private static final String INTENT_NAME_NOTE_DATA = "INTENT_NAME_NOTE_DATA";
    private static final String INTENT_NAME_NOTE_TYPE = "INTENT_NAME_NOTE_TYPE";
    private static final String INTENT_NAME_NOTE_VERSION = "INTENT_NAME_NOTE_VERSION";
    private static DefaultStorableStrategy defaultStrategy = new DefaultTextNoteStrategy();

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
    public static DatabaseStorable createFromData(String ID, String Type, String Data, int Version) throws UnpackingDataException {
        try {
            Class<?> clazz = Class.forName(Type);
            DatabaseStorable object;
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
    public static DatabaseStorable storableFromIntent(Intent intent) throws UnpackingDataException {
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

    /**
     * set the strategy for creating default storables
     * @param strategy to use for defaults
     */
    public static void setDefaultStrategy(DefaultStorableStrategy strategy){
        defaultStrategy = strategy;
    }


    /**
     * get a default DatabaseStorable with currently set strategy
     * @return
     */
    public static DatabaseStorable getDefaultStorable(){
        return defaultStrategy.createDefault();
    }

}
