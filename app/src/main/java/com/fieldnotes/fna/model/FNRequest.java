package com.fieldnotes.fna.model;

/**
 * Models a request sent to FNP
 */

public class FNRequest {
    private DHDPHeader mHeader;
    private DHDPRequestType mRequestType;
    private FieldNote mFieldNote;

    FNRequest(final FNRequest.Builder builder) {
        mHeader = builder.header;
        mRequestType = builder.requestType;
        mFieldNote = builder.fieldNote;
    }

    public static FNRequest.Builder newBuilder() {
        return new FNRequest.Builder();
    }

    public static FNRequest.Builder newBuilder(final FNRequest copy) {
        return new FNRequest.Builder(copy);
    }

    public DHDPRequestType getRequestType() {
        return mRequestType;
    }

    public DHDPHeader getHeader() {
        return mHeader;
    }

    public FieldNote getFieldNote() {
        return mFieldNote;
    }

    public static final class Builder {
        private DHDPHeader header;
        private DHDPRequestType requestType;
        private FieldNote fieldNote;

        private Builder() {
        }

        public Builder(final FNRequest copy) {
            header = copy.mHeader;
            requestType = copy.mRequestType;
            fieldNote = copy.mFieldNote;
        }

        public FNRequest.Builder setHeader(final DHDPHeader header) {
            this.header = header;
            return this;
        }

        public FNRequest.Builder setRequestType(final DHDPRequestType requestType) {
            this.requestType = requestType;
            return this;
        }

        // TODO: should be automatically set
        public FNRequest.Builder setFieldNote(final FieldNote fieldNote) {
            this.fieldNote = fieldNote;
            return this;
        }

        public FNRequest build() {
            return new FNRequest(this);
        }
    }
}
