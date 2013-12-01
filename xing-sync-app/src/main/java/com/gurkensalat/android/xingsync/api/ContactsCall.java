package com.gurkensalat.android.xingsync.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.gurkensalat.android.xingsync.preferences.SyncPrefs_;

import de.akquinet.android.androlog.Log;

public class ContactsCall
{
	private static final String TAG = "xingsync.ContactsCall";

	@Pref
	SyncPrefs_ syncPrefs;

	public JSONObject perform(final Object... args)
	{
		JSONObject json = new JSONObject();

		if (syncPrefs.debugMockApiCalls().get())
		{
		}
		else
		{
			Log.i(TAG, "Hardcoded Mock for now");
		}

		// Log.i(TAG, json.toString());

		return json;
	}

	public List<User> performAndParse(final Object... args)
	{
		List<User> allUsers = new ArrayList<User>();

		// User user = null;
		// JSONObject json = perform(args);
		//
		// try
		// {
		// user = User.fromJSON(json);
		// }
		// catch (JSONException e)
		// {
		// Log.e(TAG, "While parsing JSON", e);
		// }
		//
		// return user;

		return allUsers;
	}
}
