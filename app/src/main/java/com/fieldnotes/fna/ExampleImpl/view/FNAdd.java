package com.fieldnotes.fna.ExampleImpl.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devhunter.DHDPConnector4J.DHDPRequestService;
import com.devhunter.DHDPConnector4J.header.DHDPHeader;
import com.devhunter.DHDPConnector4J.request.DHDPRequest;
import com.devhunter.DHDPConnector4J.request.DHDPRequestType;
import com.devhunter.DHDPConnector4J.response.DHDPResponse;
import com.devhunter.DHDPConnector4J.response.DHDPResponseType;
import com.fieldnotes.fna.ExampleImpl.gps.SelfLocator;
import com.fieldnotes.fna.ExampleImpl.model.FNAsyncTask;
import com.fieldnotes.fna.ExampleImpl.model.FieldNote;
import com.fieldnotes.fna.ExampleImpl.view.adapters.HintAdapter;
import com.fieldnotes.fna.ExampleImpl.view.datetime.SelectDateFragment;
import com.fieldnotes.fna.ExampleImpl.view.datetime.SelectTimeFragment;
import com.fieldnotes.fna.R;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import static android.content.Context.MODE_PRIVATE;

public class FNAdd extends Fragment {
    private EditText mDateStart;
    private EditText mDateEnd;
    private EditText mTimeStart;
    private EditText mTimeEnd;
    private CheckBox mGpsCheckbox;

