package com.example.felix.notizen.views.fragments;

/**
 * Interface the SaveDataFragment will call, depending on the user interaction on the fragment
 */
public interface SaveDataFragmentListener {
    /**
     * discard changes and exit to the state before the listener was activated
     */
    void discardAndExit();

    /**
     * save and exit to the state before the listener was activated
     */
    void saveAndExit();

    /**
     * cancel the currently active exit request
     */
    void cancelExit();
}
