package com.fieldnotes.fna.ExampleImpl.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.devhunter.DHDPConnector4J.DHDPRequestService;
import com.devhunter.DHDPConnector4J.header.DHDPHeader;
import com.devhunter.DHDPConnector4J.request.DHDPRequest;
import com.devhunter.DHDPConnector4J.request.DHDPRequestType;
import com.devhunter.DHDPConnector4J.response.DHDPResponse;
import com.devhunter.DHDPConnector4J.response.DHDPResponseType;
import com.fieldnotes.fna.ExampleImpl.model.FNAsyncTask;
import com.fieldnotes.fna.ExampleImpl.model.FieldNote;
import com.fieldnotes.fna.R;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FNLogin extends AppCompatActivity {
    private static Logger mLogger = Logger.getLogger(FNLogin.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        // Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.drawable.fn_icon);
            actionBar.setTitle("");
        } else {
            mLogger.severe("ActionBar failed to load");
        }

        // FNLogin button
        Button loginBtn = findViewById(R.id.LoginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogger.info("Attempting to FNLogin");
                new LoginAsyncTask(FNLogin.this).execute();
            }
        });
    }

    /**
     * Asynchronous login with progress bar
     */
    private static class LoginAsyncTask extends FNAsyncTask {

        private WeakReference<FNLogin> weakLoginContextRef;

        LoginAsyncTask(FNLogin loginContext) {
            super(loginContext);
            // get weak reference to FNLogin
            weakLoginContextRef = new WeakReference<>(loginContext);
        }

        /**
         * Run the login in an AsyncTask background thread
         *
         * @param args for task
         * @return string
         */
        @Override
        protected String doInBackground(String... args) {
            // get context from weak reference
            FNLogin loginContext = weakLoginContextRef.get();

            // get Views from context
            EditText userNameET = loginContext.findViewById(R.id.UsernameET);
            EditText passwordET = loginContext.findViewById(R.id.PasswordET);

            // collect data from UI
//            String userName = userNameET.getText().toString();
            String userName = "unit test";
//            String password = passwordET.getText().toString();
            String password = "fnunittest";

            //Create a DHDPHeader - containing DHDP metadata
            DHDPHeader header = DHDPHeader.newBuilder()
                    .setRequestType(DHDPRequestType.LOGIN)
                    .setCreator(userName)
                    .setOrganization(loginContext.getResources().getString(R.string.ORGANIZATION))
                    .setOriginator(loginContext.getResources().getString(R.string.FIELDNOTE_ENTITY))
                    .setRecipient(loginContext.getResources().getString(R.string.DHDP_ENTITY))
                    .build();

            //Create client DHDPBody implementation
            FieldNote body = new FieldNote();
            body.put(loginContext.getResources().getString(R.string.USERNAME_KEY), userName);
            body.put(loginContext.getResources().getString(R.string.PASSWORD_KEY), password);

            //Create a DHDPRequest payload to DHDP
            DHDPRequest request = DHDPRequest.newBuilder()
                    .setHeader(header)
                    .setBody(body)
                    .build();

            // send DHDPRequest to DHDP through the DHDPRequestService
            DHDPResponse response = DHDPRequestService.getInstance()
                    .sendRequest(loginContext.getResources().getString(R.string.DHDP_HOST), request);

            // handle DHDPResponse received from DHDP
            if (response.getResponse().getResponseType().equals(DHDPResponseType.SUCCESS)) {
                Resources resources = loginContext.getResources();
                // get token from results
                List<Map<String, Object>> results = response.getResponse().getResults();
                String token = results.get(0).get(resources.getString(R.string.TOKEN_KEY)).toString();

                // save login preferences
                loginContext.getSharedPreferences(resources.getString(R.string.REMEMBER_LOGIN_PREF_NAME), MODE_PRIVATE).edit()
                        .putString(resources.getString(R.string.PREF_USERNAME), userName)
                        .putString(resources.getString(R.string.PREF_PASSWORD), password)
                        .putString(resources.getString(R.string.PREF_TOKEN), token)
                        .apply();

                Intent ii = new Intent(loginContext, FNWelcome.class);
                loginContext.startActivity(ii);
                loginContext.finish();
            }
            return response.getResponse().getMessage();
        }
    }
}

