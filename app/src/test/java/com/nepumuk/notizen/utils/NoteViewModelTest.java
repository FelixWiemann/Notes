package com.nepumuk.notizen.utils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.nepumuk.notizen.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.utils.db_access.DbDataHandler;
import com.nepumuk.notizen.testutils.AndroidTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;

@SuppressWarnings({"rawtypes", "unchecked"})
@PrepareForTest({DbDataHandler.class, NoteViewModel.class})
public class NoteViewModelTest extends AndroidTest {

    DbDataHandler handler;
    NoteViewModel modelUnderTest;
    DatabaseStorable storable1;
    DatabaseStorable storable2;
    DatabaseStorable storable3;
    HashMap<String, DatabaseStorable> storables;
    MutableLiveData<HashMap<String, DatabaseStorable>> liveData;
    NoteViewModel.helper helper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        // init mocks
        handler = mock(DbDataHandler.class);
        liveData = mock(MutableLiveData.class);
        helper = mock(NoteViewModel.helper.class);

        // create a list stored in Database
        storables = new HashMap<>();
        storable1 = mock(DatabaseStorable.class);
        when(storable1.getId()).thenReturn(UUID.randomUUID().toString());
        storables.put(storable1.getId(), storable1);
        storable2 = mock(DatabaseStorable.class);
        when(storable2.getId()).thenReturn(UUID.randomUUID().toString());
        storables.put(storable2.getId(), storable2);
        storable3 = mock(DatabaseStorable.class);
        when(storable3.getId()).thenReturn(UUID.randomUUID().toString());
        storables.put(storable3.getId(), storable3);

        // init handler
        when(handler.read()).thenReturn(new ArrayList<>(storables.values()));
        mockStatic(NoteViewModel.helper.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                switch (invocation.getMethod().getName()){
                    case "getLiveData":
                        return liveData;
                    case "getDBHandler":
                        return handler;
                }
                fail();
                return null;
            }
        });

        modelUnderTest = new NoteViewModel();

        when(liveData.getValue()).thenReturn(storables);
        // wait for the loading to finish
        // in reality will be in background, cannot have for reliable/replicable testing
        modelUnderTest.waitForFetchToFinish();
    }

    @After
    public void after() throws InterruptedException {
        // make sure the thread is not running anymore so that the next test does not fail
        if (modelUnderTest.isCurrentlyFetchingDataFromDB()) {
            modelUnderTest.waitForFetchToFinish();
        }
    }

    @Test
    public void getData() {
        // given
        HashMap<String, DatabaseStorable> result;
        // when
        result = modelUnderTest.getData().getValue();
        // then
        assertEquals(storables, result);
    }

    @Test
    public void testReadDataFromDB() {
        // given the setup
        // when creating of modelUnderTest
        // then
        verify(liveData).postValue(storables);
    }

    @Test
    public void updateOrCreateKnown() {
        // given
        NoteViewModel modelSpy = spy(modelUnderTest);
        // when
        modelSpy.updateOrCreate(storable1);
        // then
        verify(modelSpy).updateData(storable1);

    }
    @Test
    public void updateOrCreateUnKnown() {
        // given
        NoteViewModel modelSpy = spy(modelUnderTest);
        DatabaseStorable storableMock = mock(DatabaseStorable.class);
        when(storableMock.getId()).thenReturn(UUID.randomUUID().toString());
        // when
        modelSpy.updateOrCreate(storableMock);
        // then
        verify(modelSpy).createData(storableMock);
    }

    @Test
    public void updateData() {
        // given
        clearInvocations(liveData);
        // when
        modelUnderTest.updateData(storable1);
        // then
        verify(handler).update(storable1);
        verify(liveData).setValue(any(HashMap.class));
    }

    @Test
    public void deleteData() {
        // given
        clearInvocations(liveData);
        // when
        modelUnderTest.deleteData(storable1);
        // then
        verify(handler).delete(storable1);
        verify(liveData).setValue(any(HashMap.class));
    }

    @Test
    public void createData() {
        // given
        clearInvocations(liveData);
        // when
        modelUnderTest.createData(storable1);
        // then
        verify(handler).insert(storable1);
        verify(liveData).setValue(any(HashMap.class));
    }

    @Test
    public void observe() {
        // given
        LifecycleOwner owner = mock(LifecycleOwner.class);
        Observer observer = mock(Observer.class);
        // when
        modelUnderTest.observe(owner, observer);
        // then
        verify(liveData).observe(owner,observer);
    }

    @Test
    public void observeForEver() {
        // given
        Observer observer = mock(Observer.class);
        // when
        modelUnderTest.observeForEver(observer);
        // then
        verify(liveData).observeForever(observer);
    }
}