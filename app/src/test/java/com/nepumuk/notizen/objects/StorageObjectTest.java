package com.nepumuk.notizen.objects;

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


@PrepareForTest({StorageObjectTest.StorageObjectImpl.class})
public class StorageObjectTest extends AndroidTest {

    private StorageObjectImpl object;
    private final String newTitle = "testtitle";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        object = new StorageObjectImpl();
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
        object = new StorageObjectImpl();
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
    public void setTitle() {
        StorageObjectImpl testImpl = Mockito.spy(object);
        testImpl.setTitle("new title");
        assertEquals("new title", testImpl.getTitle());
    }

    @Test
    public void testJson(){
        object = new StorageObjectImpl();
        String json = object.toJson();
        assertTrue(json.contains("creationDate"));
        assertTrue(json.contains("lastChangedDate"));
    }

    @Ignore ("test support class, not to be tested")
    static class StorageObjectImpl extends StorageObject {
        StorageObjectImpl(){
            super(UUID.randomUUID(),"");
        }

        @Override
        public int getVersion() {
            return 1;
        }
    }
}