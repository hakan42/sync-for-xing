package com.gurkensalat.android.xingsync.preferences;

import com.gurkensalat.android.xingsync.R;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

public class AccountPreferences extends PreferenceActivity {

	public static final String TAG = "AccountPreferences";
	private boolean shouldForceSync = false;

	@Override
	public void onCreate( Bundle icicle ) {
		super.onCreate(icicle);
		Log.i(TAG, "onCreate");
		addPreferencesFromResource(R.xml.preferences_resources);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause(): shouldForceSync == " + shouldForceSync);
		if( shouldForceSync ) {
			// AccountAuthenticatorService.resyncAccount(this);
			Log.i(TAG, "onPause(): shouldForceSync true");
		}
	}

	Preference.OnPreferenceChangeListener syncToggle = new Preference.OnPreferenceChangeListener() {
		public boolean onPreferenceChange( Preference preference, Object newValue ) {
			shouldForceSync = true;
			return true;
		}
	};
}