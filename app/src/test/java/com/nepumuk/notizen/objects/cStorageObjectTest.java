package com.nepumuk.notizen.objects;

import com.nepumuk.notizen.utils.OnUpdateCallback;
import com.nepumuk.notizen.objects.filtersort.SortCategory;
import com.nepumuk.notizen.testutils.AndroidTest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@PrepareForTest({cStorageObjectTest.cStorageObjectImpl.class})
public class cStorageObjectTest extends AndroidTest {

    private cStorageObjectImpl object;
    private final String newTitle = "testtitle";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        object = new cStorageObjectImpl();
        object.setTitle(newTitle);

    }

    @Test
    public void initSortables(){
        // given
        String title;
        long creationTime;
        long lastChangeTime;
        // when
        title =  object.getSortable(SortCategory.TITLE);
        creationTime = object.getSortable(SortCategory.CREATION_TIME);
        lastChangeTime = object.getSortable(SortCategory.LAST_CHANGE_TIME);
        // then
        assertEquals(object.getTitle(), title);
        assertEquals(object.getCreationDate(), creationTime);
        assertEquals(object.getLastChangedDate(), lastChangeTime);
    }

    @Test
    public void getDataString() {
        object = new cStorageObjectImpl();
        assertEquals(object.getDataString(),object.toJson());
    }

    @Test
    public void getType() {
        assertEquals(object.getType(), object.getClass().getCanonicalName());
    }

    @Test
    public void getId() {
        assertEquals(object.getIdString(), object.getId());
    }

    @Test
    public void getTitle() {
        assertEquals(object.getTitle(), newTitle);
    }

    @Test
    public void onDataChanged() {
        cStorageObjectImpl testImpl = Mockito.spy(object);
        testImpl.onDataChanged();
        Mockito.verify(testImpl, Mockito.times(1)).updateData();
    }

    @Test
    public void updateData() {
        OnUpdateCallback callback = Mockito.mock(OnUpdateCallback.class);
        object.setOnChangeListener(callback);
        Mockito.verify(callback, Mockito.times(1)).update();
    }

    @Test
    public void setTitle() {
        cStorageObjectImpl testImpl = Mockito.spy(object);
       // Mockito.when(testImpl.setTitle(anyString())).thenCallRealMethod();
        testImpl.setTitle("new title");
        Mockito.verify(testImpl, Mockito.times(1)).updateData();
        assertEquals("new title", testImpl.getTitle());
    }

    @Test
    public void testJson(){
        object = new cStorageObjectImpl();
        String json = object.toJson();
        assertTrue(json.contains("creationDate"));
        assertTrue(json.contains("lastChangedDate"));
    }

    @SuppressWarnings("UnconstructableJUnitTestCase")
    @Ignore
    static class cStorageObjectImpl extends  cStorageObject{
        cStorageObjectImpl(){
            super(UUID.randomUUID(),"");
        }

        @Override
        public int getVersion() {
            return 1;
        }
        @Test
        public void test(){

        }
    }
}