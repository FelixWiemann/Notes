package com.example.felix.notizen.Utils;

/**
 * Created by felix on 13/04/17.
 *
 * DataManager for managing all Notes while App-Runtime
 * 1. load Data from DB at startup
 * 2. manage data on runtime (updates of notes, etc)
 * 3. save data regulary
 * 4. write everything in DB at app close
 * singleton, as only on instance should be availables
 *
 */
public class RunTimeDataManager {

    private static RunTimeDataManager ourInstance = new RunTimeDataManager();

    public static RunTimeDataManager getInstance() {
        return ourInstance;
    }

    private RunTimeDataManager() {
    }
}
