package com.gurkensalat.android.xingsync.oauth;

import java.net.URLEncoder;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.gurkensalat.android.xingsync.SyncPrefs_;
import com.gurkensalat.android.xingsync.keys.XingOAuthKeys;

@EActivity
public class PrepareRequestTokenActivity extends Activity
{
	private static String TAG = "xingsync.PrepareRequestTokenActivity";

	private OAuthConsumer consumer;
	private OAuthProvider provider;

	@Pref
	SyncPrefs_ syncPrefs;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.i(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		try
		{
			System.setProperty("debug", "true");
			this.consumer = new CommonsHttpOAuthConsumer(XingOAuthKeys.CONSUMER_KEY, XingOAuthKeys.CONSUMER_SECRET);
			this.provider = new CommonsHttpOAuthProvider(XingOAuthKeys.REQUEST_URL + "?scope="
			        + URLEncoder.encode(XingOAuthKeys.SCOPE, XingOAuthKeys.ENCODING), XingOAuthKeys.ACCESS_URL,
			        XingOAuthKeys.AUTHORIZE_URL);
		}
		catch (Exception e)
		{
			Log.e(TAG, "Error creating consumer / provider", e);
		}

		Log.i(TAG, "Starting task to retrieve request token.");
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
		final Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(XingOAuthKeys.OAUTH_CALLBACK_SCHEME))
		{
			Log.i(TAG, "Callback received : " + uri);
			Log.i(TAG, "Retrieving Access Token");
			// TODO Avoid passing the current prefs into access task
			SharedPreferences prefs = syncPrefs.getSharedPreferences();
			new RetrieveAccessTokenTask(this, consumer, provider, prefs).execute(uri);
			finish();
		}
	}

}
