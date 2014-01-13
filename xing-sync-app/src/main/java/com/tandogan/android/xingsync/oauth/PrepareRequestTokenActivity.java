package com.tandogan.android.xingsync.oauth;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.XingApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.tandogan.android.xingsync.R;
import com.tandogan.android.xingsync.keys.XingOAuthKeys;
import com.tandogan.android.xingsync.preferences.SyncPrefs_;

@EActivity
public class PrepareRequestTokenActivity extends Activity
{
	private static Logger LOG = LoggerFactory.getLogger(PrepareRequestTokenActivity.class);

	private OAuthService service;

	@Pref
	SyncPrefs_ syncPrefs;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		LOG.info("onCreate()");
		super.onCreate(savedInstanceState);
		try
		{
			System.setProperty("debug", "true");

			// Step One: Create the OAuthService object
			ServiceBuilder builder = new ServiceBuilder().provider(XingApi.class).apiKey(XingOAuthKeys.CONSUMER_KEY)
			        .apiSecret(XingOAuthKeys.CONSUMER_SECRET);

			String oauthCallbackScheme = getString(R.string.oauth_callback_scheme);
			String oauthCallbackHost = getString(R.string.oauth_callback_host);
			String oauthCallbackUrl = oauthCallbackScheme + "://" + oauthCallbackHost;

			// TODO productive key is broken, have to use "oob"
			// oauthCallbackUrl = "oob";

			builder = builder.callback(oauthCallbackUrl);

			service = builder.build();

			// Request Token cannot be obtained here, we would get
			// NetworkOnMainThreadException exceptions
		}
		catch (Exception e)
		{
			LOG.error("Error creating OAuthService", e);
		}

		LOG.info("Starting task to retrieve request token.");

		// TODO Avoid passing the current prefs into request task
		SharedPreferences prefs = syncPrefs.getSharedPreferences();
		new OAuthRequestTokenTask(this, prefs, service).execute();
	}

	/**
	 * Called when the OAuthRequestTokenTask finishes (user has authorized the
	 * request token). The callback URL will be intercepted here.
	 */
	@Override
	public void onNewIntent(Intent intent)
	{
		LOG.info("onNewIntent()");

		String oauthCallbackScheme = getString(R.string.oauth_callback_scheme);

		super.onNewIntent(intent);
		final Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(oauthCallbackScheme))
		{
			LOG.info("Callback received : " + uri);

			// TODO Avoid passing the current prefs into access task
			SharedPreferences prefs = syncPrefs.getSharedPreferences();
			String token = prefs.getString(XingOAuthKeys.REQUEST_TOKEN, null);
			String secret = prefs.getString(XingOAuthKeys.REQUEST_TOKEN_SECRET, null);

			// TODO how to access the request token?
			// Put in preferences and re-fetch it maybe?
			Token requestToken = new Token(token, secret); // service.getRequestToken();
			LOG.info("Request Token is: '" + requestToken + "'");

			LOG.info("Retrieving Access Token");
			new RetrieveAccessTokenTask(getApplicationContext(), prefs, service, requestToken).execute(uri);
			finish();
		}
	}
}
