package com.fieldnotes.fna.DHDPConnector;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * DHDPHeader defines the data that must be included in a Request or Response header.
 * Under the hood it is a JSON object, but from the outside it appears as a POJO.
 *
 * This object is self-policing to enforce that all the fields required by DHDP are met.
 *
 * NOTE: a DHDPHeader that is received by a client may contain a ResponseType,
 * but it cannot be set on the client side
 */
public class DHDPHeader extends JSONObject implements Header {

    //JSON DHDPHeader keys
    private static final String creatorKey = "Creator";
    private static final String organizationKey = "Organization";
    private static final String requestTypeKey = "RequestType";
    private static final String responseTypeKey = "ResponseType";
    private static final String originatorKey = "Originator";
    private static final String recipientKey = "Recipient";

    private static final Logger mLogger = Logger.getLogger(DHDPHeader.class.getName());
    private static final String BUILD_ERROR_MESSAGE = "DHDPHeaders require: \n- a creator\n- an organization\n- an originator\n- a recipient\n- and a request type";

    /**
     * creates a JSON interpretation of a Header
     */
    private DHDPHeader(DHDPHeaderBuilder builder) throws JSONException {
        // create a JSON Header out of the builder's values
        put(creatorKey, builder.creator);
        put(organizationKey, builder.organization);
        if (builder.requestType != null) {
            put(requestTypeKey, builder.requestType);
        }
        put(originatorKey, builder.originator);
        put(recipientKey, builder.recipient);
    }

    public static DHDPHeader.DHDPHeaderBuilder newBuilder() {
        return new DHDPHeader.DHDPHeaderBuilder();
    }

    @Override
    public String getCreator() {
        try {
            return get(creatorKey).toString();
        } catch (JSONException e) {
            mLogger.severe("DHDPHeader contains no creator");
            return null;
        }
    }

    @Override
    public String getOrganization() {
        try {
            return get(organizationKey).toString();
        } catch (JSONException e) {
            mLogger.severe("DHDPHeader contains no organization");
            return null;
        }
    }

    @Override
    public DHDPRequestType getRequestType() {
        try {
            return (DHDPRequestType) get(requestTypeKey);
        } catch (JSONException e) {
            mLogger.severe("DHDPHeader contains no RequestType");
            return null;
        }
    }

    @Override
    public DHDPResponseType getResponseType() {
        try {
            return (DHDPResponseType) get(responseTypeKey);
        } catch (JSONException e) {
            mLogger.severe("DHDPHeader contains no ResponseType");
            return null;
        }
    }

    @Override
    public String getOriginator() {
        try {
            return get(originatorKey).toString();
        } catch (JSONException e) {
            mLogger.severe("DHDPHeader contains no Originator");
            return null;
        }
    }

    @Override
    public String getRecipient() {
        try {
            return get(recipientKey).toString();
        } catch (JSONException e) {
            mLogger.severe("DHDPHeader contains no RequestType");
            return null;
        }
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static class DHDPHeaderBuilder {
        private String creator;
        private String organization;
        private DHDPRequestType requestType;
        private String originator;
        private String recipient;

        DHDPHeaderBuilder() {
        }

        public DHDPHeaderBuilder setCreator(String creator) {
            this.creator = creator;
            return this;
        }

        public DHDPHeaderBuilder setOrganization(String organization) {
            this.organization = organization;
            return this;
        }

        public DHDPHeaderBuilder setRequestType(DHDPRequestType requestType) {
            this.requestType = requestType;
            return this;
        }

        public DHDPHeaderBuilder setOriginator(String originator) {
            this.originator = originator;
            return this;
        }

        public DHDPHeaderBuilder setRecipient(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public DHDPHeader build() {
            try {
                if (creator == null || organization == null || originator == null || recipient == null || requestType == null) {
                    throw new IllegalArgumentException(BUILD_ERROR_MESSAGE);
                } else {
                    return new DHDPHeader(this);
                }
            } catch (JSONException e) {
                mLogger.severe("Could not create DHDPHeader: " + e.toString());
                return null;
            }
        }
    }
}
