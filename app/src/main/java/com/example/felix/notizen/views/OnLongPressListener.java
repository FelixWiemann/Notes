package com.example.felix.notizen.views;

import com.example.felix.notizen.Utils.DBAccess.DatabaseStorable;

/**
 * OnLongPressListener listens to long presses - duh
 */
public interface OnLongPressListener {
    /**
     * will be called on a long press, the callback contains the data
     * that are associated with the long press
     * @param data DatabaseStorable that have been selected with the longpress
     */
    void onLongPress(DatabaseStorable data);
}
