package com.gurkensalat.android.xingsync.oauth;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gurkensalat.android.xingsync.keys.XingOAuthKeys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;

/**
 * An asynchronous task that communicates with Google to retrieve a request
 * token. (OAuthGetRequestToken)
 * 
 * After receiving the request token from Google, show a browser to the user to
 * authorize the Request Token. (OAuthAuthorizeToken)
 * 
 */
public class OAuthRequestTokenTask extends AsyncTask<Void, Void, Void>
{
	private static Logger LOG = LoggerFactory.getLogger(OAuthRequestTokenTask.class);

	private Context context;

	private OAuthService service;

	private Token requestToken;

	// TODO convert to annotated preferences later
	private SharedPreferences prefs;

	/**
	 * 
	 * We pass the OAuth consumer and provider.
	 * 
	 * @param context
	 *            Required to be able to start the intent to launch the browser.
	 */
	public OAuthRequestTokenTask(Context context, SharedPreferences prefs, OAuthService service)
	{
		this.context = context;
		this.prefs = prefs;
		this.service = service;
	}

	/**
	 * 
	 * Retrieve the OAuth Request Token and present a browser to the user to
	 * authorize the token.
	 * 
	 */
	@Override
	protected Void doInBackground(Void... params)
	{
		LOG.info("doInBackground");

		try
		{
			LOG.info("Retrieving request token from XING servers");
			// Step Two: Get the request token

			requestToken = service.getRequestToken();
			LOG.info("Request Token is: '" + requestToken + "'");

			// Write request token info to preferences
			LOG.info("====== putting token into preferences ======");
			LOG.info("====== request token: '" + requestToken + "' ======");
			final Editor edit = prefs.edit();
			edit.putString(XingOAuthKeys.REQUEST_TOKEN, requestToken.getToken());
			edit.putString(XingOAuthKeys.REQUEST_TOKEN_SECRET, requestToken.getSecret());
			edit.commit();
			LOG.info("====== done putting token into preferences ======");

			// Step Three: Making the user validate your request token

			final String url = service.getAuthorizationUrl(requestToken);

			LOG.info("Popping a browser with the authorize URL : " + url);
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
			        | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);
			context.startActivity(intent);
		}
		catch (Exception e)
		{
			LOG.error("Error during OAUth request token creation", e);
		}

		return null;
	}
}
