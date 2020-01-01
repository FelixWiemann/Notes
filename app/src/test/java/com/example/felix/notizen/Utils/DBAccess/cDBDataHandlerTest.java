package com.example.felix.notizen.Utils.DBAccess;

import android.content.ContentValues;

import com.example.felix.notizen.testutils.AndroidTest;
import com.example.felix.notizen.testutils.DataBaseStorableTestImpl;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.progress.ThreadSafeMockingProgress.mockingProgress;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PrepareForTest({cDBHelper.class,cDBDataHandler.class})
public class cDBDataHandlerTest extends AndroidTest {

    private cDBHelper helperMock;
    private cDBDataHandler handlerUnderTest;
    private ContentValuesImpl testImpl;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testImpl = new ContentValuesImpl();
        helperMock = mock(cDBHelper.class);
        ContentValues contentValueMock = mock(ContentValues.class);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                return testImpl.get((String)invocation.getArgument(0));
            }
        }).when(contentValueMock).get(anyString());
        Answer answerOnPut = new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                testImpl.put((String)invocation.getArgument(0), invocation.getArgument(1));
                return null;
            }
        };
        doAnswer(answerOnPut).when(contentValueMock).put(anyString(), anyString());
        doAnswer(answerOnPut).when(contentValueMock).put(anyString(), anyInt());

        whenNew(ContentValues.class).withAnyArguments().thenReturn(contentValueMock);

        mockStatic(cDBHelper.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                switch (invocation.getMethod().getName()){
                    case "getInstance":
                        return helperMock;
                }
                fail();
                return null;
            }
        });
        handlerUnderTest = new cDBDataHandler();
    }

    @Test
    public void insert() {
        // given
        ArgumentCaptor captor = ArgumentCaptor.forClass(cDBHelper.class);
        // when
        handlerUnderTest.insert(new DataBaseStorableTestImpl());
        // then
        verify(helperMock).insert((ContentValues) captor.capture());
        ContentValues captured = (ContentValues) captor.getValue();

        assertEquals(DataBaseStorableTestImpl.DATA_ID ,captured.get(cDBHelper.aDB_COLUMN_ID));
        assertEquals(DataBaseStorableTestImpl.DATA_STRING ,captured.get(cDBHelper.aDB_COLUMN_JSONDATA));
        assertEquals(DataBaseStorableTestImpl.DATA_TYPE ,captured.get(cDBHelper.aDB_COLUMN_TYPE));
        assertEquals(DataBaseStorableTestImpl.VERSION_NO ,captured.get(cDBHelper.aDB_COLUMN_TYPEVERSION));
    }

    @Test
    public void deleteObject() {
        //given
        cDBDataHandler handlerSpy = spy(handlerUnderTest);
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
        cDBDataHandler handler = spy(handlerUnderTest);
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


    /**
     *
     * @return
     */
    private String[] isNullStringArray() {
        mockingProgress().getArgumentMatcherStorage().reportMatcher(Null.NULL);
        return new String[]{};
    }


    /**
     * wrapper class for ContentValue mock
     */
    class ContentValuesImpl {

        HashMap<String, Object> map = new HashMap<>();

        public void put(String key, Object value){
            map.put(key,value);
        }

        public Object get(String key){
            return map.get(key);
        }
    }

}