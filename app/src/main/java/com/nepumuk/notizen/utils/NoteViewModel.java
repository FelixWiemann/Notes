package com.nepumuk.notizen.utils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.utils.db_access.DbDataHandler;

import java.util.HashMap;

/**
 * View Model that contains all views stored in the database
 * on creation of an instance, all data will be loaded
 */
public class NoteViewModel extends ViewModel {
    private static final String LOG_TAG = "NoteViewModel";

    /**
     * life data content
     */
    private final MutableLiveData<HashMap<String, DatabaseStorable>> data;
    private final HashMap<String, DatabaseStorable> dataMap;

    /**
     * thread for fetching the data from the Database
     */
    private final Thread dataFetcher;

    /**
     * creates an instance of view model containing data stored in the database
     * the data will be fetched asynchronously as a part of the creation process
     * <p>
     * a Map with no data in it will be already added before fetching, so no null pointer here
     */
    public NoteViewModel() {
        super();
        data = helper.getLiveData();
        dataMap = new HashMap<>();
        data.setValue(dataMap);
        // do an async read of the DB
        dataFetcher = new Thread(this::readFromDatabase);
        dataFetcher.start();
    }

    /**
     * checks whether the data fetcher is executing
     * @return whether the thread is alive
     */
    public boolean isCurrentlyFetchingDataFromDB(){
        return dataFetcher.isAlive();
    }

    /**
     * waits until the thread execution is done.
     * Use cautiously, all main thread activity will be paused.
     */
    public void waitForFetchToFinish() throws InterruptedException {
        dataFetcher.join();
    }

    /**
     * reads from the database and sets the values into the MutableLiveData
     */
    private void readFromDatabase(){
        for (DatabaseStorable storable : helper.getDBHandler().read()) {
            dataMap.put(storable.getId(), storable);
        }
        data.postValue(dataMap);
    }

    /**
     * gets the data from the NoteViewModel
     * @return data
     */
    public MutableLiveData<HashMap<String, DatabaseStorable>> getData(){
        return data;
    }

    /**
     *
     * update or create a storable stored in the ViewModel
     * will also update the Database for persistence
     *
     * it will be updated, if already in the database
     * or created if not existent
     *
     * @param storable to be updated or created
     */
    public void updateOrCreate(DatabaseStorable storable) {
        if (dataMap.containsKey(storable.getId())) {
            updateData(storable);
        } else {
            createData(storable);
        }
    }

    /**
     * updates an entry in the ViewModel
     * will also update the entry of the Database for persistence
     *
     * @param storable to be updated
     */
    public void updateData(DatabaseStorable storable) {
        dataMap.put(storable.getId(), storable);
        data.setValue(dataMap);
        helper.getDBHandler().update(storable);
    }

    /**
     * deletes an entry in the ViewModel
     * will also delete the entry from Database
     *
     * @param storable to be deleted
     */
    public void deleteData(final DatabaseStorable storable) {
        dataMap.remove(storable.getId());
        data.setValue(dataMap);
        helper.getDBHandler().delete(storable);
    }


    /**
     * creates an entry in the ViewModel
     * will also create an entry the Database for persistence
     *
     * @param storable to be stored
     */
    public void createData(final DatabaseStorable storable) {
        dataMap.put(storable.getId(), storable);
        data.setValue(dataMap);
        helper.getDBHandler().insert(storable);
    }

    /**
     * add an observer for the data
     *
     * @param owner of the lifecycle
     * @param observer to be notified of changes
     */
    public void observe(LifecycleOwner owner, Observer<HashMap<String, DatabaseStorable>> observer) {
        data.observe(owner, observer);
    }

    /**
     * add an observer for the data forever
     *
     * @param observer to be notified of changes
     */
    public void observeForEver(Observer<HashMap<String, DatabaseStorable>> observer) {
        data.observeForever(observer);
    }

    /**
     * helper class to be able to swap new calls to mocks for testing
     */
    static class helper{

        static DbDataHandler getDBHandler() {
            return new DbDataHandler();
        }

        static MutableLiveData<HashMap<String, DatabaseStorable>> getLiveData() {
            return new MutableLiveData<>();
        }
    }

}
