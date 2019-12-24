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
     * creates an instance of view model containing data stored in the database
     * the data will be fetched asynchronously as a part of the creation process
     *
     * a Map with no data in it will be already added before fetching, so no null pointer here
     */
    public NoteViewModel(){
        Log.d(TAG, "NoteViewModel: creating");
        data = new MutableLiveData<>();
        dataMap = new HashMap<>();
        data.setValue(dataMap);
        // do an async read of the DB
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (DatabaseStorable storable: new cDBDataHandler().read()) {
                    dataMap.put(storable.getId(),storable);
                }
                data.postValue(dataMap);
                Log.d(TAG, "run: runner finished");
            }
        }).start();
    }

    public MutableLiveData<HashMap<String, DatabaseStorable>> getData(){
        return data;
    }

    public void updateOrCreate(DatabaseStorable storable){
        if (dataMap.containsKey(storable.getId())){
            updateData(storable);
        }else {
            createData(storable);
        }
    }

    public void updateData(DatabaseStorable storable){
        dataMap.put(storable.getId(), storable);
        data.setValue(dataMap);
        new cDBDataHandler().update(storable);
    }

    public void deleteData(final DatabaseStorable storable){
        dataMap.remove(storable.getId());
        data.setValue(dataMap);
        new cDBDataHandler().delete(storable);
    }

    public void createData(final DatabaseStorable storable){
        dataMap.put(storable.getId(), storable);
        data.setValue(dataMap);
        new cDBDataHandler().insert(storable);
    }

    public void observe(LifecycleOwner owner, Observer observer){
        data.observe(owner, observer);
    }

    public void observeForEver(Observer observer){
        data.observeForever(observer);
    }
}
