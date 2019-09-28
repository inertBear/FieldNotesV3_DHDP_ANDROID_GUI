package com.fieldnotes.fna.model;

public class DHDPHeader {

    private String mCreator; // user that created it
    private String mOrganization; // company the user belongs to
    // TODO: these should be Enums defined by the 'DHDPConnector' library
    private DHDPRequestType mRequestType;
    private String mSender; // app|service sending JSON ("UI" if sending from UI, "DHDP" if sending from DHDP)
    private String mRecipient; // app|service JSON is being sent to ("DHDP" if sending from UI, "UI" if sending from DHDP)


    private DHDPHeader(DHDPHeaderBuilder builder) {
        mCreator = builder.creator;
        mOrganization = builder.organization;
        mRequestType = builder.requestType;
        mSender = builder.sender;
        mRecipient = builder.recipient;
    }

    public static DHDPHeader.DHDPHeaderBuilder newBuilder() {
        return new DHDPHeader.DHDPHeaderBuilder();
    }

    public String getCreator() {
        return mCreator;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public DHDPRequestType getRequestType() {
        return mRequestType;
    }

    public String getSender() {
        return mSender;
    }

    public String getRecipient() {
        return mRecipient;
    }

    public static class DHDPHeaderBuilder {
        private String creator;
        private String organization;
        private DHDPRequestType requestType;
        private String sender;
        private String recipient;

        public DHDPHeaderBuilder() {
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

        public DHDPHeaderBuilder setSender(String sender) {
            this.sender = sender;
            return this;
        }

        public DHDPHeaderBuilder setRecipient(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public DHDPHeader build() {
            return new DHDPHeader(this);
        }
    }
}
