package com.gurkensalat.android.xingsync.oauth;

import java.net.URLEncoder;

import com.gurkensalat.android.xingsync.Constants;
import com.gurkensalat.android.xingsync.keys.XingOAuthKeys;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Prepares a OAuthConsumer and OAuthProvider
 * 
 * OAuthConsumer is configured with the consumer key & consumer secret.
 * OAuthProvider is configured with the 3 OAuth endpoints.
 * 
 * Execute the OAuthRequestTokenTask to retrieve the request, and authorize the
 * request.
 * 
 * After the request is authorized, a callback is made here.
 * 
 */
public class PrepareRequestTokenActivity extends Activity
{
    /** The tag used to log to adb console. **/
    private static final String TAG = PrepareRequestTokenActivity.class.getName().substring(Constants.PACKAGE_PREFIX_LENGTH);

    private OAuthConsumer consumer;
    private OAuthProvider provider;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        try
        {
            System.setProperty("debug", "true");
            this.consumer = new CommonsHttpOAuthConsumer(XingOAuthKeys.CONSUMER_KEY, XingOAuthKeys.CONSUMER_SECRET);
            this.provider = new CommonsHttpOAuthProvider(XingOAuthKeys.REQUEST_URL + "?scope=" + URLEncoder.encode(XingOAuthKeys.SCOPE, XingOAuthKeys.ENCODING),
                    XingOAuthKeys.ACCESS_URL, XingOAuthKeys.AUTHORIZE_URL);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error creating consumer / provider", e);
        }

        Log.i(TAG, "Starting task to retrieve request token.");
        // HAKAN - REACTIVATE THIS
        new OAuthRequestTokenTask(this, consumer, provider).execute();
    }

    /**
     * Called when the OAuthRequestTokenTask finishes (user has authorized the
     * request token). The callback URL will be intercepted here.
     */
    @Override
    public void onNewIntent(Intent intent)
    {
        Log.i(TAG, "onNewIntent()");
        super.onNewIntent(intent);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final Uri uri = intent.getData();
        if (uri != null && uri.getScheme().equals(XingOAuthKeys.OAUTH_CALLBACK_SCHEME))
        {
            Log.i(TAG, "Callback received : " + uri);
            Log.i(TAG, "Retrieving Access Token");
            new RetrieveAccessTokenTask(this, consumer, provider, prefs).execute(uri);
            finish();
        }
    }
}
