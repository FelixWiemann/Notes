package com.nepumuk.notizen.core.utils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.nepumuk.notizen.core.objects.IdObject;
import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.testutils.AndroidTest;
import com.nepumuk.notizen.core.utils.db_access.DatabaseStorable;
import com.nepumuk.notizen.core.utils.db_access.DbDataHandler;
import com.nepumuk.notizen.db.AppDataBaseHelper;
import com.nepumuk.notizen.db.Favourite;
import com.nepumuk.notizen.db.FavouriteDAO;
import com.nepumuk.notizen.db.FavouriteRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PrepareForTest({MainViewModel.class,MutableLiveData.class,DbDataHandler.class})
public class MainViewModelTest extends AndroidTest {

    @Mock(name = "handler")
    DbDataHandler handler;
    @Mock(name = "liveData")
    MutableLiveData<HashMap<String, StorageObject>> liveData;
    @Mock(name = "dataFetcher")
    BackgroundWorker dataFetcher;
    @Mock
    StorageObject storable1;
    @Mock
    StorageObject storable2;
    @Mock
    StorageObject storable3;
    @Mock(name = "dataMap")
    HashMap<String, IdObject> dataMap;

    HashMap<String, StorageObject> storables;

    @InjectMocks
    MainViewModel modelUnderTest;

    @Mock(name ="dao")
    FavouriteDAO favouriteDAO;

    @Mock
    AppDataBaseHelper appDataBaseHelper;


    FavouriteRepository spyRepo ;



    @Before
    public void setUp() throws Exception {
        super.setUp();

        when(AppDataBaseHelper.getInstance()).thenReturn(appDataBaseHelper);

        MockitoAnnotations.initMocks(this);
        when(storable1.getId()).thenReturn(UUID.randomUUID().toString());
        when(storable2.getId()).thenReturn(UUID.randomUUID().toString());
        when(storable3.getId()).thenReturn(UUID.randomUUID().toString());

        spyRepo = spy(new FavouriteRepository(favouriteDAO));

        // create a list stored in Database
        storables = new HashMap<>();
        storables.put(storable1.getId(), storable1);
        storables.put(storable2.getId(), storable2);
        storables.put(storable3.getId(), storable3);

        // init handler
        when(handler.read()).thenReturn(new ArrayList<>(storables.values()));
        when(liveData.getValue()).thenReturn(storables);
        // wait for the loading to finish
        // in reality will be in background, cannot have for reliable/replicable testing
        modelUnderTest.waitForFetchToFinish();
        when(appDataBaseHelper.getFavourites()).thenReturn(spyRepo);
    }

    @After
    public void after() throws InterruptedException {
        // make sure the thread is not running anymore so that the next test does not fail
        if (modelUnderTest.isCurrentlyFetchingDataFromDB()) {
            modelUnderTest.waitForFetchToFinish();
        }
    }

    @Test
    public void constructor() throws Exception {
        // given
        when(handler.read()).thenReturn(new ArrayList<>(storables.values()));
        whenNew(MutableLiveData.class).withAnyArguments().thenReturn(liveData);
        whenNew(DbDataHandler.class).withAnyArguments().thenReturn(handler);
        // when
        modelUnderTest = new MainViewModel();
        modelUnderTest.waitForFetchToFinish();
        // then
        assertEquals(storables.size(), modelUnderTest.dataMap.size());
        verify(liveData,atLeastOnce()).postValue(any());
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
        modelUnderTest.init();
        // when creating of modelUnderTest
        // then
        verify(dataFetcher).start();
    }

    @Test
    public void updateOrCreateKnown() {
        // given
        MainViewModel modelSpy = spy(modelUnderTest);
        String storableId = "ID";
        when(storable1.getId()).thenReturn(storableId);
        when(dataMap.containsKey(eq(storable1.getId()))).thenReturn(true);
        // when
        modelSpy.updateOrCreate(storable1);
        // then
        verify(modelSpy).updateData(storable1);

    }
    @Test
    public void updateOrCreateUnKnown() {
        // given
        MainViewModel modelSpy = spy(modelUnderTest);
        DatabaseStorable storableMock = mock(DatabaseStorable.class);
        when(storableMock.getId()).thenReturn(UUID.randomUUID().toString());
        // when
        modelSpy.updateOrCreate(storableMock);
        // then
        verify(modelSpy).createData(storableMock);
    }

    @Test
    public void updateData() throws IllegalAccessException, InstantiationException {
        // given
        // when
        modelUnderTest.updateData(storable1);
        // then
        verify(handler).update(storable1);
        verify(liveData).setValue(any(HashMap.class));
    }

    @Test
    @Ignore("verify delete does not work")
    public void deleteData() throws InterruptedException,InstantiationException, IllegalAccessException {
        // given
        // when
        modelUnderTest.deleteData(storable1);
        // then
        verify(handler).delete(storable1);
        verify(liveData).setValue(any(HashMap.class));
        // ugly, but delete is run in background...
        Thread.sleep(750);
        verify(spyRepo, atLeastOnce()).delete(any(Favourite.class));
    }

    @Test
    public void createData() throws IllegalAccessException, InstantiationException {
        // given
        // when
        modelUnderTest.createData(storable1);
        // then
        verify(handler).insert(storable1);
        verify(liveData).setValue(any(HashMap.class));
    }

    @Test
    @Ignore("verify to test")
    public void observe() {
        // given
        LifecycleOwner owner = mock(LifecycleOwner.class);
        Observer<HashMap<String, DatabaseStorable>> observer = mock(Observer.class);
        // when
        modelUnderTest.observe(owner, observer);
        // then
        //verify(liveData).observe(owner,observer);
    }

    @Test
    @Ignore("verify to test")
    public void observeForEver() {
        // given
        Observer<HashMap<String, DatabaseStorable>> observer = mock(Observer.class);
        // when
        modelUnderTest.observeForEver(observer);
        // then
        //verify(liveData).observeForever(observer);
    }
}