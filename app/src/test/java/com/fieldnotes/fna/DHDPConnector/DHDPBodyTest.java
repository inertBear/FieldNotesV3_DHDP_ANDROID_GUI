package com.fieldnotes.fna.DHDPConnector;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DHDPBodyTest {
    private String STRING_KEY = "STRING_KEY_1";
    private String STRING_VALUE = "STRING_VALUE";
    private String INT_KEY = "INT_KEY";
    private int INT_VALUE = 12;
    private String LOCALDATETIME_KEY = "DATETIME_VALUE";
    private LocalDateTime LOCALDATETIME_VALUE = LocalDateTime.now();

    @Test
    public void createDHDPBodyTest() {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(STRING_KEY, STRING_VALUE);
        bodyMap.put(INT_KEY, INT_VALUE);
        bodyMap.put(LOCALDATETIME_KEY, LOCALDATETIME_VALUE);
        DHDPTestBody body = new DHDPTestBody(bodyMap);

        assertEquals(STRING_VALUE, body.get(STRING_KEY));
        assertEquals(INT_VALUE, body.get(INT_KEY));
        assertEquals(LOCALDATETIME_VALUE, body.get(LOCALDATETIME_KEY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void mergeHeaderAndBodyTestWithDuplicateKeys() throws Exception {
        String CREATOR_KEY = "CREATOR";
        String CREATOR_VALUE = "USER";

        Set<String> reservedKeys = DHDPHeader.getReservedKeys();
        assertTrue(reservedKeys.contains(CREATOR_KEY));

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(CREATOR_KEY, CREATOR_VALUE);
        new DHDPTestBody(bodyMap);
    }

    @Test
    public void getStringTest() {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(STRING_KEY, STRING_VALUE);
        DHDPTestBody body = new DHDPTestBody(bodyMap);

        assertEquals(STRING_VALUE, body.getString(STRING_KEY));
    }

    @Test
    public void getIntTest() {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(INT_KEY, INT_VALUE);
        DHDPTestBody body = new DHDPTestBody(bodyMap);

        assertEquals(INT_VALUE, body.getInt(INT_KEY));
    }

    @Test
    public void getLdtTest() {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(LOCALDATETIME_KEY, LOCALDATETIME_VALUE);
        DHDPTestBody body = new DHDPTestBody(bodyMap);

        assertEquals(LOCALDATETIME_VALUE, body.getLDT(LOCALDATETIME_KEY));
    }

    @Test(expected = ClassCastException.class)
    public void getWrongTypeTest() {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(STRING_KEY, STRING_VALUE);
        DHDPTestBody body = new DHDPTestBody(bodyMap);

        assertEquals(STRING_VALUE, body.getInt(STRING_KEY));
    }

    private class DHDPTestBody extends DHDPBody {

        DHDPTestBody(Map<String, Object> map) {
            super(map);
        }
    }
}