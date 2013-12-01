package com.gurkensalat.android.xingsync.api;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.gurkensalat.android.xingsync.preferences.SyncPrefs_;

@EBean
public class MeCall // implements XingApiCall
{
	private static final String TAG = "xingsync.MeCall";

	@Pref
	SyncPrefs_ syncPrefs;

	public JSONObject perform(final Object... args)
	{
		JSONObject json = new JSONObject();

		if (syncPrefs.debugMockApiCalls().get())
		{
			StringBuffer sb = new StringBuffer(1024);

			sb.append("{");
			sb.append("  \"users\": [");
			sb.append("    {");
			sb.append("      \"id\": \"3382304_64b174\",");
			sb.append("      \"first_name\": \"Hakan\",");
			sb.append("      \"last_name\": \"Tandogan\",");
			sb.append("      \"display_name\": \"Hakan Tandogan\"");
			sb.append("    }");
			sb.append("  ]");
			sb.append("}");

			Log.i(TAG, sb.toString());

			try
			{
				json = new JSONObject(sb.toString());
			}
			catch (JSONException e)
			{
				Log.e(TAG, "while parsing answer string", e);
			}
		}
		else
		{
			Log.i(TAG, "Hardcoded Mock for now");
		}

		// Log.i(TAG, json.toString());

		return json;
	}

	public User performAndParse(final Object... args)
	{
		User user = null;
		JSONObject json = perform(args);

		try
		{
			user = User.fromJSON(json);
		}
		catch (JSONException e)
		{
			Log.e(TAG, "While parsing JSON", e);
		}

		return user;
	}
}
