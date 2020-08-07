package com.nepumuk.notizen.utils.db_access;

import android.content.ContentValues;
import android.database.Cursor;

import com.nepumuk.notizen.testutils.AndroidTest;
import com.nepumuk.notizen.testutils.DataBaseStorableTestImpl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.Null;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.progress.ThreadSafeMockingProgress.mockingProgress;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PrepareForTest({DbHelper.class, DbDataHandler.class})
public class DBDataHandlerTest extends AndroidTest {

    private DbHelper helperMock;
    private DbDataHandler handlerUnderTest;
    private ContentValuesImpl testImpl;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testImpl = new ContentValuesImpl();
        helperMock = mock(DbHelper.class);
        ContentValues contentValueMock = mock(ContentValues.class);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                return testImpl.get((String)invocation.getArgument(0));
            }
        }).when(contentValueMock).get(anyString());
        Answer<Object> answerOnPut = new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                testImpl.put((String)invocation.getArgument(0), invocation.getArgument(1));
                return null;
            }
        };
        doAnswer(answerOnPut).when(contentValueMock).put(anyString(), anyString());
        doAnswer(answerOnPut).when(contentValueMock).put(anyString(), anyInt());

        whenNew(ContentValues.class).withAnyArguments().thenReturn(contentValueMock);

        mockStatic(DbHelper.class, new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                if ("getInstance".equals(invocation.getMethod().getName())) {
                    return helperMock;
                }
                fail();
                return null;
            }
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
        verify(helperMock).insert(captor.capture());
        ContentValues captured = captor.getValue();

        assertEquals(DataBaseStorableTestImpl.DATA_ID ,captured.get(DbHelper.aDB_COLUMN_ID));
        assertEquals(DataBaseStorableTestImpl.DATA_STRING ,captured.get(DbHelper.aDB_COLUMN_JSONDATA));
        assertEquals(DataBaseStorableTestImpl.DATA_TYPE ,captured.get(DbHelper.aDB_COLUMN_TYPE));
        assertEquals(DataBaseStorableTestImpl.VERSION_NO ,captured.get(DbHelper.aDB_COLUMN_TYPEVERSION));
    }

    @Test
    public void deleteObject() {
        //given
        DbDataHandler handlerSpy = spy(handlerUnderTest);
        //when
        handlerSpy.delete(new DataBaseStorableTestImpl());
        //then
        verify(handlerSpy).delete(any(DatabaseStorable.class));
    }

    @Test
    public void reInitDatabase() {
        //given setup
        //when
        handlerUnderTest.reInitDatabase();
        //then
        verify(helperMock).deleteAndReinit();
    }

    @Test
    public void updateSingle() {
        //given
        DatabaseStorable storable = new DataBaseStorableTestImpl();
        //when
        handlerUnderTest.update(storable);
        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(helperMock).update(any(ContentValues.class),captor.capture(),isNullStringArray());
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
        DbDataHandler handler = spy(handlerUnderTest);
        //when
        handler.update(storables);
        //then
        verify(handler, times(2)).update(any(DatabaseStorable.class));

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
        verify(helperMock).delete(selectionCaptor.capture(), selArgsCaptor.capture());
        assertEquals("ID LIKE ?", selectionCaptor.getValue());
        assertArrayEquals(new String[]{storable.getId()}, selArgsCaptor.getValue());

    }

    @Test
    public void contentValueToDatabaseStorableTest(){
        // given
        final int COLUMN_TYPE = 0;
        final int COLUMN_ID = 1;
        final int COLUMN_JSONDATA = 2;
        final int COLUMN_TYPEVERSION = 3;
        Cursor cursor = mock(Cursor.class);
        when(cursor.getColumnIndex(matches(DbHelper.aDB_COLUMN_TYPE))).thenReturn(COLUMN_TYPE);
        when(cursor.getColumnIndex(matches(DbHelper.aDB_COLUMN_ID))).thenReturn(COLUMN_ID);
        when(cursor.getColumnIndex(matches(DbHelper.aDB_COLUMN_JSONDATA))).thenReturn(COLUMN_JSONDATA);
        when(cursor.getColumnIndex(matches(DbHelper.aDB_COLUMN_TYPEVERSION))).thenReturn(COLUMN_TYPEVERSION);
        DataBaseStorableTestImpl testImpl = new DataBaseStorableTestImpl();

        when(cursor.getString(COLUMN_TYPE)).thenReturn(testImpl.getType());
        when(cursor.getString(COLUMN_ID)).thenReturn(testImpl.getId());
        when(cursor.getString(COLUMN_JSONDATA)).thenReturn(testImpl.getDataString());
        when(cursor.getInt(COLUMN_TYPEVERSION)).thenReturn(testImpl.getVersion());
        // when
        DatabaseStorable storable = handlerUnderTest.contentValueToDatabaseStorable(cursor);
        // then
        assertEquals(testImpl.getType(),storable.getType());
        assertEquals(testImpl.getId(),storable.getId());
        assertEquals(testImpl.getDataString(),storable.getDataString());
        assertEquals(testImpl.getVersion(),storable.getVersion());
    }


    private String[] isNullStringArray() {
        mockingProgress().getArgumentMatcherStorage().reportMatcher(Null.NULL);
        return new String[]{};
    }


    /**
     * wrapper class for ContentValue mock
     */
    private static class ContentValuesImpl {

        HashMap<String, Object> map = new HashMap<>();

        public void put(String key, Object value){
            map.put(key,value);
        }

        public Object get(String key){
            return map.get(key);
        }
    }

}