package com.nepumuk.notizen.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nepumuk.notizen.testutils.DataBaseStorableTestImpl;
import com.nepumuk.notizen.views.adapters.view_holders.ViewHolderInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TitleAdapter.class,LayoutInflater.class})
public class TitleAdapterTest {

    TitleAdapter<DataBaseStorableTestImpl> adapterUnderTest;
    @Mock
    LayoutInflater inflater;
    @Mock
    View view;
    @Mock
    TextView textView;

    @Before
    public void setUp() throws Exception {
        // create title adapter under test
        adapterUnderTest = new TitleAdapter<>(new ArrayList<>(),1);
        // prepare mocks
        mockStatic(LayoutInflater.class);
        when(LayoutInflater.from(any())).thenReturn(inflater);
        doReturn(view).when(inflater).inflate(anyInt(),any(ViewGroup.class),anyBoolean());
        when(view.findViewById(anyInt())).thenReturn(textView);
    }

    @Test
    public void onCreateViewHolder() {
        // given
        ViewHolderInterface<DataBaseStorableTestImpl> holderInterface;
        // when
        holderInterface = adapterUnderTest.onCreateViewHolder(mock(ViewGroup.class),0);
        // then
        // view holder was constructed
        assertNotNull(holderInterface);
        // and properly initialized
        verify(view,times(1)).findViewById(anyInt());

    }
}