package com.nepumuk.notizen.core.utils.db_access;

import android.content.ContentValues;
import android.database.Cursor;

import com.nepumuk.notizen.core.testutils.AndroidTest;
import com.nepumuk.notizen.core.testutils.DataBaseStorableTestImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.internal.progress.ThreadSafeMockingProgress;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@PrepareForTest({DbHelper.class, DbDataHandler.class})
public class DBDataHandlerTest extends AndroidTest {

    private DbHelper helperMock;
    private DbDataHandler handlerUnderTest;
    private ContentValuesImpl testImpl;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testImpl = new ContentValuesImpl();
        helperMock = PowerMockito.mock(DbHelper.class);
        ContentValues contentValueMock = PowerMockito.mock(ContentValues.class);
        PowerMockito.doAnswer(invocation -> testImpl.get(invocation.getArgument(0))).when(contentValueMock).get(ArgumentMatchers.anyString());
        Answer<Object> answerOnPut = invocation -> {
            testImpl.put(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        };
        PowerMockito.doAnswer(answerOnPut).when(contentValueMock).put(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        PowerMockito.doAnswer(answerOnPut).when(contentValueMock).put(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt());

        PowerMockito.whenNew(ContentValues.class).withAnyArguments().thenReturn(contentValueMock);

        PowerMockito.mockStatic(DbHelper.class, (Answer<Object>) invocation -> {
            if ("getInstance".equals(invocation.getMethod().getName())) {
                return helperMock;
            }
            fail();
            return null;
        });
        handlerUnderTest = new DbDataHandler();
    }

    @Test
    public void insert() {
        // given
        ArgumentCaptor<ContentValues> captor = ArgumentCaptor.forClass(ContentValues.class);
        // when
        handlerUnderTest.insert(new DataBaseStorableTestImpl());
        // then
        Mockito.verify(helperMock).insert(captor.capture());
        ContentValues captured = captor.getValue();

        Assert.assertEquals(DataBaseStorableTestImpl.DATA_ID ,captured.get(DbHelper.aDB_COLUMN_ID));
        Assert.assertEquals(DataBaseStorableTestImpl.DATA_STRING ,captured.get(DbHelper.aDB_COLUMN_JSONDATA));
        Assert.assertEquals(DataBaseStorableTestImpl.DATA_TYPE ,captured.get(DbHelper.aDB_COLUMN_TYPE));
        Assert.assertEquals(DataBaseStorableTestImpl.VERSION_NO ,captured.get(DbHelper.aDB_COLUMN_TYPEVERSION));
    }

    @Test
    public void deleteObject() {
        //given
        DbDataHandler handlerSpy = PowerMockito.spy(handlerUnderTest);
        //when
        handlerSpy.delete(new DataBaseStorableTestImpl());
        //then
        Mockito.verify(handlerSpy).delete(ArgumentMatchers.any(DatabaseStorable.class));
    }

    @Test
    public void reInitDatabase() {
        //given setup
        //when
        handlerUnderTest.reInitDatabase();
        //then
        Mockito.verify(helperMock).deleteAndReinit();
    }

    @Test
    public void updateSingle() {
        //given
        DatabaseStorable storable = new DataBaseStorableTestImpl();
        //when
        handlerUnderTest.update(storable);
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(helperMock).update(ArgumentMatchers.any(ContentValues.class),captor.capture(),isNullStringArray());
        assertTrue(captor.getValue().contains("ID='"));
        assertTrue(captor.getValue().contains(storable.getId()));
    }

    @Ignore ("TODO needs to be implemented")
    @Test
    public void read() {
        // TODO to be tested, cursor needs to be mocked...
        //given
        //when
        //then
    }

    @Test
    public void updateList() {
        //given
        ArrayList<DatabaseStorable> storables = new ArrayList<>();
        storables.add(new DataBaseStorableTestImpl());
        storables.add(new DataBaseStorableTestImpl());
        DbDataHandler handler = PowerMockito.spy(handlerUnderTest);
        //when
        handler.update(storables);
        //then
        Mockito.verify(handler, Mockito.times(2)).update(ArgumentMatchers.any(DatabaseStorable.class));

    }

    @Test
    public void deleteObjectId() {
        //given
        DatabaseStorable storable = new DataBaseStorableTestImpl();
        ArgumentCaptor<String> selectionCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String[]> selArgsCaptor = ArgumentCaptor.forClass(String[].class);
        //when
        handlerUnderTest.delete(storable.getId());
        //then
        Mockito.verify(helperMock).delete(selectionCaptor.capture(), selArgsCaptor.capture());
        Assert.assertEquals("ID LIKE ?", selectionCaptor.getValue());
        Assert.assertArrayEquals(new String[]{storable.getId()}, selArgsCaptor.getValue());

    }

    @Test
    public void contentValueToDatabaseStorableTest(){
        // given
        final int COLUMN_TYPE = 0;
        final int COLUMN_ID = 1;
        final int COLUMN_JSONDATA = 2;
        final int COLUMN_TYPEVERSION = 3;
        Cursor cursor = PowerMockito.mock(Cursor.class);
        PowerMockito.when(cursor.getColumnIndex(ArgumentMatchers.matches(DbHelper.aDB_COLUMN_TYPE))).thenReturn(COLUMN_TYPE);
        PowerMockito.when(cursor.getColumnIndex(ArgumentMatchers.matches(DbHelper.aDB_COLUMN_ID))).thenReturn(COLUMN_ID);
        PowerMockito.when(cursor.getColumnIndex(ArgumentMatchers.matches(DbHelper.aDB_COLUMN_JSONDATA))).thenReturn(COLUMN_JSONDATA);
        PowerMockito.when(cursor.getColumnIndex(ArgumentMatchers.matches(DbHelper.aDB_COLUMN_TYPEVERSION))).thenReturn(COLUMN_TYPEVERSION);
        DataBaseStorableTestImpl testImpl = new DataBaseStorableTestImpl();

        PowerMockito.when(cursor.getString(COLUMN_TYPE)).thenReturn(testImpl.getType());
        PowerMockito.when(cursor.getString(COLUMN_ID)).thenReturn(testImpl.getId());
        PowerMockito.when(cursor.getString(COLUMN_JSONDATA)).thenReturn(testImpl.getDataString());
        PowerMockito.when(cursor.getInt(COLUMN_TYPEVERSION)).thenReturn(testImpl.getVersion());
        // when
        DatabaseStorable storable = handlerUnderTest.contentValueToDatabaseStorable(cursor);
        // then
        Assert.assertEquals(testImpl.getType(),storable.getType());
        Assert.assertEquals(testImpl.getId(),storable.getId());
        Assert.assertEquals(testImpl.getDataString(),storable.getDataString());
        Assert.assertEquals(testImpl.getVersion(),storable.getVersion());
    }


    private String[] isNullStringArray() {
        ThreadSafeMockingProgress.mockingProgress().getArgumentMatcherStorage().reportMatcher(Null.NULL);
        return new String[]{};
    }


    /**
     * wrapper class for ContentValue mock
     */
    private static class ContentValuesImpl {

        final HashMap<String, Object> map = new HashMap<>();

        public void put(String key, Object value){
            map.put(key,value);
        }

        public Object get(String key){
            return map.get(key);
        }
    }

}