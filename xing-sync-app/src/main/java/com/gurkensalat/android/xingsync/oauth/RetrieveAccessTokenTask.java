package com.gurkensalat.android.xingsync.oauth;

import java.net.URI;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.scribe.model.OAuthConstants;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;

import com.gurkensalat.android.xingsync.R;
import com.gurkensalat.android.xingsync.keys.XingOAuthKeys;
import com.gurkensalat.android.xingsync.sync.AddAccountActivity_;

public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void>
{
	private static Logger LOG = LoggerFactory.getLogger(RetrieveAccessTokenTask.class);

	private Context context;

	private OAuthService service;

	private Token requestToken;

	// TODO convert to annotated preferences later
	private SharedPreferences prefs;

	public RetrieveAccessTokenTask(Context context, SharedPreferences prefs, OAuthService service, Token requestToken)
	{
		this.context = context;
		this.prefs = prefs;
		this.service = service;
		this.requestToken = requestToken;
	}

	/**
	 * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret
	 * for future API calls.
	 */
	@Override
	protected Void doInBackground(Uri... params)
	{
		LOG.info("doInBackground");

		final Uri uri = params[0];

		LOG.info("URI received is: '" + uri + "'");

		try
		{
			// Step Four: Get the access Token

			LOG.info("doInBackground(), 1");

			Verifier verifier = null;

			List<NameValuePair> parameters = URLEncodedUtils.parse(new URI(uri.toString()), "UTF-8");
			LOG.info("Parsed {} paremeters", parameters.size());
			for (NameValuePair nvp : parameters)
			{
				LOG.info("  '" + nvp.getName() + "' : '" + nvp.getValue() + "'");
				if (OAuthConstants.VERIFIER.equals(nvp.getName()))
				{
					verifier = new Verifier(nvp.getValue());
				}
			}

			LOG.info("doInBackground(), 2");

			if (verifier == null)
			{
				LOG.info("Verifier could not be extracted!");
			}
			else
			{
				// the requestToken you had from step 2
				LOG.info("Request Token: '" + requestToken + "'");
				Token accessToken = service.getAccessToken(requestToken, verifier);
				LOG.info("Access Token: '" + accessToken + "'");

				LOG.info("doInBackground(), 3");

				// Write access and request token info to preferences
				LOG.info("====== putting token into preferences ======");
				LOG.info("====== request token: '" + requestToken + "' ======");
				LOG.info("====== access token: '" + accessToken + "' ======");
				final Editor edit = prefs.edit();
				edit.putString(XingOAuthKeys.REQUEST_TOKEN, requestToken.getToken());
				edit.putString(XingOAuthKeys.REQUEST_TOKEN_SECRET, requestToken.getSecret());
				edit.putString(XingOAuthKeys.ACCESS_TOKEN, accessToken.getToken());
				edit.putString(XingOAuthKeys.ACCESS_TOKEN_SECRET, accessToken.getSecret());
				edit.commit();
				LOG.info("====== done putting token into preferences ======");

				LOG.info("doInBackground(), 4");

				Intent intent = new Intent(context, AddAccountActivity_.class);
				intent.setAction(context.getString(R.string.LOGIN_INTENT_ACTION));
				// TODO Not sure if we really want to start a new history stack
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		}
		catch (Exception e)
		{
			LOG.info("doInBackground(), error");

			LOG.error("OAuth - Access Token Retrieval Error", e);
		}

		LOG.info("OAuth - Access Token Retrieval Error");

		return null;
	}
}
