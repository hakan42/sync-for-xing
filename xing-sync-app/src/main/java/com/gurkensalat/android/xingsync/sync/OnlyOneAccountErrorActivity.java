package com.gurkensalat.android.xingsync.sync;

import com.googlecode.androidannotations.annotations.EActivity;

import android.accounts.AccountAuthenticatorActivity;
import android.os.Bundle;
import android.widget.Toast;

@EActivity
public class OnlyOneAccountErrorActivity extends AccountAuthenticatorActivity
{
	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		// Toast.makeText(this, R.string.sync_only_one_account,
		// Toast.LENGTH_LONG).show();
		Toast.makeText(this, "ONLY ONE ACCOUNT", Toast.LENGTH_LONG).show();
		finish();
	}
}
