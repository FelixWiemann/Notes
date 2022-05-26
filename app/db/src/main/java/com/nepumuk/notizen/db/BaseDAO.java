package com.nepumuk.notizen.db;

import androidx.annotation.WorkerThread;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.Collection;

@Dao
@WorkerThread
public abstract class BaseDAO<T> {
    /**
     * inserts all given objects
     * @param objects
     */
    @Insert
    public abstract void insert(Collection<T> objects);
    /**
     * inserts given object
     * @param object
     */
    @Insert
    public abstract void insert(T object);

    /**
     * objects to delete
     * @param objects to delete
     */
    @Delete
    public abstract void delete(Collection<T> objects);

    /**
     * given object to delete
     * @param object to delete
     */
    @Delete
    public abstract void delete(T object);

    /**
     * update the given objects
     * @param objects to update
     * @return number of objects changed
     */
    @Update
    public abstract int update(Collection<T> objects);


    /**
     * update the given objects
     * @param object to update
     * @return number of objects changed
     */
    @Update
    public abstract int update(T object);

    /**
     * creates or updates a favourite in the database depending of whether it exists or not
     * @param object to update or create
     */
    @Transaction()
    public void createOrUpdate(T object){
        if (update(object)==0){
            insert(object);
        }
    }
    /**
     * creates or updates a favourite in the database depending of whether it exists or not
     * @param object to update or create
     */
    @Transaction()
    public void createOrUpdate(Collection<T> object){
        if (update(object)==0){
            insert(object);
        }
    }
}
