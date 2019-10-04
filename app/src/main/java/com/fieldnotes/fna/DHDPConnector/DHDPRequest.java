package com.fieldnotes.fna.DHDPConnector;

/**
 * Models a request to be sent to DHDP
 */
public class DHDPRequest {
    private DHDPHeader mHeader;
    private DHDPBody mBody;

    private DHDPRequest(final DHDPRequest.Builder builder) {
        mHeader = builder.header;
        mBody = builder.body;
    }

    public static DHDPRequest.Builder newBuilder() {
        return new DHDPRequest.Builder();
    }

    public DHDPHeader getHeader() {
        return mHeader;
    }

    public DHDPBody getBody() {
        return mBody;
    }

    public static final class Builder {
        private DHDPHeader header;
        private DHDPBody body;

        private Builder() {
        }

        public DHDPRequest.Builder setHeader(final DHDPHeader header) {
            this.header = header;
            return this;
        }

        public DHDPRequest.Builder setBody(final DHDPBody body) {
            this.body = body;
            return this;
        }

        public DHDPRequest build() {
            return new DHDPRequest(this);
        }
    }
}
