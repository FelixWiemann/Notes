package com.example.felix.notizen.objects;

import com.example.felix.notizen.testutils.AndroidTest;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class cJSONObjectTest extends AndroidTest {

    private final String test_title = "test title";
    private UUID mId;
    private cJSONObject object;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mId = UUID.randomUUID();
        object = new cJSONObjectImpl(mId, test_title);
    }

    @Test
    public void testJsonParametersOfInheritedParameters(){
        String json = object.toJson();
        assertTrue(json.contains(test_title));
        assertTrue(json.contains(mId.toString()));
    }


    @Test
    public void testToString() {
        assertEquals(object.toJson(),object.toString());
    }

    @Test
    public void testEquals() {
        assertNotEquals("test", object);
        assertNotEquals(object, new cJSONObjectImpl(UUID.randomUUID(), test_title));
        assertNotEquals(object, new cJSONObjectImpl(mId, "") );
        assertEquals(object, new cJSONObjectImpl(mId, test_title));
    }

    private class cJSONObjectImpl extends cJSONObject{

        cJSONObjectImpl(UUID mId, String test_title) {
            super(mId,test_title);
        }
    }
}