package com.example.felix.notizen.Utils;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;
import com.example.felix.notizen.Utils.DBAccess.cDBDataHandler;

import java.util.HashMap;

/**
 * View Model that contains all views stored in the database
 * on creation of an instance, all data will be loaded
 */
public class NoteViewModel extends ViewModel {
    private static final String TAG = "NoteViewModel";

    /**
     * life data content
     */
    private MutableLiveData<HashMap<String, DatabaseStorable>> data;
    private HashMap<String, DatabaseStorable> dataMap;

    /**
     * thread for fetching the data from the Database
     */
    private Thread dataFetcher;

    /**
     * creates an instance of view model containing data stored in the database
     * the data will be fetched asynchronously as a part of the creation process
     * <p>
     * a Map with no data in it will be already added before fetching, so no null pointer here
     */
    public NoteViewModel() {
        super();
        Log.d(TAG, "NoteViewModel: creating");
        data = helper.getLiveData();
        dataMap = new HashMap<>();
        data.setValue(dataMap);
        // do an async read of the DB
        dataFetcher = new Thread(new Runnable() {
            @Override
            public void run() {
                readFromDatabase();
            }
        });
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
    public void observe(LifecycleOwner owner, Observer observer) {
        data.observe(owner, observer);
    }

    /**
     * add an observer for the data forever
     *
     * @param observer to be notified of changes
     */
    public void observeForEver(Observer observer) {
        data.observeForever(observer);
    }

    /**
     * helper class to be able to swap new calls to mocks for testing
     */
    static class helper{

        static cDBDataHandler getDBHandler() {
            return new cDBDataHandler();
        }

        static MutableLiveData<HashMap<String, DatabaseStorable>> getLiveData() {
            return new MutableLiveData<>();
        }
    }

}
