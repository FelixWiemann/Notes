package com.example.felix.notizen.objects;

import java.util.UUID;

/**
 * JSON Object for basic JSON Handling
 */

public abstract class cJSONObject extends cIdObject {

    public static final String aJSON_Title = "TITLE";
    public static final String aJSON_ID = "ID";
    public static final String aJSON_SEP = ":";
    public static final String aJSON_FIELD_SIGN = "\"";
    public static final String aJSON_COMMA=",";
    public static final String aJSON_OBJ_BEGIN = "{";
    public static final String aJSON_OBJ_END = "}";
    public static final String aJSON_NEW_LINE ="\n";
    public static final String aJSON_ARRAY_BEGIN = "[";
    public static final String aJSON_ARRAY_END = "]";


    public cJSONObject(UUID mID, String mTitle) {
        super(mID, mTitle);
    }

    public cJSONObject(String mTitle) {
        super(mTitle);
    }

    public abstract String generateJSONString();

    public String getJsonID (){
        String returnString = aJSON_FIELD_SIGN + aJSON_ID +aJSON_FIELD_SIGN+ aJSON_SEP + aJSON_FIELD_SIGN + getIdString() + aJSON_FIELD_SIGN +aJSON_COMMA+ aJSON_NEW_LINE;
        return returnString;
    }
    public String getJsonTitle(){
        return aJSON_FIELD_SIGN+aJSON_Title+aJSON_FIELD_SIGN+aJSON_SEP+aJSON_FIELD_SIGN+getTitle()+aJSON_FIELD_SIGN;
    }


}
