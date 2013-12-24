package com.gurkensalat.android.xingsync;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.gurkensalat.android.xingsync.api.ContactsCall;
import com.gurkensalat.android.xingsync.api.MeCall;
import com.gurkensalat.android.xingsync.preferences.SyncPrefs_;
import com.gurkensalat.android.xingsync.sync.AccountAuthenticatorService;
import com.gurkensalat.android.xingsync.sync.AddAccountActivity_;

@EActivity
public class HelloAndroidActivity extends Activity
{
	private static Logger LOG = LoggerFactory.getLogger(HelloAndroidActivity.class);

	@ViewById(R.id.oauth_mock_hint)
	TextView oauth_mock_hint;

	@Bean
	ContactsCall contactsCall;

	@Bean
	MeCall meCall;

	@Pref
	SyncPrefs_ syncPrefs;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		LOG.info("onCreate");
		super.onCreate(savedInstanceState);

		AccountAuthenticatorService.listAccounts(getApplicationContext());

		if (AccountAuthenticatorService.hasAccount(getApplicationContext()))
		{
			LOG.info("We have our own account");

			Intent intent = new Intent(Settings.ACTION_SYNC_SETTINGS);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

			// To show only our own accounts
			// intent.putExtra(Settings.EXTRA_AUTHORITIES, new String[] {
			// getResources().getString(R.string.ACCOUNT_TYPE) });

			startActivity(intent);
		}
		else
		{
			LOG.info("No, no account of appropiate type");

			// TODO make this dependent on preference
			// oauth_mock_hint.setVisibility(View.VISIBLE);

			setContentView(R.layout.login);

			// Intent intent = new Intent(Settings.ACTION_SYNC_SETTINGS);
			// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			//
			// startActivity(intent);
		}
	}

	@Click(R.id.btn_clear_credentials)
	void clearCredentials(View clickedView)
	{
		LOG.info("About to clear credentials");
		syncPrefs.edit().oauth_token().put("").apply();
		syncPrefs.edit().oauth_token_secret().put("").apply();
	}

	@Click(R.id.btn_oauth_login)
	void launchOAuthLogin(View clickedView)
	{
		LOG.info("About to launch OAuth dance");

		// Do we really need the NEW_TASK?
		// http://stackoverflow.com/questions/3918517/calling-startactivity-from-outside-of-an-activity-context
		AddAccountActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
		finish();
	}
}
