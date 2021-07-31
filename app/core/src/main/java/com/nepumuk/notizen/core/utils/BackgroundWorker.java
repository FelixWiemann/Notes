package com.nepumuk.notizen.core.utils;

import android.app.Activity;

public class BackgroundWorker {
    Runnable runnable;
    Activity activity = null;

    public BackgroundWorker(Runnable runnable) {
        super();
        this.runnable = runnable;
    }

    public BackgroundWorker(Activity activity, Runnable runnable) {
        super();
        this.runnable = runnable;
        this.activity = activity;
    }

    // todo do proper background handling
    public void start(){
        if (activity == null) {
            new Thread(runnable).start();
        }else{
            activity.runOnUiThread(runnable);
        }
    }
}
