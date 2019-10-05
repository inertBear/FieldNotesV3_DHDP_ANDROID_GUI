package com.fieldnotes.fna.DHDPConnector;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Models a response received from DHDP
 */
public class DHDPResponse {
    //JSON DHDPResponse keys
    static final String STATUS_KEY = "STATUS";
    static final String MESSAGE_KEY = "MESSAGE";
    static final String TIMESTAMP_KEY = "TIMESTAMP";
    static final String RESULT_KEY = "RESULT";

    private DHDPHeader mResponseHeader;
    private DHDPResponseType mResponseType;
    private String mMessage;
    private ArrayList<HashMap<String, String>> mResultList;
    private LocalDateTime mTimestamp;

    private DHDPResponse(final DHDPResponse.Builder builder) {
        mResponseHeader = builder.header;
        mResponseType = builder.responseType;
        mMessage = builder.message;
        mResultList = builder.resultList;
        mTimestamp = builder.timestamp;
    }

    static DHDPResponse.Builder newBuilder() {
        return new DHDPResponse.Builder();
    }

    public DHDPHeader getHeader() {
        return mResponseHeader;
    }

    public DHDPResponseType getResponseType() {
        return mResponseType;
    }

    public String getMessage() {
        return mMessage;
    }

    public ArrayList<HashMap<String, String>> getResults() {
        return mResultList;
    }

    public LocalDateTime getTimestamp() {
        return mTimestamp;
    }

    static final class Builder {
        private DHDPHeader header;
        private DHDPResponseType responseType;
        private String message;
        private ArrayList<HashMap<String, String>> resultList;
        private LocalDateTime timestamp;

        private Builder() {
        }

        DHDPResponse.Builder setHeader(final DHDPHeader header) {
            this.header = header;
            return this;
        }

        DHDPResponse.Builder setStatus(final DHDPResponseType responseType) {
            this.responseType = responseType;
            return this;
        }

        DHDPResponse.Builder setMessage(final String message) {
            this.message = message;
            return this;
        }

        DHDPResponse.Builder setResults(final ArrayList<HashMap<String, String>> resultList) {
            this.resultList = resultList;
            return this;
        }

        DHDPResponse.Builder setTimestamp(final LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        DHDPResponse build() {
            return new DHDPResponse(this);
        }
    }
}
