package com.example.felix.notizen.objects;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON Object for basic JSON Handling
 *
 * TODO document annotations for JSON (jackson)
 */
public abstract class cJSONObject {

    cJSONObject() {
        super();
    }

    @JsonIgnore
    public String toJson(){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(this);
        return node.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return this.toJson();
    }

    @Override
    public boolean equals(Object that) {

        if (that == null){
            return false;
        }

        if (this.getClass() != that.getClass()){
            return false;
        }
        return this.toJson().equalsIgnoreCase(((cJSONObject) that).toJson());
    }
}
