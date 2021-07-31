package com.nepumuk.notizen.core.utils;

import android.os.Looper;

import androidx.core.os.HandlerCompat;

// todo do proper background handling

/**
 * Wrapper class for worker implementation used in this app
 * the provided runnables will be run on different levels, e.g. background or ui thread.
 */
public class BackgroundWorker {
    Runnable runnable;
    Thread runner = null;
    boolean runOnUiThread;

    /**
     * create a worker that will run the given runnable in background
     * @param runnable to run
     */
    public BackgroundWorker(Runnable runnable) {
        super();
        this.runnable = runnable;
    }

    /**
     * create a worker that will run the given runnable on the UI thread of the provided activity
     * @param runOnMain whether to run on main thread
     * @param runnable runnable to run on UI thread
     */
    public BackgroundWorker(boolean runOnMain, Runnable runnable) {
        super();
        runOnUiThread = runOnMain;
        this.runnable = runnable;
    }

    /**
     * run the runnable supplied during creation
     */
    public void start(){
        // no activity supplied, run in background
        if (!runOnUiThread) {
            runner = new Thread(runnable);
            runner.start();
        }else{
            HandlerCompat.createAsync(Looper.getMainLooper()).post(runnable);
        }
    }

    /**
     * check whether the given runnable is alive
     * if it is run on the ui-thread, false will be returned
     * @see Thread#isAlive()
     * @return whether the runnable is alive
     */
    public boolean isAlive(){
        return runner != null && runner.isAlive() ;
    }

    /**
     * waits until the runnables execution has completed.
     * @see Thread#join()
     * @throws InterruptedException in case the runnable is interrupted
     */
    public void join() throws InterruptedException {
        if (isAlive()){
            runner.join();
        }
    }
}
