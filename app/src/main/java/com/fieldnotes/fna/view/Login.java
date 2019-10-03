package com.fieldnotes.fna.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fieldnotes.fna.R;
import com.fieldnotes.fna.asynctask.FNAsyncTask;
import com.fieldnotes.fna.model.DHDPHeader;
import com.fieldnotes.fna.model.DHDPResponseType;
import com.fieldnotes.fna.model.FNRequest;
import com.fieldnotes.fna.model.DHDPRequestType;
import com.fieldnotes.fna.model.DHDPResponse;
import com.fieldnotes.fna.model.FieldNote;
import com.fieldnotes.fna.service.DHDPRequestService;

import org.json.JSONException;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class Login extends AppCompatActivity {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static Logger mLogger = Logger.getLogger(Login.class.getName());

    private EditText mUserNameET;
    private EditText mPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        //customize actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.fn_icon);
        getSupportActionBar().setTitle("");

        // get views
        mUserNameET = findViewById(R.id.UsernameET);
        mPasswordET = findViewById(R.id.PasswordET);

        // Login button
        Button loginBtn = findViewById(R.id.LoginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginAsyncTask(Login.this).execute();
            }
        });

//        checkAutoLogin();
    }

    /**
     * Asynchronous login with progress bar
     */
    class LoginAsyncTask extends FNAsyncTask {

        LoginAsyncTask(Context context) {
            super(context);
        }

        /**
         * Run the login in an AsyncTask background thread
         *
         * @param args
         * @return string
         */
        @Override
        protected String doInBackground(String... args) {
            // collect data from UI
            String userName = mUserNameET.getText().toString();
            String password = mPasswordET.getText().toString();

            //DHDPConnector - UI creates a header defined by the library
            DHDPHeader header = DHDPHeader.newBuilder()
                    .setRequestType(DHDPRequestType.LOGIN)
                    .setCreator(userName)
                    .setOrganization("FieldNotes")
                    .setOriginator("FNA")
                    .setRecipient("DHDP")
                    .build();

            //UI - DEFINES ITS OWN OBJECT DEFINITION
            FieldNote loginFn = FieldNote.newBuilder()
                    .setUsername(userName)
                    .setPassword(password)
                    .build();

            //UI - defines a Request object that extends a DHDPRequest
            // the UIRequest object should extend the DHDPRequest which requires a Header and a request type
            // DHDPRequest (Interface) & DHDPRequestType
            FNRequest request = FNRequest.newBuilder()
                    .setHeader(header)
                    .setFieldNote(loginFn)
                    .build();

//            try {
                //DHDPConnector - use DHDPRequestService to send request to server
                // send request to FNP
                DHDPResponse response = DHDPRequestService.getInstance().sendRequest(request);

                if (response.getResponseType().equals(DHDPResponseType.SUCCESS)) {
                    // navigate to Welcome View
                    Intent ii = new Intent(Login.this, Welcome.class);
                    startActivity(ii);
                    finish();
                }
                return response.getMessage();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
        }
    }
}

