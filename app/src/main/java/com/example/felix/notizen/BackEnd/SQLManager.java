package com.example.felix.notizen.BackEnd;

/**
 * NOT USED, AS USING JSON INSTEAD
 * Created by felix on 13/04/17.
 * class for managing connection to DB
 *
 */
@Deprecated
public class SQLManager {

    private static SQLManager ourInstance = new SQLManager();

    public static SQLManager getInstance() {
        return ourInstance;
    }

    private SQLManager() {
    }

}
