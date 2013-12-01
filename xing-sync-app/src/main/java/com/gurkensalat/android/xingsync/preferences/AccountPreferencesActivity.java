package com.gurkensalat.android.xingsync.preferences;

import com.gurkensalat.android.xingsync.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class AccountPreferencesActivity extends PreferenceActivity
{
	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		addPreferencesFromResource(R.xml.preferences_resources);
	}
}