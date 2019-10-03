package com.fieldnotes.fna.service;

import com.fieldnotes.fna.model.DHDPHeader;
import com.fieldnotes.fna.model.DHDPResponse;
import com.fieldnotes.fna.model.FNRequest;
import com.fieldnotes.fna.model.DHDPResponseType;
import com.fieldnotes.fna.model.FieldNote;
import com.fieldnotes.fna.parser.JSONParser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * Service for sending requests to FNP
 */
public class DHDPRequestService {
    private static Logger mLogger = Logger.getLogger(DHDPHeader.class.getName());
    private static DHDPRequestService sInstance;

    private DHDPRequestService() {
    }

    public static DHDPRequestService getInstance() {
        if (sInstance == null) {
            return new DHDPRequestService();
        } else {
            return sInstance;
        }
    }

    public DHDPResponse sendRequest(FNRequest request) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = convertRequestToJson(request);
        if (jsonObject != null) {
            return jsonParser.createHttpRequest("http://localhost:8080", "POST", jsonObject);
        }
        return DHDPResponse.newBuilder()
                .setMessage("fail")
                .setMessage("failed to send")
                .build();
    }

    private JSONObject convertRequestToJson(FNRequest request) {
        try {
            return mergeJson(request.getHeader(), request.getFieldNote());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject mergeJson(DHDPHeader header, FieldNote body) throws JSONException {
        Gson gson = new Gson();
        // convert header to JSON
        String sourceString = gson.toJson(header);
        JSONObject headerJson = new JSONObject(sourceString);
        // convert body to JSON
        String bodyString = gson.toJson(body);
        JSONObject bodyJson = new JSONObject(bodyString);

        // add these two JSON objects to a new JSONObject
        JSONObject requestJson = new JSONObject();
//        ex:
//        request {
//            header {
//                1 {
//                },
//                2 {
//                },
//            },
//            body {
//                1 {
//                },
//                2 {
//                }
//            }
//        }


        return null;
    }

//    public static DHDPResponse sendRequest(FNRequest request) throws JSONException {
//        JSONParser mJsonParser = new JSONParser();
//        JSONObject json;
//
//        switch (request.getRequestType()) {
//            case LOGIN:
//                json = mJsonParser.createHttpRequest(LOGIN_URL, HTTP_REQUEST_METHOD_POST, request.getRequestParams());
//
//                if (json.getString(RESPONSE_TOKEN_TAG) != null) {
//                    return DHDPResponse.newBuilder()
//                            .setStatustype(convertResponseType(json.getString(RESPONSE_STATUS_TAG)))
//                            .setMessage(json.getString(RESPONSE_MESSAGE_TAG))
//                            .setToken(json.getString(RESPONSE_TOKEN_TAG))
//                            .build();
//                } else {
//                    return DHDPResponse.newBuilder()
//                            .setStatustype(DHDPResponseType.FAILURE)
//                            .setMessage("No Login Token Received")
//                            .build();
//                }
//            case ADD:
//                json = mJsonParser.createHttpRequest(ADD_NOTE_URL, HTTP_REQUEST_METHOD_POST, request.getRequestParams());
//
//                return DHDPResponse.newBuilder()
//                        .setStatustype(convertResponseType(json.getString(RESPONSE_STATUS_TAG)))
//                        .setMessage(json.getString(RESPONSE_MESSAGE_TAG))
//                        .build();
//            case UPDATE:
//                json = mJsonParser.createHttpRequest(UPDATE_NOTE_URL, HTTP_REQUEST_METHOD_POST, request.getRequestParams());
//
//                return DHDPResponse.newBuilder()
//                        .setStatustype(convertResponseType(json.getString(RESPONSE_STATUS_TAG)))
//                        .setMessage(json.getString(RESPONSE_MESSAGE_TAG))
//                        .build();
//
//            case SEARCH:
//                json = mJsonParser.createHttpRequest(SEARCH_NOTES_URL, HTTP_REQUEST_METHOD_POST, request.getRequestParams());
//
//                DHDPResponse.Builder responseBuilder = DHDPResponse.newBuilder();
//                responseBuilder.setStatustype(convertResponseType(json.getString(RESPONSE_STATUS_TAG)));
//                if (json.getString(RESPONSE_STATUS_TAG).equals(RESPONSE_STATUS_SUCCESS)) {
//                    //get Json object that is inside of the 'message'
//                    JSONArray tickets = new JSONArray(json.getString(RESPONSE_MESSAGE_TAG));
//
//                    if (tickets.length() > 0) {
//                        ArrayList<HashMap<String, String>> allSearchResults = new ArrayList<>();
//                        //assign the strings to values
//                        for (int i = 0; i < tickets.length(); i++) {
//                            //create new HashMap on each loop, so the same keys can be re-used
//                            HashMap<String, String> singleResult = new HashMap<>();
//                            //get result
//                            JSONObject result = tickets.getJSONObject(i);
//                            // map the result
//                            singleResult.put(TICKET_NUMBER_TAG, result.getString(TICKET_NUMBER_TAG));
//                            singleResult.put(USER_TAG, result.getString(USER_TAG));
//                            singleResult.put(PROJECT_NUMBER_TAG, result.getString(PROJECT_NUMBER_TAG));
//                            singleResult.put(WELLNAME_TAG, result.getString(WELLNAME_TAG));
//                            singleResult.put(DESCRIPTION_TAG, result.getString(DESCRIPTION_TAG));
//                            singleResult.put(BILLING_TAG, result.getString(BILLING_TAG));
//                            singleResult.put(DATE_START_TAG, result.getString(DATE_START_TAG));
//                            singleResult.put(DATE_END_TAG, result.getString(DATE_END_TAG));
//                            singleResult.put(TIME_START_TAG, result.getString(TIME_START_TAG));
//                            singleResult.put(TIME_END_TAG, result.getString(TIME_END_TAG));
//                            singleResult.put(LOCATION_TAG, result.getString(LOCATION_TAG));
//                            singleResult.put(MILEAGE_START_TAG, result.getString(MILEAGE_START_TAG));
//                            singleResult.put(MILEAGE_END_TAG, result.getString(MILEAGE_END_TAG));
//                            singleResult.put(GPS_TAG, result.getString(GPS_TAG));
//                            //put HashMap into ArrayList
//                            allSearchResults.add(singleResult);
//                        }
//                        responseBuilder.setMessage("Search Complete");
//                        responseBuilder.setResultList(allSearchResults);
//                    } else {
//                        responseBuilder.setMessage(json.getString(RESPONSE_MESSAGE_TAG));
//                    }
//                } else {
//                    responseBuilder.setMessage(json.getString(RESPONSE_MESSAGE_TAG));
//                }
//
//                return responseBuilder.build();
//            default:
//                return DHDPResponse.newBuilder()
//                        .setStatustype(DHDPResponseType.FAILURE)
//                        .setMessage("Could not set Request Type")
//                        .build();
//        }
//    }

//    private static DHDPResponseType convertResponseType(String status) {
//        switch (status) {
//            case RESPONSE_STATUS_SUCCESS:
//                return DHDPResponseType.SUCCESS;
//            case RESPONSE_STATUS_FAILURE:
//            default:
//                return DHDPResponseType.FAILURE;
//        }
//    }
}
