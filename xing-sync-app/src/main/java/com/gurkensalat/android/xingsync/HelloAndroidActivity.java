package com.gurkensalat.android.xingsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.gurkensalat.android.xingsync.oauth.OAuthButtonsFragment;
import com.gurkensalat.android.xingsync.oauth.OAuthResultsFragment;
import com.gurkensalat.android.xingsync.oauth.OAuthSecretsFragment;
import com.gurkensalat.android.xingsync.oauth.PrepareRequestTokenActivity_;

@EActivity(R.layout.main)
public class HelloAndroidActivity extends Activity
{
	private static String TAG = "xing-sync";

	@Pref
	SyncPrefs_ syncPrefs;

	@FragmentById(R.id.main_layout_fragment_oauth_secrets)
	OAuthSecretsFragment secretsFragment;

	@FragmentById(R.id.main_layout_fragment_oauth_buttons)
	OAuthButtonsFragment buttonsFragment;

	@FragmentById(R.id.main_layout_fragment_oauth_results)
	OAuthResultsFragment resultsFragment;

	@ViewById(R.id.oauth_token)
	TextView oauthToken;

	@ViewById(R.id.oauth_token_secret)
	TextView oauthTokenSecret;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		// setContentView(R.layout.account_entry);

		Intent intent = new Intent(Settings.ACTION_SYNC_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

		// To show only our own accounts
		intent.putExtra(Settings.EXTRA_AUTHORITIES, new String[] { getResources().getString(R.string.ACCOUNT_TYPE) });

		startActivity(intent);
	}

	@Override
	protected void onResume()
	{
		// onResume() is always called just before activity is displayed
		Log.i(TAG, "onResume()");
		super.onResume();
		displaySecrets();
	}

	@Click(R.id.btn_launch_oauth)
	void launchOAuth(View clickedView)
	{
		Log.i(TAG, "About to launch OAuth dance");
		PrepareRequestTokenActivity_.intent(clickedView.getContext()).start();
	}

	@Click(R.id.btn_clear_credentials)
	void clearCredentials(View clickedView)
	{
		Log.i(TAG, "About to clear credentials");
		syncPrefs.oauth_token().put("");
		syncPrefs.oauth_token_secret().put("");
		displaySecrets();
	}

	private void displaySecrets()
	{
		Log.i(TAG, "oauth_token: '" + syncPrefs.oauth_token().get() + "'");
		if (oauthToken != null)
		{
			oauthToken.setText(syncPrefs.oauth_token().get());
		}

		Log.i(TAG, "oauth_token_secret: '" + syncPrefs.oauth_token_secret().get() + "'");
		if (oauthTokenSecret != null)
		{
			oauthTokenSecret.setText(syncPrefs.oauth_token_secret().get());
		}
	}
}
