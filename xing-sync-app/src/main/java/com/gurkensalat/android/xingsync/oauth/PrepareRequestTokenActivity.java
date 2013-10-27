package com.gurkensalat.android.xingsync.oauth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.googlecode.androidannotations.annotations.EActivity;

@EActivity
public class PrepareRequestTokenActivity extends Activity
{
	private static String TAG = "xing-sync.PrepareRequestTokenActivity";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// NO VIEW FOR THIS ACTIVITY
		// setContentView(R.layout.main);
		Log.i(TAG, "onCreate() called.");
		finish();
	}

}
