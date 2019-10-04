package com.fieldnotes.fna.DHDPConnector;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * DHDPHeader defines the data that must be included in a Request or Response header.
 * Under the hood it is a JSON object, but from the outside it appears as a POJO.
 * <p>
 * This object is self-policing to enforce that all the fields required by DHDP are met.
 * <p>
 * NOTE: a DHDPHeader that is received by a client may contain a ResponseType,
 * but it cannot be set on the client side
 */
public class DHDPHeader extends JSONObject implements Header {

    //JSON DHDPHeader keys
    private static final String CREATOR_KEY = "CREATOR";
    private static final String ORGANIZATION_KEY = "ORGANIZATION";
    private static final String REQUEST_TYPE_KEY = "REQUEST_TYPE";
    private static final String RESPONSE_TYPE_KEY = "RESPONSE_TYPE";
    private static final String ORIGINATOR_KEY = "ORIGINATOR";
    private static final String RECIPIENT_KEY = "RECIPIENT";

    private static final Logger mLogger = Logger.getLogger(DHDPHeader.class.getName());
    private static final String BUILD_ERROR_MESSAGE = "DHDPHeaders require: \n- a creator\n- an organization\n- an originator\n- a recipient\n- and a request type";
    private static Set<String> mReservedKeys = new HashSet<>();

    /**
     * creates a JSON interpretation of a Header
     */
    private DHDPHeader(DHDPHeaderBuilder builder) throws JSONException {
        // create a JSON Header out of the builder's values
        put(CREATOR_KEY, builder.creator);
        put(ORGANIZATION_KEY, builder.organization);
        if (builder.requestType != null) {
            put(REQUEST_TYPE_KEY, builder.requestType);
        }
        put(ORIGINATOR_KEY, builder.originator);
        put(RECIPIENT_KEY, builder.recipient);
    }

    public static DHDPHeader.DHDPHeaderBuilder newBuilder() {
        return new DHDPHeader.DHDPHeaderBuilder();
    }

    /**
     * retrieve the value from the DHDPHeader by key
     * @param key to get value for
     * @return string interpretation of the value
     */
    private String getValue(String key){
        try {
            if (key.equals(REQUEST_TYPE_KEY) || key.equals(RESPONSE_TYPE_KEY)) {
                return get(key).toString();
            }
            return getString(key);
        } catch (JSONException e) {
            mLogger.severe("DHDPHeader does not contain " + key);
            return null;
        }
    }

    @Override
    public String getCreator() {
        return getValue(CREATOR_KEY);
    }

    @Override
    public String getOrganization() {
        return getValue(ORGANIZATION_KEY);
    }

    @Override
    public DHDPRequestType getRequestType() {
        return DHDPRequestType.valueOf(getValue(REQUEST_TYPE_KEY));
    }

    @Override
    public DHDPResponseType getResponseType() {
        return DHDPResponseType.valueOf(getValue(RESPONSE_TYPE_KEY));
    }

    @Override
    public String getOriginator() {
        return getValue(ORIGINATOR_KEY);
    }

    @Override
    public String getRecipient() {
        return getValue(RECIPIENT_KEY);
    }

    /**
     * Provides a list of keys that are reserved and cannot be used by DHDPBody
     *
     * @return list of reserved keys
     */
    static Set<String> getReservedKeys() {
        return mReservedKeys;
    }

    static {
        mReservedKeys.add(CREATOR_KEY);
        mReservedKeys.add(ORGANIZATION_KEY);
        mReservedKeys.add(REQUEST_TYPE_KEY);
        mReservedKeys.add(RESPONSE_TYPE_KEY);
        mReservedKeys.add(ORIGINATOR_KEY);
        mReservedKeys.add(RECIPIENT_KEY);
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
