package com.nepumuk.notizen.core.views.fragments;

/**
 * Interface the SaveDataFragment will call, depending on the user interaction on the fragment
 */
public interface SaveDataFragmentListener {

    /**
     * save and exit to the state before the listener was activated
     */
    void saveAndExit();

    /**
     * cancel the currently active exit request
     */
    void cancelExit();

    /**
     * discard currently changed data and exit request
     */
    void discardAndExit();
}
