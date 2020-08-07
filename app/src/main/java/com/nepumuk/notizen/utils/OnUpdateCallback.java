package com.nepumuk.notizen.utils;

public interface OnUpdateCallback {
    /**
     * gets called, if the listener should update itself.
     * this is most likely due to some changed data within the caller.
     * the listener should reinitialize what ever he had done
     */
    void update();

    /*
     * maybe a new method update(<Data>) will be necessary if updatingUI all the date costs too much time
     * this then should handle where the update will be needed
     */


    /**
     * will get called, if the listener should redo it's layouting
     * e.g. size of a view has changed, and the parent should reconsider the new size
     */
    void requestNewLayout(int newExpandedSize);
}
