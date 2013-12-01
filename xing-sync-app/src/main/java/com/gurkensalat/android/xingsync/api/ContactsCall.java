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
			StringBuffer sb = new StringBuffer(1024);

			sb.append("{");
			sb.append("  \"contacts\": {");
			sb.append("    \"total\": 297,");
			sb.append("    \"users\": [");
			sb.append("      {");
			sb.append("        \"id\": \"6841253_007b9e\",");
			sb.append("        \"first_name\": \"Hasmet\",");
			sb.append("        \"last_name\": \"Acar\",");
			sb.append("        \"display_name\": \"Hasmet Acar\",");
			sb.append("        \"active_email\": null");
			sb.append("      },");
			sb.append("      {");
			sb.append("        \"id\": \"4261890_ffd916\",");
			sb.append("        \"first_name\": \"Murat\",");
			sb.append("        \"last_name\": \"Acun\",");
			sb.append("        \"display_name\": \"Murat Acun\",");
			sb.append("        \"active_email\": null");
			sb.append("      }");
			sb.append("    ]");
			sb.append("  }");
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
