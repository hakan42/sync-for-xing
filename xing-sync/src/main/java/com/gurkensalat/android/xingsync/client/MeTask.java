package com.gurkensalat.android.xingsync.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.gurkensalat.android.xingsync.R;
import com.gurkensalat.android.xingsync.authenticator.AuthenticatorActivity;
import com.gurkensalat.android.xingsync.keys.XingOAuthKeys;
import com.gurkensalat.android.xingsync.oauth.OAuthFlowApp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

/**
 * Represents an asynchronous task that calls the "ME" method to fetch user data
 */
public class MeTask extends AsyncTask<Void, Void, String>
{
    /**
     * 
     */
    private final static String TAG = MeTask.class.getName();

    /**
     * 
     */
    private SharedPreferences   prefs;

    /**
     * 
     */
    private final OAuthFlowApp  caller;

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
            String ME_REQUEST = "https://api.xing.com/v1/users/me";

            jsonOutput = doGet(ME_REQUEST, getConsumer(this.prefs));
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

    private OAuthConsumer getConsumer(SharedPreferences prefs)
    {
        Log.i(TAG, "getConsumer()");

        String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
        String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(XingOAuthKeys.CONSUMER_KEY, XingOAuthKeys.CONSUMER_SECRET);
        consumer.setTokenWithSecret(token, secret);
        return consumer;
    }

    private String doGet(String url, OAuthConsumer consumer) throws Exception
    {
        Log.i(TAG, "doGet(" + url + ", " + consumer + ")");

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        Log.i(TAG, "Requesting URL : " + url);
        consumer.sign(request);
        HttpResponse response = httpclient.execute(request);
        Log.i(TAG, "Statusline : " + response.getStatusLine());
        InputStream data = response.getEntity().getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));
        String responeLine;
        StringBuilder responseBuilder = new StringBuilder();
        while ((responeLine = bufferedReader.readLine()) != null)
        {
            responseBuilder.append(responeLine);
        }
        Log.i(TAG, "Response : " + responseBuilder.toString());
        return responseBuilder.toString();
    }

}
