package com.nepumuk.notizen.core.utils;

public interface OnUpdateCallback {
    /**
     * gets called, if the listener should update itself.
     * this is most likely due to some changed data within the caller.
     * the listener should reinitialize what ever he had done
     */
    void update();

    /**
     * will get called, if the listener should redo it's layouting
     * e.g. size of a view has changed, and the parent should reconsider the new size
     */
    void requestNewLayout(int newExpandedSize);
}
