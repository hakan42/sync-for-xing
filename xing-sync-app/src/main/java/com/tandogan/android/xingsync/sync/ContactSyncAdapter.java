package com.tandogan.android.xingsync.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.accounts.Account;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/* package protected */class ContactSyncAdapter extends AbstractThreadedSyncAdapter
{
	private Logger LOG = LoggerFactory.getLogger(ContactSyncAdapter.class);

	private Context mContext;

	public ContactSyncAdapter(Context context)
	{
		super(context, true);
		mContext = context;
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider,
	        SyncResult syncResult)
	{
		try
		{
			ContactsSyncAdapterService.performSync(mContext, account, extras, authority, provider, syncResult);
		}
		catch (OperationCanceledException e)
		{
		}
	}
}