    private static Logger mLogger = Logger.getLogger(FNLogin.class.getName());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // customize action bar
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if (activity != null) {
            // Action Bar
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setIcon(R.drawable.fn_icon);
                actionBar.setTitle("");
            } else {
                mLogger.severe("ActionBar failed to load");
            }
        } else {
            mLogger.severe("Activity is null");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_tab_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        final FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            // prevent any of the views from being focused onViewCreated
            View focusView = view.findViewById(R.id.focus_view);
            focusView.requestFocus();

            // define dateTime DialogFragments
            //TODO: merge these into 2 date/time pickers
            mDateStart = view.findViewById(R.id.dateStart);
            mDateStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dateFragment = new SelectDateFragment(mDateStart);
                    dateFragment.show(fragmentManager, "DatePicker");
                }
            });
            mTimeStart = view.findViewById(R.id.timeStart);
            mTimeStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment timeFragment = new SelectTimeFragment(mTimeStart);
                    timeFragment.show(fragmentManager, "TimePicker");
                }
            });
            mDateEnd = view.findViewById(R.id.dateEnd);
            mDateEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dateFragment = new SelectDateFragment(mDateEnd);
                    dateFragment.show(fragmentManager, "DatePicker");
                }
            });
            mTimeEnd = view.findViewById(R.id.timeEnd);
            mTimeEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment timeFragment = new SelectTimeFragment(mTimeEnd);
                    timeFragment.show(fragmentManager, "TimePicker");
                }
            });

            // define location spinner
            Spinner mLocation = view.findViewById(R.id.location);
            final HintAdapter locationHintAdapter = new HintAdapter(getActivity(),
                    R.layout.layout_spinner_item, getResources().getStringArray(R.array.location_code));
            mLocation.setAdapter(locationHintAdapter);
            mLocation.setSelection(locationHintAdapter.getCount());
            mLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position < locationHintAdapter.getCount()) {
                        TextView tv = (TextView) view;
                        tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            // define billing code spinner
            Spinner mBillingCode = view.findViewById(R.id.billingCode);
            final HintAdapter billingHintAdapter = new HintAdapter(getActivity(),
                    R.layout.layout_spinner_item, getResources().getStringArray(R.array.billing_code));
            mBillingCode.setAdapter(billingHintAdapter);
            mBillingCode.setSelection(billingHintAdapter.getCount());
            mBillingCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position < billingHintAdapter.getCount()) {
                        TextView tv = (TextView) view;
                        tv.setTextColor(getResources().getColor(R.color.colorBlack));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            // define gps checkbox
            mGpsCheckbox = view.findViewById(R.id.gpsCheckbox);
            mGpsCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        FragmentActivity activity = getActivity();
                        if (activity != null) {
                            //if location services are not granted to FieldNotes
                            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                mGpsCheckbox.setChecked(false);
                            }
                            new SelfLocator(getActivity()).execute();
                        }
                    }
                }
            });

            // define add button
            FloatingActionButton addButton = view.findViewById(R.id.addButton);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AddAsyncTask(getActivity()).execute();
                }
            });
        }
    }

    /**
     * Asynchronous AddNote with progress bar
     */
    private static class AddAsyncTask extends FNAsyncTask {

        private WeakReference<Activity> weakAddContextRef;

        AddAsyncTask(Activity context) {
            super(context);
            // get weak reference to FNAdd fragment
            weakAddContextRef = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... strings) {
            // get context from weak reference
            final Activity addContext = weakAddContextRef.get();
            Resources resources = addContext.getResources();

            //get views
            EditText projectEt = addContext.findViewById(R.id.projectName);
            EditText wellNameEt = addContext.findViewById(R.id.wellName);
            Spinner locationSpr = addContext.findViewById(R.id.location);
            Spinner billingSpr = addContext.findViewById(R.id.billingCode);
            EditText dateStartEt = addContext.findViewById(R.id.dateStart);
            EditText dateEndEt = addContext.findViewById(R.id.dateEnd);
            EditText timeStartEt = addContext.findViewById(R.id.timeStart);
            EditText timeEndEt = addContext.findViewById(R.id.timeEnd);
            EditText mileageStartEt = addContext.findViewById(R.id.mileageStart);
            EditText mileageEndEt = addContext.findViewById(R.id.mileageEnd);
            EditText descriptionEt = addContext.findViewById(R.id.description);

            // get values from preferences
            SharedPreferences prefs = addContext.getSharedPreferences(
                    resources.getString(R.string.REMEMBER_LOGIN_PREF_NAME), MODE_PRIVATE);
            String username = prefs.getString(resources.getString(R.string.PREF_USERNAME), "");
            String token = prefs.getString(resources.getString(R.string.PREF_TOKEN), "");

            // build timestamps from GUI
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
            long startTimestamp = 0L;
            long endTimestamp = 0L;
            try {
                Date d = formatter.parse(dateStartEt.getText() + " " + timeStartEt.getText());
                startTimestamp = d.getTime();
                d = formatter.parse(dateEndEt.getText() + " " + timeEndEt.getText());
                endTimestamp = d.getTime();
            } catch (ParseException e) {
                // do nothing - response from DHDP will be displayed in toast
            }

            // create DHDPHeader
            DHDPHeader header = DHDPHeader.newBuilder()
                    .setRequestType(DHDPRequestType.ADD)
                    .setCreator(username)
                    .setOrganization(resources.getString(R.string.ORGANIZATION))
                    .setOriginator(resources.getString(R.string.FIELDNOTE_ENTITY))
                    .setRecipient(resources.getString(R.string.DHDP_ENTITY))
                    .build();

            // create client DHDPBody implementation for UI values
            FieldNote fieldNote = new FieldNote();
            fieldNote.put(resources.getString(R.string.TOKEN_KEY), token);
            fieldNote.put(resources.getString(R.string.USERNAME_KEY), username);
            fieldNote.put(resources.getString(R.string.PROJECT_KEY), projectEt.getText().toString());
            fieldNote.put(resources.getString(R.string.WELLNAME_KEY), wellNameEt.getText().toString());
            fieldNote.put(resources.getString(R.string.LOCATION_KEY), locationSpr.getSelectedItem().toString());
            fieldNote.put(resources.getString(R.string.BILLING_TYPE_KEY), billingSpr.getSelectedItem().toString());
            fieldNote.put(resources.getString(R.string.START_TIMESTAMP_KEY), String.valueOf(startTimestamp));
            fieldNote.put(resources.getString(R.string.END_TIMESTAMP_KEY), String.valueOf(endTimestamp));
            fieldNote.put(resources.getString(R.string.START_MILEAGE_KEY), mileageStartEt.getText().toString());
            fieldNote.put(resources.getString(R.string.END_MILEAGE_KEY), mileageEndEt.getText().toString());
            fieldNote.put(resources.getString(R.string.DESCRIPTION_KEY), descriptionEt.getText().toString());

            // create a DHDPRequest payload to DHDP
            DHDPRequest request = DHDPRequest.newBuilder()
                    .setHeader(header)
                    .setBody(fieldNote)
                    .build();

            // send DHDPRequest to DHDP through the DHDPRequestService
            DHDPResponse response = DHDPRequestService.getInstance()
                    .sendRequest(resources.getString(R.string.DHDP_HOST), request);

            // handle DHDPResponse received from DHDP
            if (response.getResponse().getResponseType().equals(DHDPResponseType.SUCCESS)) {
                // return to default activity
                Intent ii = new Intent(addContext, FNWelcome.class);
                addContext.startActivity(ii);
                addContext.finish();
            }
            return response.getResponse().getMessage();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}