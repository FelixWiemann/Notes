package com.nepumuk.notizen.core.utils.db_access;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

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
        ContentValues values = Mockito.mock(ContentValues.class);
        // when
        helper.update(values, whereClause, whereArgs);
        // then
        Mockito.verify(mDB, Mockito.times(1)).update(ArgumentMatchers.anyString(), ArgumentMatchers.eq(values), ArgumentMatchers.eq(whereClause), ArgumentMatchers.eq(whereArgs));
    }

    @Test
    public void delete(){
        // given
        String selection = "";
        String[] whereArgs = new String[] {""};
        // when
        helper.delete(selection, whereArgs);
        // then
        Mockito.verify(mDB, Mockito.times(1)).delete(ArgumentMatchers.anyString(), ArgumentMatchers.eq(selection), ArgumentMatchers.eq(whereArgs));
    }

    @Test
    public void onCreate(){
        // given
        // when
        helper.onCreate(mDB);
        // then
        Mockito.verify(mDB, Mockito.times(1)).execSQL(ArgumentMatchers.eq(DbHelper.SQL_CREATE_ENTRIES));
    }

    @Test
    @Ignore("get rid of the log warn")
    public void deleteAndReInit(){
        // given
        // when
        helper.deleteAndReinit();
        // then
        Mockito.verify(mDB, Mockito.times(1)).execSQL(ArgumentMatchers.eq(DbHelper.SQL_DELETE_ENTRIES));
        Mockito.verify(mDB, Mockito.times(1)).execSQL(ArgumentMatchers.eq(DbHelper.SQL_CREATE_ENTRIES));
    }

    @Test
    public void create(){
        // given
        ContentValues values = Mockito.mock(ContentValues.class);
        // when
        helper.insert(values);
        // then
        Mockito.verify(mDB, Mockito.times(1)).insert(ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.eq(values));
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
    public void getAll(){
        // given
        // when
        helper.getAll();
        // then
    }
}