package com.fieldnotes.fna.DHDPConnector;

import com.fieldnotes.fna.model.DHDPResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.logging.Logger;

/**
 * TODO: block comment
 */
public class DHDPRequestService {
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
        JSONObject preparedRequest = merge(request.getHeader(), request.getBody());
        if (preparedRequest != null) {
//            return jsonParser.createHttpRequest("http://localhost:8080", "POST", preparedRequest);
        }
        return DHDPResponse.newBuilder()
                .setMessage("failed to send")
                .build();
    }

    /**
     * Combine the header and body into one JSON Object
     * @param header metadata for request
     * @param body data for request
     * @return a JSONObject containing both the header's and the body's fields
     */
    JSONObject merge(DHDPHeader header, DHDPBody body) {
        JSONObject headerAndBody = null;
        try {
            headerAndBody = new JSONObject();

            // combine header and body
            Iterator iterator = header.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                headerAndBody.put(key, header.get(key));
            }
            iterator = body.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                headerAndBody.put(key, body.get(key));
            }
        } catch (JSONException e) {
            mLogger.severe("Could not combine header and body");
        }
        return headerAndBody;
    }
}
