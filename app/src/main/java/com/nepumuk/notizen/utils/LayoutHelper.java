package com.nepumuk.notizen.utils;

import java.util.ArrayList;

public class LayoutHelper {
    public static LayoutHelper LayoutHelper = new LayoutHelper();

    private int SwipableLeftSize = -1;
    private int SwipableRightSize = -1;
    private ArrayList<OnHelperCallback> callbacks = new ArrayList<>();


    public int getSwipableLeftSize() {
        return SwipableLeftSize;
    }

    public int getSwipableRightSize() {
        return SwipableRightSize;
    }


    public void registerSwipableChangeListener(OnHelperCallback callback) {
        callback.layoutUpdate(SwipableLeftSize, SwipableRightSize);
        callbacks.add(callback);
    }

    public void updateSize(int left, int right){
        if (SwipableLeftSize != left || SwipableRightSize != right){
            SwipableLeftSize = left;
            SwipableRightSize =right;
            notifyCallbacks();
        }
    }

    private void notifyCallbacks(){
        for (OnHelperCallback callback :
                callbacks) {
            callback.layoutUpdate(SwipableLeftSize, SwipableRightSize);
        }

    }


}


