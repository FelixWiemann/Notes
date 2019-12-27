package com.example.felix.notizen.objects;

import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class cJSONObjectTest extends AndroidTest {

    private cJSONObject object;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        object = new cJSONObjectImpl();
    }

    @Test
    public void testToString() {
        assertEquals(object.toJson(),object.toString());
    }

    @Test
    public void testEquals() {
        assertNotEquals("test", object);
        assertEquals(object, new cJSONObjectImpl());
        assertNotEquals(null, object);
        assertNotEquals(object, null);
        assertNotEquals(object, 1);
    }

    private class cJSONObjectImpl extends cJSONObject{
        cJSONObjectImpl() {
        }
    }
}