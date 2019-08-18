package com.example.felix.notizen.objects;

import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON Object for basic JSON Handling
 */

public abstract class cJSONObject extends cIdObject {

    public cJSONObject(UUID mID, String mTitle) {
        super(mID, mTitle);
    }

    public cJSONObject(String mTitle) {
        super(mTitle);
    }

    public cJSONObject() {
        super();

    }


    @JsonIgnore
    public String toJson(){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(this);
        return node.toString();
    }

    @Override
    public String toString() {
        return this.toJson();
    }

    @Override
    public boolean equals(Object that) {
        if (this.getClass() != that.getClass()){
            return false;
        }
        if (this.toJson().equalsIgnoreCase(((cJSONObject)that).toJson())){
            return true;
        }
        return false;
    }
}
