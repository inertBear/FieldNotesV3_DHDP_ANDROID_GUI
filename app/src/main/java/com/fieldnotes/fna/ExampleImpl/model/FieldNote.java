package com.fieldnotes.fna.ExampleImpl.model;

import com.devhunter.DHDPConnector4J.request.DHDPRequestBody;

import java.time.LocalDateTime;

public class FieldNote extends DHDPRequestBody {

    /* all values passed in the constructor can be retrieved from getBody(), but it may be easier
     * (depending on the implementation) to store them as separate variables, as seen here
     */
    private String mTicketNumber;
    private String mUsername;
    private String mPassword;
    private String mToken;
    private String mProject;
    private String mWellName;
    private String mLocation;
    private String mBilling;
    private LocalDateTime mStartDateTime;
    private LocalDateTime mEndDateTime;
    private Integer mMileageStart;
    private Integer mMileageEnd;
    private String mDescription;
    private String mGps;

    public void setTicketNumber(String ticketNumber) {
        mTicketNumber = ticketNumber;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public void setProject(String project) {
        mProject = project;
    }

    public void setWellName(String wellname) {
        mWellName = wellname;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public void setBilling(String billing) {
        mBilling = billing;
    }

    public void setStartDate(LocalDateTime startDate) {
        mStartDateTime = startDate;
    }

    public void setEndDate(LocalDateTime endTime) {
        mEndDateTime = endTime;
    }

    public void setMileageStart(int startMileage) {
        mMileageStart = startMileage;
    }

    public void setMileageEnd(int endMileage) {
        mMileageEnd = endMileage;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setGps(String gps) {
        mGps = gps;
    }

    public String getTicketNumber() {
        return mTicketNumber;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getToken() {
        return mToken;
    }

    public String getProject() {
        return mProject;
    }

    public String getWellname() {
        return mWellName;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getBilling() {
        return mBilling;
    }

    public String getDescription() {
        return mDescription;
    }

    public LocalDateTime getStartDateTime() {
        return mStartDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return mEndDateTime;
    }

    public int getMileageStart() {
        return mMileageStart;
    }

    public int getMileageEnd() {
        return mMileageEnd;
    }

    public String getGps() {
        return mGps;
    }
}

