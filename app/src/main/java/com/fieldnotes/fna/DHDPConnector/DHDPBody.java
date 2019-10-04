package com.fieldnotes.fna.DHDPConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Logger;

/**
 * DHDPBody defines the client data that DHDP will process.
 * Under the hood it is a JSON object, but from the outside it appears as a POJO.
 *
 * This object has no imposed restrictions, as the implementation of this class is the
 * responsibility of the client
 *
 * Accessor methods have been included to retrieve 'auto-casted' Objects for storage in a more
 * accessible field within the DHDPBody implementation
 */
public abstract class DHDPBody extends JSONObject {
    private static final Logger mLogger = Logger.getLogger(DHDPBody.class.getName());
    private Map<String, Object> mDHDPBodyMapping;

    public DHDPBody(Map<String, Object> bodyMap) throws IllegalArgumentException {
        mDHDPBodyMapping = bodyMap;
        try {
            for (String key : bodyMap.keySet()) {
                Object value = bodyMap.get(key);
                mLogger.info("Adding {" + key + ":" + value.toString() + "} to DHDPBody");
                put(key, value);
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("DHDPBody could not be created");
        }
    }

    /**
     * retrieves the values stored in the DHDPBody as a map
     * @return map of the values in the body
     */
    public Map<String, Object> getBody() {
        return mDHDPBodyMapping;
    }

    /**
     * retrieve String value from mapping
     * @param key
     * @return
     */
    public String getString(String key) {
        Object value = get(key);
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    /**
     * retrieves int value from local mapping
     * @param key
     * @return
     */
    public int getInt(String key) {
        Object value = get(key);
        if (value != null) {
            return (Integer) value;
        }
        //TODO: shouldn't return '0' if is not set
        return 0;
    }

    /**
     * retrieve LocalDateTime from local mapping
     * @param key
     * @return
     */
    public LocalDateTime getLDT(String key) {
        return (LocalDateTime) get(key);
    }

    /**
     * retrieve an uncasted generic object from local mapping
     * @param key
     * @return
     */
    public Object get(String key){
        if (mDHDPBodyMapping.containsKey(key)) {
            return mDHDPBodyMapping.get(key);
        }
        return null;
    }
}
