package com.tandogan.android.xingsync.sync;

import org.androidannotations.annotations.EActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.accounts.AccountAuthenticatorActivity;
import android.os.Bundle;
import android.widget.Toast;

@EActivity
public class OnlyOneAccountErrorActivity extends AccountAuthenticatorActivity
{
	private Logger LOG = LoggerFactory.getLogger(OnlyOneAccountErrorActivity.class);

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
