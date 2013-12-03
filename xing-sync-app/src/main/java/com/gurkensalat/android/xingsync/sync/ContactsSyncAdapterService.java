package com.gurkensalat.android.xingsync.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.accounts.Account;
import android.accounts.OperationCanceledException;
import android.app.Service;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;

import com.googlecode.androidannotations.annotations.EService;

@EService
public class ContactsSyncAdapterService extends Service
{
	private static Logger LOG = LoggerFactory.getLogger(ContactsSyncAdapterService.class);

	private static ContactSyncAdapter sSyncAdapter = null;

	private static ContentResolver mContentResolver = null;

	public ContactsSyncAdapterService()
	{
		super();
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		IBinder ret = null;
		ret = getSyncAdapter().getSyncAdapterBinder();
		return ret;
	}

	private ContactSyncAdapter getSyncAdapter()
	{
		if (sSyncAdapter == null)
		{
			sSyncAdapter = new ContactSyncAdapter(this);
		}

		return sSyncAdapter;
	}

	static void performSync(Context context, Account account, Bundle extras, String authority, ContentProviderClient provider,
	        SyncResult syncResult) throws OperationCanceledException
	{
		mContentResolver = context.getContentResolver();
		LOG.info("performSync: " + account.toString());
		// This is where the magic will happen!
	}

}