package com.nepumuk.notizen.core.utils.db_access;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface DatabaseStorable {
    /**
     * version on the object.
     * Increment if the format has changed fundamentally and you have to change/add fields
     * @return version
     */
    @JsonIgnore
    int getVersion();

    /**
     * get the data string that shall be stored in the database
     * @return string
     */
    @JsonIgnore
    String getDataString();

    /**
     * get the type of the object.
     * will be saved in the database for deserialization
     * @return
     */
    @JsonIgnore
    String getType();

    /**
     * id of the object
     * primary key of the database
     * @return
     */
    @JsonIgnore
    String getId();

}
