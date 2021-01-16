package com.nepumuk.notizen.core.utils;

import android.text.Editable;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

public class SimpleTextWatcherTest {

    @Test
    public void afterTextChanged() {
        // given
        Editable editable = mock(Editable.class);
        SimpleTextWatcher.OnTextChangeListener changeListener = mock(SimpleTextWatcher.OnTextChangeListener.class);
        SimpleTextWatcher watcher = new SimpleTextWatcher(changeListener);
        // when
        watcher.afterTextChanged(editable);
        // then
        verify(changeListener,atLeastOnce()).onTextChange(editable);
    }

    @Test
    public void testEmpty(){
        // given
        SimpleTextWatcher.OnTextChangeListener changeListener = mock(SimpleTextWatcher.OnTextChangeListener.class);
        SimpleTextWatcher watcher = new SimpleTextWatcher(changeListener);
        // when
        watcher.beforeTextChanged("",0,0,0);
        watcher.onTextChanged("",0,0,0);
        // then
        // no exceptions
    }


}