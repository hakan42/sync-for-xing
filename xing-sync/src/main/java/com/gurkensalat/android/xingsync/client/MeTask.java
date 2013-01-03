package com.gurkensalat.android.xingsync.client;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gurkensalat.android.xingsync.Constants;
import com.gurkensalat.android.xingsync.oauth.OAuthFlowApp;

/**
 * Represents an asynchronous task that calls the "ME" method to fetch user data
 */
public class MeTask extends AbstractXINGApiTask
{
    /** The tag used to log to adb console. **/
    private final static String TAG = MeTask.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    /**
     * 
     */
    private final SharedPreferences prefs;

    /**
     * 
     */
    private final OAuthFlowApp caller;

    /**
     * 
     */
    private String data;

    /**
     * @param caller
     */
    public MeTask(OAuthFlowApp caller)
    {
        this.caller = caller;
        this.prefs = PreferenceManager.getDefaultSharedPreferences(caller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doInBackground(Void... params)
    {
        Log.i(TAG, "doInBackground(" + params + ")");

        String jsonOutput = "";
        try
        {
            jsonOutput = doGet(Constants.ME_REQUEST, getConsumer(this.prefs));
            Log.i(TAG, "Response to ME: " + jsonOutput);
            data = "success";
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error executing request", e);
            data = jsonOutput;
        }

        return null;
    }

    @Override
    protected void onPostExecute(final String authToken)
    {
        Log.i(TAG, "onPostExecute(" + authToken + ")");

        // On a successful authentication, call back into the Activity to
        // communicate the authToken (or null for an error).
        // this.caller.onAuthenticationResult(authToken);

        caller.showResult(data);
    }

    @Override
    protected void onCancelled()
    {
        Log.i(TAG, "onCancelled()");

        // If the action was canceled (by the user clicking the cancel
        // button in the progress dialog), then call back into the
        // activity to let it know.
        // this.caller.onAuthenticationCancel();
    }

}
