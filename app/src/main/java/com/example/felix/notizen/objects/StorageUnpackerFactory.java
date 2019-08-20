package com.example.felix.notizen.objects;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class StorageUnpackerFactory {
    private static final StorageUnpackerFactory ourInstance = new StorageUnpackerFactory();

    public static StorageUnpackerFactory getInstance() {
        return ourInstance;
    }

    private StorageUnpackerFactory() {
    }

    public DatabaseStorable createFromData(String ID, String Type, String Data, int Version) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Class<?> clazz = Class.forName(Type);
        Constructor<?> ctor = clazz.getConstructor(UUID.class);
        DatabaseStorable object = (DatabaseStorable) ctor.newInstance(UUID.fromString(ID));

        ObjectMapper mapper = new ObjectMapper();
        object = mapper.readValue(Data, mapper.getTypeFactory().constructType(object.getClass()));
        return object;
    }

}
