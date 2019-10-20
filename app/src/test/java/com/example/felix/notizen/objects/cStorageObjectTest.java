package com.example.felix.notizen.objects;

import com.example.felix.notizen.Utils.OnUpdateCallback;
import com.example.felix.notizen.testutils.AndroidTest;
import com.example.felix.notizen.views.viewsort.SortCategory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.UUID;

import static org.junit.Assert.*;

public class cStorageObjectTest extends AndroidTest {

    private cStorageObjectImpl object;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        object = new cStorageObjectImpl();
        object.setTitle("testtitle");
    }

    @Test
    public void initSortables(){

        assertNotNull(object.getSortable(SortCategory.TITLE));
        assertNotNull(object.getSortable(SortCategory.CREATION_TIME));
    }

    @Test
    public void getDataString() {
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
    public void wasUpdatedSinceLastSave() {
        // TODO
    }

    @Test
    public void onSave() {
        // TODO
    }

    @Test
    public void getTitle() {
        assertEquals(object.getTitle(), "testtitle");
    }

    @Test
    public void onDataChanged() {
        cStorageObjectImpl testImpl = Mockito.spy(object);
        testImpl.onDataChanged();
        Mockito.verify(testImpl, Mockito.times(1)).updateData();
        assertFalse(object.wasUpdatedSinceLastSave());
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
        object.setTitle("new title");
        Mockito.verify(testImpl, Mockito.times(1)).updateData();
        assertFalse(object.wasUpdatedSinceLastSave());
        assertEquals("new title", object.getTitle());
    }

    @Test
    public void testJson(){
        String json = object.toJson();
        assertTrue(json.contains("creationDate"));
        assertTrue(json.contains("lastChangedDate"));
    }

    private class cStorageObjectImpl extends  cStorageObject{
        cStorageObjectImpl(){
            super(UUID.randomUUID(),"");
        }

        @Override
        public int getVersion() {
            return 1;
        }
    };
}