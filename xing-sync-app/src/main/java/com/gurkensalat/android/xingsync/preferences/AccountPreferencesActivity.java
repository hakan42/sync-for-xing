package com.gurkensalat.android.xingsync.preferences;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.gurkensalat.android.xingsync.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

@EActivity
public class AccountPreferencesActivity extends PreferenceActivity
{
	private static final String TAG = "xingsync.AccountPreferencesActivity";

	@Pref
	SyncPrefs_ syncPrefs;

	@Override
	public void onCreate(Bundle icicle)
	{
		Log.i(TAG, "onCreate() called");
		super.onCreate(icicle);
		addPreferencesFromResource(R.xml.preferences_resources);

		// TODO replease with PreferencesFragment code
		// http://developer.android.com/reference/android/preference/PreferenceActivity.html
		// http://developer.android.com/reference/android/preference/PreferenceFragment.html
	}

	@Override
	protected void onStart()
	{
		Log.i(TAG, "onStart() called");

		super.onStart();
		// preferences.registerOnSharedPreferenceChangeListener(backupListener);
	}

	@Override
	protected void onStop()
	{
		Log.i(TAG, "onStop() called");

		// preferences.unregisterOnSharedPreferenceChangeListener(backupListener);
		super.onStop();
	}
}