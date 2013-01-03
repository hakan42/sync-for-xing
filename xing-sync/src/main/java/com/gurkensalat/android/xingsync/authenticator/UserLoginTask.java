package com.gurkensalat.android.xingsync.authenticator;

import android.os.AsyncTask;
import android.util.Log;

import com.gurkensalat.android.xingsync.client.NetworkUtilities;

/**
 * Represents an asynchronous task used to authenticate a user against the
 * SampleSync Service
 */
public class UserLoginTask extends AsyncTask<Void, Void, String>
{

    /**
     * 
     */
    private final AuthenticatorActivity authenticatorActivity;

    /**
     * @param authenticatorActivity
     */
    UserLoginTask(AuthenticatorActivity authenticatorActivity)
    {
        this.authenticatorActivity = authenticatorActivity;
    }

    @Override
    protected String doInBackground(Void... params)
    {
        // We do the actual work of authenticating the user
        // in the NetworkUtilities class.
        try
        {
            return NetworkUtilities.authenticate(this.authenticatorActivity.getmUsername(), this.authenticatorActivity.getmPassword());
        }
        catch (Exception ex)
        {
            Log.e(AuthenticatorActivity.TAG, "UserLoginTask.doInBackground: failed to authenticate");
            Log.i(AuthenticatorActivity.TAG, ex.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(final String authToken)
    {
        // On a successful authentication, call back into the Activity to
        // communicate the authToken (or null for an error).
        this.authenticatorActivity.onAuthenticationResult(authToken);
    }

    @Override
    protected void onCancelled()
    {
        // If the action was canceled (by the user clicking the cancel
        // button in the progress dialog), then call back into the
        // activity to let it know.
        this.authenticatorActivity.onAuthenticationCancel();
    }
}