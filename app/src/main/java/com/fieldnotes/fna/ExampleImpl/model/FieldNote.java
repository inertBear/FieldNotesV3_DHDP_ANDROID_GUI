package com.fieldnotes.fna.ExampleImpl.model;

import com.fieldnotes.fna.DHDPConnector.DHDPBody;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fieldnotes.fna.ExampleImpl.constants.Constants.BILLING_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.DESCRIPTION_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.END_DATE_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.END_MILEAGE;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.GPS_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.LOCATION_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.PASSWORD_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.PROJECT_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.START_DATE_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.START_MILEAGE;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.TOKEN_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.USERNAME_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.WELL_KEY;

public class FieldNote extends DHDPBody {

    /* all values passed in the constructor can be retrieved from getBody(), but it may be easier
     * (depending on the implementation) to store them as separate variables, as seen here
     */
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

    public FieldNote(Map<String, Object> map) {
        // make sure to call the super method in order to create the JSON under the hood
        super(map);
        // accessor methods provide easy way to assign values locally
        mUsername = getString(USERNAME_KEY);
        mPassword = getString(PASSWORD_KEY);
        mToken = getString(TOKEN_KEY);
        mProject = getString(PROJECT_KEY);
        mWellName = getString(WELL_KEY);
        mLocation = getString(LOCATION_KEY);
        mBilling = getString(BILLING_KEY);
        mDescription = getString(DESCRIPTION_KEY);
        mStartDateTime = getLDT(START_DATE_KEY);
        mEndDateTime = getLDT(END_DATE_KEY);
        mMileageStart = getInt(START_MILEAGE);
        mMileageEnd = getInt(END_MILEAGE);
        mGps = getString(GPS_KEY);
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

