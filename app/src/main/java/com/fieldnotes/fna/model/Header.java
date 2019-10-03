package com.fieldnotes.fna.model;

public interface Header {

    /**
     * retrieves the user that created the header
     * @return username
     */
    String getCreator();

    /**
     * retrieves the organization the user belongs to
     * @return organization name
     */
    String getOrganization();

    /**
     * retrieves request type associated with the header
     * @return request type enum
     */
    DHDPRequestType getRequestType();

    /**
     * retrieves response type associated with the header
     * @return response type enum
     */
    DHDPResponseType getResponseType();

    /**
     * retrieves name of the application originating the request
     * @return addresser's name
     */
    String getOriginator();

    /**
     * retrieves name of the application the header is addressed to
     * @return addressee's name
     */
    String getRecipient();
}
