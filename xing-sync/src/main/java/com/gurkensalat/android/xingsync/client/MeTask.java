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
        String jsonOutput = "";
        try
        {
            jsonOutput = doGet(Constants.ME_REQUEST, getConsumer(this.prefs));
            Log.i(TAG, "Response to ME: " + jsonOutput);

            // caller.showResult(jsonOutput);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error executing request", e);
            // caller.showResult("Error retrieving myself : " + jsonOutput);
        }

        return null;
    }
}
