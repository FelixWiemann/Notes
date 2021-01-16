package com.nepumuk.notizen.textnotes.testutils;

import com.nepumuk.notizen.core.objects.StorageObject;

/**
 * Database Storable test implementation that contains no data,
 * but can be used for testing when a DatabaseStorable is needed
 * warnings suppressed, as test class
 */
public class DataBaseStorableTestImpl extends StorageObject {
    public static final String DATA_STRING = "{\"lastChangedDate\":1594896550151,\"title\":\"title\",\"creationDate\":1594896550151,\"idString\":\"bb4bb4a3-51e2-4fd4-b96e-5b7a3a132be2\"}";
    public static final String DATA_TYPE = DataBaseStorableTestImpl.class.getCanonicalName();
    public static final String DATA_ID = "bb4bb4a3-51e2-4fd4-b96e-5b7a3a132be2";
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
        if (this.getClass() != that.getClass()){
            return false;
        }
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
