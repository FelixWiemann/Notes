package com.example.felix.notizen.testutils;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

/**
 * Database Storable test implementation that contains no data,
 * but can be used for testing when a DatabaseStorable is needed
 */
public class DataBaseStorableTestImpl implements DatabaseStorable {
    public static final String DATA_STRING = "DATA STRING";
    public static final String DATA_TYPE = "TYPE STRING";
    public static final String DATA_ID = "ID STRING";
    public static final int VERSION_NO = 5423;

    @Override
    public int getVersion() {
        return VERSION_NO;
    }

    @Override
    public String getDataString() {
        return DATA_STRING;
    }

    @Override
    public String getType() {
        return DATA_TYPE;
    }

    @Override
    public String getId() {
        return DATA_ID;
    }
}
