package com.fieldnotes.fna.ExampleImpl.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fieldnotes.fna.DHDPConnector.DHDPEntity;
import com.fieldnotes.fna.DHDPConnector.DHDPHeader;
import com.fieldnotes.fna.DHDPConnector.DHDPOrganization;
import com.fieldnotes.fna.DHDPConnector.DHDPRequest;
import com.fieldnotes.fna.DHDPConnector.DHDPRequestService;
import com.fieldnotes.fna.DHDPConnector.DHDPRequestType;
import com.fieldnotes.fna.DHDPConnector.DHDPResponse;
import com.fieldnotes.fna.DHDPConnector.DHDPResponseType;
import com.fieldnotes.fna.ExampleImpl.model.FNAsyncTask;
import com.fieldnotes.fna.ExampleImpl.model.FieldNote;
import com.fieldnotes.fna.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.fieldnotes.fna.ExampleImpl.constants.Constants.PASSWORD_KEY;
import static com.fieldnotes.fna.ExampleImpl.constants.Constants.USERNAME_KEY;

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

        WeakReference<FNLogin> weakLoginContextRef;

        LoginAsyncTask(FNLogin loginContext) {
            super(loginContext);
            // get weak reference to FNLogin
            weakLoginContextRef = new WeakReference<>(loginContext);
        }

        /**
         * Run the login in an AsyncTask background thread
         *
         * @param args
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
            String userName = userNameET.getText().toString();
            String password = passwordET.getText().toString();

            //Create a DHDPHeader - containing DHDP metadata
            DHDPHeader header = DHDPHeader.newBuilder()
                    .setRequestType(DHDPRequestType.LOGIN)
                    .setCreator(userName)
                    .setOrganization(DHDPOrganization.DEVHUNTER)
                    .setOriginator(DHDPEntity.FieldNotes)
                    .setRecipient(DHDPEntity.DHDP)
                    .build();

            //Create client DHDPBody implementation
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put(USERNAME_KEY, userName);
            bodyMap.put(PASSWORD_KEY, password);
            FieldNote body = new FieldNote(bodyMap);

            //Create a DHDPRequest payload to DHDP
            DHDPRequest request = DHDPRequest.newBuilder()
                    .setHeader(header)
                    .setBody(body)
                    .build();

            // send DHDPRequest to DHDP through the DHDPRequestService
            DHDPResponse response = DHDPRequestService.getInstance().sendRequest(request);

            // handle DHDPResponse received from DHDP
            if (response.getResponseType().equals(DHDPResponseType.SUCCESS)) {
                Intent ii = new Intent(loginContext, FNWelcome.class);
                loginContext.startActivity(ii);
                loginContext.finish();
            }
            return response.getMessage();
        }
    }
}

