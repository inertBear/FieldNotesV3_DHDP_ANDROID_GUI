package com.fieldnotes.fna.DHDPConnector;

import com.fieldnotes.fna.model.DHDPResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * TODO: block comment
 */
public class DHDPRequestService <T extends DHDPBody> {
    private static Logger mLogger = Logger.getLogger(DHDPHeader.class.getName());
    private static DHDPRequestService sInstance;

    private DHDPRequestService() {
    }

    public static DHDPRequestService getInstance() {
        if (sInstance == null) {
            sInstance = new DHDPRequestService();
        }
        return sInstance;
    }

    public DHDPResponse sendRequest(DHDPRequest request) {
        JSONObject requestJson = merge(request.getHeader(), request.getBody());
        if (requestJson != null) {
//            return jsonParser.createHttpRequest("http://localhost:8080", "POST", jsonObject);
        }
        return DHDPResponse.newBuilder()
                .setMessage("failed to send")
                .build();
    }

    public JSONObject merge(DHDPHeader header, DHDPBody body) {
        // add these two JSON objects to a new JSONObject
        JSONObject requestJson = null;
        try {
            requestJson = new JSONObject();
            requestJson.put("HEADER", header);
            requestJson.put("BODY", body);
        } catch (JSONException e) {
            mLogger.severe("Could not create Reponse JSONObject");
        }
        return requestJson;
    }
}
