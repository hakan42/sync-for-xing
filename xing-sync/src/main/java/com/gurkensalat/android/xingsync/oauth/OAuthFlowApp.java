package com.gurkensalat.android.xingsync.oauth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gurkensalat.android.xingsync.R;
import com.gurkensalat.android.xingsync.authenticator.UserLoginTask;
import com.gurkensalat.android.xingsync.client.MeTask;
import com.gurkensalat.android.xingsync.keys.XingOAuthKeys;

/**
 * Entry point in the application. Launches the OAuth flow by starting the
 * PrepareRequestTokenActivity
 * 
 */
public class OAuthFlowApp extends Activity
{
    private final static String TAG          = OAuthFlowApp.class.getName();

    private static final int    PICK_CONTACT = 0;

    private SharedPreferences   prefs;

    /** Keep track of the API call task so can cancel it if requested */
    private MeTask              mMeTask      = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.i(TAG, "onCreate(" + savedInstanceState + ")");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_result);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String token = prefs.getString(OAuth.OAUTH_TOKEN, "NO-TOKEN");
        TextView oauthToken = (TextView) findViewById(R.id.oauth_token);
        oauthToken.setText(token);

        String token_secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "NO-TOKEN-SECRET");
        TextView oauthTokenSecret = (TextView) findViewById(R.id.oauth_token_secret);
        oauthTokenSecret.setText(token_secret);
    }

    public void performApiCallME(View view)
    {
        Log.i(TAG, "performApiCallME(" + view + ")");

        mMeTask = new MeTask(this);
        mMeTask.execute();
    }

    public void showResult(String result)
    {
        TextView textView = (TextView) findViewById(R.id.api_call_result);
        textView.setText(result);
    }

    private void performApiCall()
    {
        TextView textView = (TextView) findViewById(R.id.response_code);

        String jsonOutput = "";
        try
        {
            String ME_REQUEST = "https://api.xing.com/v1/users/me";

            // jsonOutput = doGet(ME_REQUEST, getConsumer(this.prefs));
            // Log.i(TAG, "Response to ME: " + jsonOutput);
            // JSONObject jsonResponse = new JSONObject(jsonOutput);
            // JSONObject m = (JSONObject) jsonResponse.get("feed");
            // JSONArray entries = (JSONArray) m.getJSONArray("entry");
            // String contacts = "";
            // for (int i = 0; i < entries.length(); i++)
            // {
            // JSONObject entry = entries.getJSONObject(i);
            // JSONObject title = entry.getJSONObject("title");
            // if (title.getString("$t") != null &&
            // title.getString("$t").length() > 0)
            // {
            // contacts += title.getString("$t") + "\n";
            // }
            // }
            // Log.i(TAG, jsonOutput);
            textView.setText(jsonOutput);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error executing request", e);
            textView.setText("Error retrieving contacts : " + jsonOutput);
        }
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data)
    {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode)
        {
        case (PICK_CONTACT):
            if (resultCode == Activity.RESULT_OK)
            {
                Uri contactData = data.getData();
                Cursor c = managedQuery(contactData, null, null, null, null);
                if (c.moveToFirst())
                {
                    String name = c.getString(c.getColumnIndexOrThrow(People.NAME));
                    Log.i(TAG, "Response : " + "Selected contact : " + name);
                }
            }
            break;
        }
    }

    private void clearCredentials()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final Editor edit = prefs.edit();
        edit.remove(OAuth.OAUTH_TOKEN);
        edit.remove(OAuth.OAUTH_TOKEN_SECRET);
        edit.commit();
    }
}
