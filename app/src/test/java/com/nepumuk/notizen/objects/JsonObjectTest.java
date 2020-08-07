package com.nepumuk.notizen.objects;

import com.nepumuk.notizen.testutils.AndroidTest;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class JsonObjectTest extends AndroidTest {

    private JsonObject object;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        object = new JsonObjectImpl();
    }

    @Test
    public void testToString() {
        assertEquals(object.toJson(),object.toString());
    }

    @Test
    public void testEquals() {
        assertNotEquals("test", object);
        assertEquals(object, new JsonObjectImpl());
        assertNotEquals(null, object);
        assertNotEquals(object, null);
        assertNotEquals(object, 1);
    }

    private static class JsonObjectImpl extends JsonObject {
        @JsonProperty("testProp")
        private String testProperty = "testproperty";

        JsonObjectImpl() {
            super();
        }
    }
}