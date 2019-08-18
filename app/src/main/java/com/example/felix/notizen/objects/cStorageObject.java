package com.example.felix.notizen.objects;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public abstract class cStorageObject extends cJSONObject implements DatabaseStorable {

    public cStorageObject(UUID mID, String mTitle) {
        super(mID, mTitle);
    }

    public cStorageObject() {
        super();

    }

    @Override
    public String getDataString() {
        return this.toJson();
    }

    @Override
    public String getType() {
        return getClass().getCanonicalName();
    }

    @Override
    public String getId() {
        return this.getIdString();
    }
}
