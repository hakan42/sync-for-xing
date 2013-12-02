package com.gurkensalat.android.xingsync.preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.gurkensalat.android.xingsync.R;

@EActivity
public class AccountPreferencesActivity extends PreferenceActivity
{
	private static Logger LOG = LoggerFactory.getLogger(AccountPreferencesActivity.class);

	@Pref
	SyncPrefs_ syncPrefs;

	@Override
	public void onCreate(Bundle icicle)
	{
		LOG.info("onCreate() called");
		super.onCreate(icicle);
		addPreferencesFromResource(R.xml.preferences_resources);

		// TODO replease with PreferencesFragment code
		// http://developer.android.com/reference/android/preference/PreferenceActivity.html
		// http://developer.android.com/reference/android/preference/PreferenceFragment.html
	}

	@Override
	protected void onStart()
	{
		LOG.info("onStart() called");

		super.onStart();
		// preferences.registerOnSharedPreferenceChangeListener(backupListener);
	}

	@Override
	protected void onStop()
	{
		LOG.info("onStop() called");

		// preferences.unregisterOnSharedPreferenceChangeListener(backupListener);
		super.onStop();
		savePreferencesToAnnotation();
	}

	private void savePreferencesToAnnotation()
	{

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		LOG.info("prefs is " + prefs.toString());

		// TODO make debugMockApiCalls Boolean.FALSE before beta deployment
		boolean debugMockApiCalls = prefs.getBoolean(getResources().getString(R.string.prefs_debug_mock_api_calls_key),
		        Boolean.TRUE);
		syncPrefs.edit().debugMockApiCalls().put(debugMockApiCalls).apply();
	}
}