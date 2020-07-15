package com.example.felix.notizen.testutils;

import com.example.felix.notizen.objects.cStorageObject;

/**
 * Database Storable test implementation that contains no data,
 * but can be used for testing when a DatabaseStorable is needed
 */
public class DataBaseStorableTestImpl extends cStorageObject {
    public static final String DATA_STRING = "DATA STRING";
    public static final String DATA_TYPE = "TYPE STRING";
    public static final String DATA_ID = "ID STRING";
    public static final int VERSION_NO = 5423;

    /**
     * overwrite getIdString to be able to create a defined ID
     * instead of the randomly assigned in constructor
     */
    @Override
    public String getIdString() {
        return getId();
    }

    /**
     * since cStorageObject only compares to the content, not the objects itself,
     * we need to overwrite the behaviour for the tests
     */
    @Override
    public boolean equals(Object that) {
        // use hash code, as the content is for all the same
        // (except for the creation, last use times; however they are not reliably different)
        return that.hashCode() == this.hashCode();
    }

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
