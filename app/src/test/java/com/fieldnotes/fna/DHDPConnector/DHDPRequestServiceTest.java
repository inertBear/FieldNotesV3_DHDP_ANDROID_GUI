package com.fieldnotes.fna.DHDPConnector;

import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DHDPRequestServiceTest {

    private static final String CREATOR_KEY = "CREATOR";
    private static final String ORGANIZATION_KEY = "ORGANIZATION";
    private static final String REQUEST_TYPE_KEY = "REQUEST_TYPE";
    private static final String ORIGINATOR_KEY = "ORIGINATOR";
    private static final String RECIPIENT_KEY = "RECIPIENT";
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
    public void sendRequestTest() {
        //TODO
    }
    
    @Test
    public void mergeHeaderAndBodyTest() throws Exception {
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

        JSONObject preparedRequest = DHDPRequestService.getInstance().merge(header, body);

        assertEquals(USERNAME, preparedRequest.get(CREATOR_KEY));
        assertEquals(ORGANIZATION, preparedRequest.get(ORGANIZATION_KEY));
        assertEquals(TYPE, preparedRequest.get(REQUEST_TYPE_KEY));
        assertEquals(ORIGINATOR, preparedRequest.get(ORIGINATOR_KEY));
        assertEquals(RECIPIENT, preparedRequest.get(RECIPIENT_KEY));
        assertEquals(USERNAME, preparedRequest.get(USERNAME_KEY));
        assertEquals(PASSWORD, preparedRequest.get(PASSWORD_KEY));
    }

    private class DHDPTestBody extends DHDPBody {

        DHDPTestBody(Map<String, Object> map) {
            super(map);
        }
    }
}