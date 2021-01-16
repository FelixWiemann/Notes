package com.nepumuk.notizen.core.views.adapters;

import android.view.View;

import com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderFactory;

import org.junit.Ignore;
import org.junit.Test;

import static com.nepumuk.notizen.core.views.adapters.view_holders.ViewHolderFactory.UNKNOWN_TYPE;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

public class ViewHolderFactoryTest {

    @Test
    @Ignore("test in task")
    public void getNewViewHolderInstance() throws Exception {
        // given
       /* Integer type = R.layout.task_view;
        View v = mock(View.class);
        // when
        ViewHolderInterface inter = ViewHolderFactory.getNewViewHolderInstance(type,v);
        // then
        assertTrue(inter instanceof TaskViewHolder);*/
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNewViewHolderInstanceForUnknownType() throws Exception {
        // given
        Integer type = UNKNOWN_TYPE;
        View v = mock(View.class);
        // when; expecting error
        ViewHolderFactory.getNewViewHolderInstance(type,v);
        // then

    }

    @Test
    @Ignore("test in task")
    public void getTypeForClass() {
        // given
       /* Integer expectedType = R.layout.task_view;
        // when
        Integer actualType = ViewHolderFactory.getTypeForClass(BaseTask.class);
        // then
        assertEquals(expectedType, actualType);*/
    }

    @Test
    public void getTypeForSuperClass(){
        /*// given
        Integer expectedType = R.layout.task_view;
        // when
        Integer actualType = ViewHolderFactory.getTypeForClass(TestTask.class);
        // then
        assertEquals(expectedType, actualType);*/
    }

    @Test
    public void getTypeForUnknownClass(){
        // given
        Integer expectedType = UNKNOWN_TYPE;
        // when
        Integer actualType = ViewHolderFactory.getTypeForClass(Object.class);
        // then
        assertEquals(expectedType, actualType);
    }

/*
    static class TestTask extends BaseTask {

        /**
         * abstract method to implement in each inherited task type.
         * used for special deletion things
         * e.g. timed task: delete timer service associated with it
         *
        @Override
        public void deleteTask() {

        }

        @Override
        public int getVersion() {
            return 0;
        }
    }*/

}