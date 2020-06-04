package com.fieldnotes.fna.ExampleImpl.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Stores context as a WeakReference and abstracts async behaviors
 */
public abstract class FNAsyncTask extends AsyncTask<String, String, String> {
    private static WeakReference<Context> mContext;
    private ProgressDialog mProgressDialog;

    public FNAsyncTask(Context context) {
        mContext = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext.get());
        mProgressDialog.setMessage("Working...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    @Override
    protected abstract String doInBackground(String... args);

    /**
     * Dismiss the progress bar, and display response message as a toast
     **/
    protected void onPostExecute(String message) {
        super.onPostExecute(message);

        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        // display response message
        if (message != null) {
            Toast toast = Toast.makeText(mContext.get().getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public WeakReference<Context> getContext() {
        return mContext;
    }
}
