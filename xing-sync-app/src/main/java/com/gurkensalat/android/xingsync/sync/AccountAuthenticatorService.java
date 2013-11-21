package com.gurkensalat.android.xingsync.sync;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;

import com.googlecode.androidannotations.annotations.EService;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.gurkensalat.android.xingsync.R;
import com.gurkensalat.android.xingsync.SyncPrefs_;

import fm.last.android.activity.AccountAccessPrompt;
import fm.last.api.MD5;

@EService
public class AccountAuthenticatorService extends Service
{
	private static final String TAG = "xingsync.AccountAuthenticatorService";

	private static AccountAuthenticatorImpl sAccountAuthenticator = null;

	@Pref
	SyncPrefs_ syncPrefs;

	public AccountAuthenticatorService()
	{
		super();
	}

	private static class AccountAuthenticatorImpl extends AbstractAccountAuthenticator
	{
		private Context mContext;

		public AccountAuthenticatorImpl(Context context)
		{
			super(context);
			mContext = context;
		}

		public static Bundle addAccount(Context ctx, String username, String password)
		{
			Log.i(TAG, "addAccount('" + username + "', '" + password + "')");

			Bundle result = null;
			Account account = new Account(username, ctx.getString(R.string.ACCOUNT_TYPE));
			AccountManager am = AccountManager.get(ctx);
			if (am.addAccountExplicitly(account, MD5.getInstance().hash(password), null))
			{
				result = new Bundle();
				result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
				result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
			}
			return result;
		}

		public static Boolean hasAccount(Context ctx)
		{
			AccountManager am = AccountManager.get(ctx);
			Account[] accounts = am.getAccountsByType(ctx.getString(R.string.ACCOUNT_TYPE));
			if (accounts != null && accounts.length > 0)
				return true;
			else
				return false;
		}

		public static void removeAccount(Context ctx)
		{
			Log.i(TAG, "removeAccount");
			AccountManager am = AccountManager.get(ctx);
			Account[] accounts = am.getAccountsByType(ctx.getString(R.string.ACCOUNT_TYPE));
			for (Account account : accounts)
			{
				am.removeAccount(account, null, null);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.accounts.AbstractAccountAuthenticator#addAccount(android
		 * .accounts.AccountAuthenticatorResponse, java.lang.String,
		 * java.lang.String, java.lang.String[], android.os.Bundle)
		 */
		@Override
		public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType,
		        String[] requiredFeatures, Bundle options) throws NetworkErrorException
		{
			Log.i(TAG, "addAccount");
			Bundle result;

			if (hasAccount(mContext))
			{
				result = new Bundle();
				Intent i = new Intent(mContext, OnlyOneAccountErrorActivity_.class);
				i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
				result.putParcelable(AccountManager.KEY_INTENT, i);
				return result;
			}
			else
			{
				result = new Bundle();
				Intent i = new Intent(mContext, AddAccountActivity_.class);
				i.setAction(mContext.getString(R.string.LOGIN_INTENT_ACTION));
				i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
				result.putParcelable(AccountManager.KEY_INTENT, i);
			}
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.accounts.AbstractAccountAuthenticator#confirmCredentials
		 * (android.accounts.AccountAuthenticatorResponse,
		 * android.accounts.Account, android.os.Bundle)
		 */
		@Override
		public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options)
		{
			// TODO Auto-generated method stub
			Log.i(TAG, "confirmCredentials");
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.accounts.AbstractAccountAuthenticator#editProperties(
		 * android.accounts.AccountAuthenticatorResponse, java.lang.String)
		 */
		@Override
		public Bundle editProperties(AccountAuthenticatorResponse response, String accountType)
		{
			// TODO Auto-generated method stub
			Log.i(TAG, "editProperties");
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.accounts.AbstractAccountAuthenticator#getAuthToken(android
		 * .accounts.AccountAuthenticatorResponse, android.accounts.Account,
		 * java.lang.String, android.os.Bundle)
		 */
		@Override
		public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options)
		        throws NetworkErrorException
		{
			Log.i(TAG, "getAuthToken");

			String api_key = options.getString("api_key");
			String api_secret = options.getString("api_secret");

			AccountManager am = AccountManager.get(mContext);
			String user = account.name.toLowerCase().trim();
			String md5Password = am.getPassword(account);
			String authToken = MD5.getInstance().hash(user + md5Password);

			Log.i(TAG, "getAuthToken");

			Bundle result = new Bundle();
			Intent i = new Intent(mContext, AccountAccessPrompt.class);
			i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
			i.putExtra("api_key", api_key);
			i.putExtra("api_secret", api_secret);
			i.putExtra("user", user);
			i.putExtra("authToken", authToken);
			result.putParcelable(AccountManager.KEY_INTENT, i);
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.accounts.AbstractAccountAuthenticator#getAuthTokenLabel
		 * (java.lang.String)
		 */
		@Override
		public String getAuthTokenLabel(String authTokenType)
		{
			// TODO Auto-generated method stub
			Log.i(TAG, "getAuthTokenLabel");
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.accounts.AbstractAccountAuthenticator#hasFeatures(android
		 * .accounts.AccountAuthenticatorResponse, android.accounts.Account,
		 * java.lang.String[])
		 */
		@Override
		public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features)
		        throws NetworkErrorException
		{
			// TODO Auto-generated method stub
			Log.i(TAG, "hasFeatures: " + features);
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.accounts.AbstractAccountAuthenticator#updateCredentials
		 * (android.accounts.AccountAuthenticatorResponse,
		 * android.accounts.Account, java.lang.String, android.os.Bundle)
		 */
		@Override
		public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType,
		        Bundle options)
		{
			// TODO Auto-generated method stub
			Log.i(TAG, "updateCredentials");
			return null;
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		IBinder ret = null;
		if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))
			ret = getAuthenticator().getIBinder();
		return ret;
	}

	public static void addAccount(Context ctx, String username, String password, Parcelable response)
	{
		AccountAuthenticatorResponse authResponse = (AccountAuthenticatorResponse) response;
		Bundle result = AccountAuthenticatorImpl.addAccount(ctx, username, password);
		if (authResponse != null)
			authResponse.onResult(result);
	}

	public static Boolean hasAccount(Context ctx)
	{
		return AccountAuthenticatorImpl.hasAccount(ctx);
	}

	public static void removeAccount(Context ctx)
	{
		AccountAuthenticatorImpl.removeAccount(ctx);
	}

	public static void resyncAccount(Context context)
	{
		Log.i(TAG, "resyncAccount");

		// Editor editor =
		// PreferenceManager.getDefaultSharedPreferences(LastFMApplication.getInstance()).edit();
		// editor.putBoolean("do_full_sync", true);
		// editor.commit();
		// AccountManager am = AccountManager.get(context);
		// Account[] accounts =
		// am.getAccountsByType(context.getString(R.string.ACCOUNT_TYPE));
		// if (ContentResolver.getSyncAutomatically(accounts[0],
		// ContactsContract.AUTHORITY))
		// {
		// // Try turning it off and on again
		// ContentResolver.setSyncAutomatically(accounts[0],
		// ContactsContract.AUTHORITY, false);
		// ContentResolver.setSyncAutomatically(accounts[0],
		// ContactsContract.AUTHORITY, true);
		// }
	}

	private AccountAuthenticatorImpl getAuthenticator()
	{
		if (sAccountAuthenticator == null)
			sAccountAuthenticator = new AccountAuthenticatorImpl(this);
		return sAccountAuthenticator;
	}
}
