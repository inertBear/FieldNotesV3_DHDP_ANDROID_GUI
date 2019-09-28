package com.fieldnotes.fna.model;

import java.time.LocalDateTime;

public class FieldNote {
    // TODO: move to separate Login object
    private String mUsername;
    private String mPassword;

    private String mProductKey;
    private String mProject;
    private String mWellName;
    private String mLocation;
    private String mBilling;
    private LocalDateTime mStartDate;
    private LocalDateTime mEndDate;
    private LocalDateTime mStartTime;
    private LocalDateTime mEndTime;
    private int mMileageStart;
    private int mMileageEnd;
    private String mDescription;
    private String mGps;

    private FieldNote(FieldNoteBuilder builder) {
        this.mUsername = builder.username;
        this.mPassword = builder.password;

        this.mProductKey = builder.productKey;
        this.mProject = builder.project;
        this.mWellName = builder.wellName;
        this.mLocation = builder.location;
        this.mBilling = builder.billing;
        this.mStartDate = builder.startDate;
        this.mEndDate = builder.endDate;
        this.mStartTime = builder.startTime;
        this.mEndTime = builder.endTime;
        this.mMileageStart = builder.mileageStart;
        this.mMileageEnd = builder.mileageEnd;
        this.mDescription = builder.description;
        this.mGps = builder.gps;
    }

    public static FieldNote.FieldNoteBuilder newBuilder() {
        return new FieldNote.FieldNoteBuilder();
    }

    public String getUsername() {
        return mUsername;
    }
    public String getPassword() {
        return mPassword;
    }

    public String getProductKey() {
        return mProductKey;
    }

    public String getProject() {
        return mProject;
    }

    public String getWellName() {
        return mWellName;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getBilling() {
        return mBilling;
    }

    public LocalDateTime getStartDate() {
        return mStartDate;
    }

    public LocalDateTime getEndDate() {
        return mEndDate;
    }

    public LocalDateTime getStartTime() {
        return mStartTime;
    }

    public LocalDateTime getEndTime() {
        return mEndTime;
    }

    public int getMileageStart() {
        return mMileageStart;
    }

    public int getMileageEnd() {
        return mMileageEnd;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getGPS() {
        return mGps;
    }


    public static class FieldNoteBuilder {
        private String username;
        private String password;

        private String productKey;
        private String project;
        private String wellName;
        private String location;
        private String billing;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private int mileageStart;
        private int mileageEnd;
        private String description;
        private String gps;

        public FieldNoteBuilder() {
        }

        public FieldNoteBuilder setUsername(String username) {
            this.username = username;
            return this;
        }
        public FieldNoteBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public FieldNoteBuilder setProductKey(String productKey) {
            this.productKey = productKey;
            return this;
        }

        public FieldNoteBuilder setProject(String project) {
            this.project = project;
            return this;
        }

        public FieldNoteBuilder setWellname(String wellname) {
            this.wellName = wellname;
            return this;
        }

        public FieldNoteBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public FieldNoteBuilder setBilling(String billing) {
            this.billing = billing;
            return this;
        }

        public FieldNoteBuilder setDateStart(LocalDateTime dateStart) {
            this.startDate = dateStart;
            return this;
        }

        public FieldNoteBuilder setDateEnd(LocalDateTime dateEnd) {
            this.endDate = dateEnd;
            return this;
        }

        public FieldNoteBuilder setTimeStart(LocalDateTime timeStart) {
            this.startTime = timeStart;
            return this;
        }

        public FieldNoteBuilder setTimeEnd(LocalDateTime timeEnd) {
            this.endTime = timeEnd;
            return this;
        }

        public FieldNoteBuilder setMileageStart(int mileageStart) {
            this.mileageStart = mileageStart;
            return this;
        }

        public FieldNoteBuilder setMileageEnd(int mileageEnd) {
            this.mileageEnd = mileageEnd;
            return this;
        }

        public FieldNoteBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public FieldNoteBuilder setGPS(String gps) {
            this.gps = gps;
            return this;
        }

        public FieldNote build() {
            return new FieldNote(this);
        }
    }
}
