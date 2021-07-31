package com.nepumuk.notizen.core.utils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.core.utils.db_access.DbDataHandler;

import java.util.HashMap;

/**
 * View Model that contains all views stored in the database
 * on creation of an instance, all data will be loaded
 */
public class MainViewModel extends ViewModel {
    private static final String LOG_TAG = "NoteViewModel";

    /**
     * life data content
     */
    MutableLiveData<HashMap<String, DatabaseStorable>> liveData;
    HashMap<String, DatabaseStorable> dataMap;

    /**
     * thread for fetching the data from the Database
     */
    BackgroundWorker dataFetcher;

    /**
     * reference to Db
     */
    DbDataHandler handler;

    /**
     * creates an instance of view model containing data stored in the database
     * the data will be fetched asynchronously as a part of the creation process
     * <p>
     * a Map with no data in it will be already added before fetching, so no null pointer here
     */
    public MainViewModel() {
        super();
        liveData = new MutableLiveData<>();
        dataMap = new HashMap<>();
        dataFetcher = new BackgroundWorker(this::readFromDatabase);
        handler = new DbDataHandler();
        init();
    }

    void init(){
        // do an async read of the DB
        liveData.setValue(dataMap);
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
        for (DatabaseStorable storable : handler.read()) {
            if (storable!=null) {
                dataMap.put(storable.getId(), storable);
            }
        }
        liveData.postValue(dataMap);
    }

    /**
     * gets the data from the NoteViewModel
     * @return data
     */
    public MutableLiveData<HashMap<String, DatabaseStorable>> getData(){
        return liveData;
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
        liveData.setValue(dataMap);
        handler.update(storable);
    }

    /**
     * deletes an entry in the ViewModel
     * will also delete the entry from Database
     *
     * @param storable to be deleted
     */
    public void deleteData(final DatabaseStorable storable) {
        dataMap.remove(storable.getId());
        liveData.setValue(dataMap);
        handler.delete(storable);
    }


    /**
     * creates an entry in the ViewModel
     * will also create an entry the Database for persistence
     *
     * @param storable to be stored
     */
    public void createData(final DatabaseStorable storable) {
        dataMap.put(storable.getId(), storable);
        liveData.setValue(dataMap);
        handler.insert(storable);
    }

    /**
     * add an observer for the data
     *
     * @param owner of the lifecycle
     * @param observer to be notified of changes
     */
    public void observe(LifecycleOwner owner, Observer<HashMap<String, DatabaseStorable>> observer) {
        liveData.observe(owner, observer);
    }

    /**
     * add an observer for the data forever
     *
     * @param observer to be notified of changes
     */
    public void observeForEver(Observer<HashMap<String, DatabaseStorable>> observer) {
        liveData.observeForever(observer);
    }

}
