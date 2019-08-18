package com.example.felix.notizen.Utils.DBAccess;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface DatabaseStorable {
    @JsonIgnore
    int getVersion();
    @JsonIgnore
    String getDataString();
    @JsonIgnore
    String getType();
    @JsonIgnore
    String getId();
}
