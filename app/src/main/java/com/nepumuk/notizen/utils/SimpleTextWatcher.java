package com.nepumuk.notizen.utils;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;

/**
 * <p>simple {@link TextWatcher} implementation
 * </p>use this text watcher if you only want to react to changed text and not manipulate or further analyze the changes
 * <p></p>implements {@link #beforeTextChanged(CharSequence, int, int, int)} and {@link #onTextChanged(CharSequence, int, int, int)} with empty bodies.
 * <p></p>{@link #afterTextChanged(Editable)} calls the provided {@link OnTextChangeListener#onTextChange(Editable)}
 */
public class SimpleTextWatcher implements TextWatcher {

    OnTextChangeListener listener;

    /**
     * creates a simple text watcher
     * @param listener
     */
    public SimpleTextWatcher(@NonNull OnTextChangeListener listener) {
        super();
        this.listener = listener;
    }

    /** */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    /** */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    /**
     * <p>gets called when the text is changed.
     * </p>calls the attached listeners {@link OnTextChangeListener#onTextChange(Editable)}
     */
    @Override
    public void afterTextChanged(Editable s){
        listener.onTextChange(s);
    }

    /**
     * text change listener which is called by the {@link SimpleTextWatcher}
     */
    public interface OnTextChangeListener{
        /**
         * is called in {@link SimpleTextWatcher#afterTextChanged(Editable)} when the {@link SimpleTextWatcher} provided with this implementation detects a change
         */
        void onTextChange(Editable s);
    }
}
