package com.example.felix.notizen.objects;

import android.content.Intent;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * class for serialization and deserialisatzion of Storables.
 *
 */
public class StoragePackerFactory {

    public static final String INTENT_NAME_NOTE_ID = "INTENT_NAME_NOTE_ID";
    public static final String INTENT_NAME_NOTE_DATA = "INTENT_NAME_NOTE_DATA";
    public static final String INTENT_NAME_NOTE_TYPE = "INTENT_NAME_NOTE_TYPE";
    public static final String INTENT_NAME_NOTE_VERSION = "INTENT_NAME_NOTE_VERSION";

    /**
     * creates a storable from the given information
     *
     * @param ID uuid-string of the object to be created
     * @param Type class name of the object to be created
     * @param Data json data to be read and put into the new storable
     * @param Version version number of the current data. used for migrating between different versions of the object
     * @return DatabaseStorable object that has been created from the given data
     * @throws ClassNotFoundException the given type is not valid
     * @throws NoSuchMethodException there is no constructor
     * @throws IllegalAccessException the data where not accessible
     * @throws InvocationTargetException and
     * @throws InstantiationException some
     * @throws IOException other exceptions
     */
    public static DatabaseStorable createFromData(String ID, String Type, String Data, int Version) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Class<?> clazz = Class.forName(Type);
        Constructor<?> ctor = clazz.getConstructor(UUID.class);
        DatabaseStorable object = (DatabaseStorable) ctor.newInstance(UUID.fromString(ID));

        ObjectMapper mapper = new ObjectMapper();
        object = mapper.readValue(Data, mapper.getTypeFactory().constructType(object.getClass()));
        return object;
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
     * @throws ClassNotFoundException the given type is not valid
     * @throws NoSuchMethodException there is no constructor
     * @throws IllegalAccessException the data where not accessible
     * @throws InvocationTargetException and
     * @throws InstantiationException some
     * @throws IOException other exceptions
     */
    public static DatabaseStorable storableFromIntent(Intent intent) throws NoSuchMethodException, IllegalAccessException, InstantiationException, IOException, InvocationTargetException, ClassNotFoundException {
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
