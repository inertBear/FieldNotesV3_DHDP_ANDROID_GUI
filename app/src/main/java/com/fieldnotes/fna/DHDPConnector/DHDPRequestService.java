package com.fieldnotes.fna.DHDPConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * This service routes single shot requests to DHDP. Each request will
 * result in a DHDPResponse object
 */
public class DHDPRequestService {
    private static Logger mLogger = Logger.getLogger(DHDPHeader.class.getName());
    private static DHDPRequestService sInstance;
    private HttpRequestWorkflow mRequestParser;
    private String mDhdpUrl = "http://localhost:8080?";

    private DHDPRequestService() {
        mRequestParser = new HttpRequestWorkflow();
    }

    public static DHDPRequestService getInstance() {
        if (sInstance == null) {
            sInstance = new DHDPRequestService();
        }
        return sInstance;
    }

    public DHDPResponse sendRequest(DHDPRequest request) {
        DHDPResponse response;
        try {
            JSONObject preparedRequest = merge(request.getHeader(), request.getBody());
            response = mRequestParser.createHttpRequest(mDhdpUrl, preparedRequest);
        } catch (JSONException e) {
            DHDPHeader header = request.getHeader();
            response = DHDPResponse.newBuilder()
                    .setHeader(DHDPHeader.newBuilder()
                            .setCreator(header.getCreator())
                            .setOrganization(header.getOrganization())
                            .setOriginator(DHDPEntity.DHDP)
                            .setRecipient(header.getOriginator())
                            .build())
                    .setStatus(DHDPResponseType.FAILURE)
                    .setMessage("Unable to send request")
                    .setTimestamp(LocalDateTime.now())
                    .setResults(null)
                    .build();
        }
        mLogger.info(response.toString());
        return response;
    }

    /**
     * Combine the header and body into one JSONObject
     *
     * @param header metadata for request
     * @param body   data for request
     * @return a JSONObject containing both the header's and the body's fields
     */
    JSONObject merge(DHDPHeader header, DHDPBody body) throws JSONException {
        JSONObject headerAndBody = new JSONObject();

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
        return headerAndBody;
    }
}
