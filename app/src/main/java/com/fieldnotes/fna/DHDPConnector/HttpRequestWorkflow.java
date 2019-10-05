package com.fieldnotes.fna.DHDPConnector;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Creates an HTTP request and parses the JSON values coming back from the PHP Server Code. Returns
 * a JSON object to the Fragments to be read
 */
class HttpRequestWorkflow {

    DHDPResponse createHttpRequest(String url, JSONObject request) throws JSONException {
        DHDPResponse dhdpResponse;
        JSONObject responseObject;
        InputStream mInputStream;

        String encodedString = encode(request.toString());
        try {
            // Create Request
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url + encodedString);

            // Execute Request
            HttpResponse httpResponse = httpClient.execute(httpPost);

            // Retrieve Response
            HttpEntity httpEntity = httpResponse.getEntity();
            mInputStream = httpEntity.getContent();

            // Read Response
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    mInputStream, StandardCharsets.ISO_8859_1), 8);
            StringBuilder responseStringBuilder = new StringBuilder();
            String strLine;
            while ((strLine = reader.readLine()) != null) {
                responseStringBuilder.append(strLine).append("\n");
            }
            mInputStream.close();

            // parse the string into JSON object
            responseObject = new JSONObject(responseStringBuilder.toString());

            // Create DHDPResponse from response
            dhdpResponse = DHDPResponse.newBuilder()
                    .setHeader(DHDPHeader.newBuilder()
                            .setCreator(request.getString(DHDPHeader.CREATOR_KEY))
                            .setOrganization(toOrganization(request.getString(DHDPHeader.ORGANIZATION_KEY)))
                            .setOriginator(toEntity(request.getString(DHDPHeader.RECIPIENT_KEY)))
                            .setRecipient(toEntity(request.getString(DHDPHeader.ORIGINATOR_KEY)))
                            .build())
                    .setStatus(toStatusType(responseObject.getString(DHDPResponse.STATUS_KEY)))
                    .setMessage(responseObject.getString(DHDPResponse.MESSAGE_KEY))
                    .setTimestamp((LocalDateTime) responseObject.get(DHDPResponse.TIMESTAMP_KEY))
                    .setResults(getResults(responseObject.getString(DHDPResponse.RESULT_KEY)))
                    .build();
        } catch (IOException | JSONException e) {
            dhdpResponse = DHDPResponse.newBuilder()
                    .setHeader(DHDPHeader.newBuilder()
                            .setCreator(request.getString(DHDPHeader.CREATOR_KEY))
                            .setOrganization(toOrganization(request.getString(DHDPHeader.ORGANIZATION_KEY)))
                            .setOriginator(DHDPEntity.DHDP)
                            .setRecipient(toEntity(request.getString(DHDPHeader.ORIGINATOR_KEY)))
                            .build())
                    .setStatus(DHDPResponseType.FAILURE)
                    .setMessage(e.getMessage())
                    .setTimestamp(LocalDateTime.now())
                    .setResults(null)
                    .build();
        }
        return dhdpResponse;
    }

    /**
     * Encode a String to BASE64
     *
     * @param value to encode
     * @return encoded value
     */
    String encode(String value) {
        byte[] encodedBytes = Base64.encodeBase64(value.getBytes());
        return new String(encodedBytes);
    }

    /**
     * Decode BASE64 to String
     *
     * @param value to decode
     * @return decoded value
     */
    String decode(String value) {
        byte[] decodedBytes = Base64.decodeBase64(value.getBytes());
        return new String(decodedBytes);
    }

    /**
     * Convert Status string to DHDPResponseType
     *
     * @param type to convert
     * @return type as a DHDPResponseType
     */
    DHDPResponseType toStatusType(String type) {
        return DHDPResponseType.valueOf(type);
    }

    /**
     * Convert organization string to DHDPOrganization
     *
     * @param organization to convert
     * @return type as a DHDPOrganization
     */
    DHDPOrganization toOrganization(String organization) {
        return DHDPOrganization.valueOf(organization);
    }

    /**
     * Convert entity string to DHDPResponseType
     *
     * @param entity to convert
     * @return type as a DHDPEntity
     */
    DHDPEntity toEntity(String entity) {
        return DHDPEntity.valueOf(entity);
    }

    /**
     * Collect results from the HTTPResponse.
     * A result may either be a JSON Array or a String.
     *
     * @param resultsString all results as a string
     * @return allResults as an ArrayList
     */
    ArrayList<HashMap<String, String>> getResults(String resultsString) {
        ArrayList<HashMap<String, String>> allResults = new ArrayList<>();

        try {
            JSONArray results = new JSONArray(resultsString);
            // add all results to the resultList
            for (int i = 0; i < results.length(); i++) {
                // put the individual result into the map
                HashMap<String, String> singleResult = new HashMap<>();
                JSONObject singleObject = results.getJSONObject(i);

                // iterator through keys and add them to the map
                Iterator<String> keys = singleObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    singleResult.put(key, singleObject.get(key).toString());
                }
                // put the map in the list
                allResults.add(singleResult);
            }
        } catch (JSONException e) {
            // add the single result to the resultList
            HashMap<String, String> singleResult = new HashMap<>();
            singleResult.put(DHDPResponse.RESULT_KEY, resultsString);
            allResults.add(singleResult);
        }
        return allResults;
    }
}