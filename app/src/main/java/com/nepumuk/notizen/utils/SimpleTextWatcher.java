package com.nepumuk.notizen.utils;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;

public class TextChangeListener implements TextWatcher {

    OnTextChangeListener listener;

    public TextChangeListener(@NonNull OnTextChangeListener listener){
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s){
        listener.onTextChange(s);
    }

    public interface OnTextChangeListener{
        void onTextChange(Editable s);
    }
}
