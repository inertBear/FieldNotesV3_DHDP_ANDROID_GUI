package com.fieldnotes.fna.parser;

import com.fieldnotes.fna.model.DHDPResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Creates an HTTP request and parses the JSON values coming back from the PHP Server Code. Returns
 * a JSON object to the Fragments to be read
 * <p>
 * Created on 5/2/2018.
 */

public class JSONParser {

    public DHDPResponse createHttpRequest(String url, String method, JSONObject jsonObject) {
        // Make new HTTP request
        InputStream mInputStream = null;
        JSONObject mJsonObj = null;
        String mJsonString = "";

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            url += "?" + encode(jsonObject.toString());
            // checking request method
            if (method.equals("POST")) {
                HttpPost httpPost = new HttpPost(url);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                mInputStream = httpEntity.getContent();
            } else if (method.equals("GET")) {
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                mInputStream = httpEntity.getContent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("POST exception: " + "An exception in POST");
        }

        try {
            // read response data and build string
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    mInputStream, StandardCharsets.ISO_8859_1), 8);
            StringBuilder str = new StringBuilder();
            String strLine;
            while ((strLine = reader.readLine()) != null) {
                str.append(strLine).append("\n");
            }
            mInputStream.close();
            mJsonString = str.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BufferedReader: no results received from the server");
        }

        // parse the string into JSON object
        try {
            mJsonObj = new JSONObject(mJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("JSON Parse: there was an error in parsing the JSON");
        }
//        return mJsonObj;
        return null;
    }

    public String encode(String encodeMe) {
        byte[] encodedBytes = Base64.encodeBase64(encodeMe.getBytes());
        return new String(encodedBytes);
    }
}