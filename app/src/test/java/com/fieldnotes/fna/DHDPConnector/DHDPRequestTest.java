package com.fieldnotes.fna.DHDPConnector;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DHDPRequestTest {

    private static final String USERNAME_KEY = "USERNAME";
    private static final String PASSWORD_KEY = "PASSWORD";

    private static final String CREATOR = "USER";
    private static final DHDPOrganization ORGANIZATION = DHDPOrganization.DEVHUNTER;
    private static final DHDPRequestType TYPE = DHDPRequestType.LOGIN;
    private static final DHDPEntity ORIGINATOR = DHDPEntity.FieldNotes;
    private static final DHDPEntity RECIPIENT = DHDPEntity.DHDP;
    private static final String USERNAME = "USER";
    private static final String PASSWORD = "Password123!@#";

    @Test
    public void createDHDPRequestTest() {
        String expectedHeader = "{\"ORGANIZATION\":\"DEVHUNTER\",\"RECIPIENT\":\"DHDP\"," +
                "\"CREATOR\":\"USER\",\"REQUEST_TYPE\":\"LOGIN\",\"ORIGINATOR\":\"FieldNotes\"}";
        String expectedBody = "{\"PASSWORD\":\"Password123!@#\",\"USERNAME\":\"USER\"}";

        DHDPHeader header = DHDPHeader.newBuilder()
                .setCreator(CREATOR)
                .setOrganization(ORGANIZATION)
                .setRequestType(TYPE)
                .setOriginator(ORIGINATOR)
                .setRecipient(RECIPIENT)
                .build();

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(USERNAME_KEY, USERNAME);
        bodyMap.put(PASSWORD_KEY, PASSWORD);
        DHDPTestBody body = new DHDPTestBody(bodyMap);

        DHDPRequest request = DHDPRequest.newBuilder()
                .setHeader(header)
                .setBody(body)
                .build();

        assertEquals(expectedHeader, request.getHeader().toString());
        assertEquals(expectedBody, request.getBody().toString());
    }

    private class DHDPTestBody extends DHDPBody {

        DHDPTestBody(Map<String, Object> map) {
            super(map);
        }
    }
}