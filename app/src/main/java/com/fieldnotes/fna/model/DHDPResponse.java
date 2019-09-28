package com.fieldnotes.fna.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Models a response received from FNP
 */

public class DHDPResponse {
    public FNResponseType mResponseType;
    public String mMessage;
    public String mToken;
    public ArrayList<HashMap<String, String>> mResultList;
    public Date mTimestamp;
    public Map<String, String> mMetadata;

    public DHDPResponse(final DHDPResponse.Builder builder) {
        mResponseType = builder.responseType;
        mMessage = builder.message;
        mToken = builder.token;
        mResultList = builder.resultList;
        mTimestamp = builder.timestamp;
        mMetadata = builder.metadata;
    }

    public static DHDPResponse.Builder newBuilder() {
        return new DHDPResponse.Builder();
    }

    public static DHDPResponse.Builder newBuilder(final DHDPResponse copy) {
        return new DHDPResponse.Builder(copy);
    }

    public FNResponseType getResponseType() {
        return mResponseType;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getToken() {
        return mToken;
    }

    public ArrayList<HashMap<String, String>> getResultList() {
        return mResultList;
    }

    public Date getTimestamp() {
        return mTimestamp;
    }

    public Map<String, String> getMetadata() {
        return mMetadata;
    }

    public static final class Builder {
        private FNResponseType responseType;
        private String message;
        private String token;
        private ArrayList<HashMap<String, String>> resultList;
        private Date timestamp;
        private Map<String, String> metadata;

        private Builder() {
        }

        public Builder(final DHDPResponse copy) {
            responseType = copy.mResponseType;
            message = copy.mMessage;
            token = copy.mToken;
            resultList = copy.mResultList;
            timestamp = copy.mTimestamp;
            metadata = copy.mMetadata;
        }

        public DHDPResponse.Builder setStatustype(final FNResponseType responseType) {
            this.responseType = responseType;
            return this;
        }

        public DHDPResponse.Builder setMessage(final String message) {
            this.message = message;
            return this;
        }

        public DHDPResponse.Builder setToken(final String token) {
            this.token = token;
            return this;
        }

        public DHDPResponse.Builder setResultList(final ArrayList<HashMap<String, String>> resultList){
            this.resultList = resultList;
            return this;
        }

        // TODO: should be automatically set
        public DHDPResponse.Builder setTimestamp(final Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public DHDPResponse.Builder setMetadata(final Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        public DHDPResponse build() {
            return new DHDPResponse(this);
        }
    }
}
