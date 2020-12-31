package com.nepumuk.notizen.utils.db_access;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DbHelperTest {

    @Mock(name="mDB")
    SQLiteDatabase mDB;

    @InjectMocks
    DbHelper helper = new DbHelper(null,"",null,1);

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void update(){
        // given
        String whereClause = "";
        String[] whereArgs = new String[] {""};
        ContentValues values = mock(ContentValues.class);
        // when
        helper.update(values, whereClause, whereArgs);
        // then
        verify(mDB,times(1)).update(anyString(),eq(values),eq(whereClause),eq(whereArgs));
    }

    @Test
    public void delete(){
        // given
        String selection = "";
        String[] whereArgs = new String[] {""};
        // when
        helper.delete(selection, whereArgs);
        // then
        verify(mDB,times(1)).delete(anyString(),eq(selection),eq(whereArgs));
    }

    @Test
    public void onCreate(){
        // given
        // when
        helper.onCreate(mDB);
        // then
        verify(mDB,times(1)).execSQL(eq(DbHelper.SQL_CREATE_ENTRIES));
    }

    @Test
    @Ignore("get rid of the log warn")
    public void deleteAndReInit(){
        // given
        // when
        helper.deleteAndReinit();
        // then
        verify(mDB,times(1)).execSQL(eq(DbHelper.SQL_DELETE_ENTRIES));
        verify(mDB,times(1)).execSQL(eq(DbHelper.SQL_CREATE_ENTRIES));
    }

    @Test
    public void create(){
        // given
        ContentValues values = mock(ContentValues.class);
        // when
        helper.insert(values);
        // then
        verify(mDB,times(1)).insert(anyString(),any(),eq(values));
    }

    @Test
    public void onUpdate(){
        // given
        int previousVersion = 1;
        int newVersion = 2;
        // when
        helper.onUpgrade(mDB,previousVersion,newVersion);
        // then
        // currently does nothing
    }

    @Test
    @Ignore("get rid of the log warn")
    public void getAll(){
        // given
        // when
        helper.getAll();
        // then
    }
}