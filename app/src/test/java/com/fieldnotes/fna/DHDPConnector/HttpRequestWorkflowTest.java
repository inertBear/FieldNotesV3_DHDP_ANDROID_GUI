package com.fieldnotes.fna.DHDPConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class HttpRequestWorkflowTest {
    private static Logger mLogger = Logger.getLogger(DHDPHeader.class.getName());
    private HttpRequestWorkflow mWorkflow;

    @Before
    public void setup() {
        mWorkflow = new HttpRequestWorkflow();
    }

    @Test
    public void encodeDecodeTest() {
        String testString = "This string will be encoded";
        String encodedString = mWorkflow.encode(testString);
        mLogger.info("Encoded String = " + encodedString);
        assertEquals(testString, mWorkflow.decode(encodedString));
    }

    @Test
    public void toStatusTypeTest() {
        assertEquals(DHDPResponseType.SUCCESS, mWorkflow.toStatusType("SUCCESS"));
        assertEquals(DHDPResponseType.FAILURE, mWorkflow.toStatusType("FAILURE"));
    }

    @Test
    public void toOrganizationTest() {
        assertEquals(DHDPOrganization.DEVHUNTER, mWorkflow.toOrganization("DEVHUNTER"));
    }

    @Test
    public void toEntityTest() {
        assertEquals(DHDPEntity.DHDP, mWorkflow.toEntity("DHDP"));
        assertEquals(DHDPEntity.DHDPConnector, mWorkflow.toEntity("DHDPConnector"));
    }

    @Test
    public void getResultsStringTest() {
        String testResult = "This result was returned as a String";
        ArrayList<HashMap<String, String>> results = mWorkflow.getResults(testResult);
        HashMap<String, String> result = results.get(0);
        assertEquals(testResult, result.get(DHDPResponse.RESULT_KEY));
    }

    @Test
    public void getResultsJSONArrayTest() throws JSONException {
        String usernameKey = "USERNAME";
        String usernameValue = "USER";
        String passwordKey = "PASSWORD";
        String passwordValue = "Password123!@#";
        String resultKey = "RESULT";
        String resultValue = "SUCCESS";
        String messageKey = "MESSAGE";
        String messageValue = "Logged In";

        // create 2 JSONObjects
        JSONObject object1 = new JSONObject();
        object1.put(usernameKey, usernameValue);
        object1.put(passwordKey, passwordValue);
        JSONObject object2 = new JSONObject();
        object2.put(resultKey, resultValue);
        object2.put(messageKey, messageValue);
        // form them into a JSONArray
        JSONArray array = new JSONArray();
        array.put(object1);
        array.put(object2);

        ArrayList<HashMap<String, String>> results = mWorkflow.getResults(array.toString());

        HashMap<String, String> result = results.get(0);
        assertEquals(usernameValue, result.get(usernameKey));
        assertEquals(passwordValue, result.get(passwordKey));
        result = results.get(1);
        assertEquals(resultValue, result.get(resultKey));
        assertEquals(messageValue, result.get(messageKey));
    }
}