package com.gurkensalat.android.xingsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.googlecode.androidannotations.annotations.EActivity;

@EActivity(R.layout.main)
public class HelloAndroidActivity extends Activity
{
	private static String TAG = "xing-sync";

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
}
