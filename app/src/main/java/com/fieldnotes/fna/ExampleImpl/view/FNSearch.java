package com.fieldnotes.fna.ExampleImpl.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.devhunter.DHDPConnector4J.DHDPRequestService;
import com.devhunter.DHDPConnector4J.header.DHDPHeader;
import com.devhunter.DHDPConnector4J.request.DHDPRequest;
import com.devhunter.DHDPConnector4J.request.DHDPRequestType;
import com.devhunter.DHDPConnector4J.response.DHDPResponse;
import com.devhunter.DHDPConnector4J.response.DHDPResponseType;
import com.fieldnotes.fna.ExampleImpl.model.FNAsyncTask;
import com.fieldnotes.fna.ExampleImpl.model.FieldNote;
import com.fieldnotes.fna.ExampleImpl.view.adapters.SearchResultAdapter;
import com.fieldnotes.fna.ExampleImpl.view.datetime.SelectDateFragment;
import com.fieldnotes.fna.R;
import com.google.gson.internal.LinkedHashTreeMap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import static android.content.Context.MODE_PRIVATE;

public class FNSearch extends Fragment {
    private TextView mDateStart;
    private TextView mDateEnd;
    private static List<Map<String, Object>> mSearchResults;
    private static Logger mLogger = Logger.getLogger(FNSearch.class.getName());


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // customize action bar
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if (activity != null) {
            ActionBar bar = activity.getSupportActionBar();
            if (bar != null) {
                activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
                activity.getSupportActionBar().setIcon(R.drawable.fn_icon);
                activity.getSupportActionBar().setTitle("");
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tab_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {

            // define dateTime DialogFragments
            mDateStart = view.findViewById((R.id.SearchDateStart));
            mDateStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectDateFragment(mDateStart);
                    newFragment.show(getFragmentManager(), "DatePicker");
                }
            });
            mDateEnd = view.findViewById(R.id.SearchDateEnd);
            mDateEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectDateFragment(mDateEnd);
                    newFragment.show(getFragmentManager(), "DatePicker");
                }
            });

            // define search button
            Button searchButton = view.findViewById(R.id.searchButton);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SearchNoteAsyncTask(getContext()).execute();
                }
            });
        }
    }

    /**
     * Asynchronous Search with progress bar
     * <p>
     * Results are fully qualified. These results can be selected
     * by the user to edit the values
     */
    private static class SearchNoteAsyncTask extends FNAsyncTask {

        SearchNoteAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected String doInBackground(String... strings) {
            Activity searchContext = (Activity) getContext().get();
            Resources resources = searchContext.getResources();

            // get preferences
            final String fnPrefs = resources.getString(R.string.REMEMBER_LOGIN_PREF_NAME);
            SharedPreferences prefs = searchContext.getSharedPreferences(fnPrefs, MODE_PRIVATE);
            //get values from preferences
            String username = prefs.getString(searchContext.getResources().getString(R.string.PREF_USERNAME), "");
            String token = prefs.getString(searchContext.getResources().getString(R.string.PREF_TOKEN), "");

            // get views from context
            EditText dateStartEt = searchContext.findViewById(R.id.SearchDateStart);
            EditText dateEndEt = searchContext.findViewById(R.id.SearchDateEnd);

            // create DHDPHeader
            DHDPHeader header = DHDPHeader.newBuilder()
                    .setRequestType(DHDPRequestType.SEARCH)
                    .setCreator(username)
                    .setOrganization(resources.getString(R.string.ORGANIZATION))
                    .setOriginator(resources.getString(R.string.FIELDNOTE_ENTITY))
                    .setRecipient(resources.getString(R.string.DHDP_ENTITY))
                    .build();

            // create client DHDPBody
            FieldNote fieldNote = new FieldNote();
            fieldNote.put(resources.getString(R.string.TOKEN_KEY), token);
            fieldNote.put(resources.getString(R.string.USERNAME_KEY), username);
            fieldNote.put(resources.getString(R.string.START_TIMESTAMP_KEY), dateStartEt.getText().toString());
            fieldNote.put(resources.getString(R.string.END_TIMESTAMP_KEY), dateEndEt.getText().toString());

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
                mSearchResults = response.getResponse().getResults();
            }
            return response.getResponse().getMessage();
        }

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            final Activity searchContext = (Activity) getContext().get();
            final Resources res = searchContext.getResources();

            // get list view
            ListView mListView = searchContext.findViewById(R.id.searchResultsListView);
            // create/set adapter with search results
            final SearchResultAdapter adapter = new SearchResultAdapter(searchContext, convertResultsToFieldNote());
            mListView.setAdapter(adapter);

            // set onClickListener to Edit Note
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //user motion to edit data
                HashMap<String, Object> selectedValue = adapter.getItem(position);

                Toast.makeText(searchContext, "Edit ticket " +
                        selectedValue.get(res.getString(R.string.TICKET_NUMBER_KEY)), Toast.LENGTH_SHORT).show();

                // TODO: implement update ticket functionality
//                // set extra args for update fragment
//                FNUpdate updateFragment = new FnUpdate();
//                Bundle args = new Bundle();
//                args.putSerializable("oldData", selectedValue);
//                updateFragment.setArguments(args);
//                // go to Edit fragment
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(android.R.id.content, updateFragment, "updateFragment")
//                        .addToBackStack("updateFragment")
//                        .commit();
                }
            });
        }

        private ArrayList<FieldNote> convertResultsToFieldNote() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ArrayList<FieldNote> results = new ArrayList<>();
            // should only be one (this layer is an adapter from the GSON)
            for (Map<String, Object> map : mSearchResults) { //(ticket#, FieldNote)
                // for each search result
                for (String ticketNumber : map.keySet()) { //(LOCATION, PROJECT, DESCRIPTION...)
                    FieldNote result = new FieldNote();
                    Map<String, Object> searchResult = (Map<String, Object>) map.get(ticketNumber);
                    result.setTicketNumber(ticketNumber);
                    for (Map.Entry<String, Object> entry : searchResult.entrySet())
                    {
                        // put the individual fields into a FieldNote
                        switch (entry.getKey()) {
                            case "PROJECT":
                                result.setProject((String) entry.getValue());
                                break;
                            case "CREATOR":
                                result.setUsername((String) entry.getValue());
                                break;
                            case "WELLNAME":
                                result.setWellName((String) entry.getValue());
                                break;
                            case "LOCATION":
                                result.setLocation((String) entry.getValue());
                                break;
                            case "BILLING_TYPE":
                                result.setBilling((String) entry.getValue());
                                break;
                            case "START_DATETIME":
                                LocalDateTime startTime = LocalDateTime.parse((String) entry.getValue(), formatter);
                                result.setStartDate(startTime);
                                break;
                            case "END_DATETIME":
                                LocalDateTime endTime = LocalDateTime.parse((String) entry.getValue(), formatter);
                                result.setEndDate(endTime);
                                break;
                            case "START_MILEAGE":
                                Double startValue = (Double) entry.getValue();
                                result.setMileageStart(startValue.intValue());
                                break;
                            case "END_MILEAGE":
                                Double endValue = (Double) entry.getValue();
                                result.setMileageEnd(endValue.intValue());
                                break;
                            case "DESCRIPTION":
                                result.setDescription((String) entry.getValue());
                                break;
                            case "GPS":
                                result.setGps((String) entry.getValue());
                                break;
                            default:
                                mLogger.info("Could not set: " + entry.getKey());
                                break;
                        }
                    }
                    // add the result to list of FieldNotes
                    results.add(result);
                }
            }
            // return list of FieldNotes
            return results;
        }
    }
}
