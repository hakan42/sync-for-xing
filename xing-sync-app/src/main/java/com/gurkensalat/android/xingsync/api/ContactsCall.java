package com.gurkensalat.android.xingsync.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.gurkensalat.android.xingsync.preferences.SyncPrefs_;

@EBean
public class ContactsCall
{
	private static Logger LOG = LoggerFactory.getLogger(ContactsCall.class);

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
			sb.append("      },");
			sb.append("      {");
			sb.append("        \"id\": \"12824542_5078cc\",");
			sb.append("        \"first_name\": \"Petra\",");
			sb.append("        \"last_name\": \"Ghotra\",");
			sb.append("        \"display_name\": \"Petra Ghotra\",");
			sb.append("        \"active_email\": null");
			sb.append("      }");
			sb.append("    ]");
			sb.append("  }");
			sb.append("}");

			LOG.info(sb.toString());

			try
			{
				json = new JSONObject(sb.toString());
			}
			catch (JSONException e)
			{
				LOG.error("while parsing answer string", e);
			}
		}
		else
		{
			LOG.info("Hardcoded Mock for now");
		}

		return json;
	}

	public List<User> parse(JSONObject json)
	{
		List<User> allUsers = new ArrayList<User>();

		if (json != null)
		{
			json = json.optJSONObject("contacts");
			// System.err.println("OBJECT-contacts: " + json);
			if (json != null)
			{
				JSONArray array = json.optJSONArray("users");
				// System.err.println("ARRAY-users: " + array);
				if ((array != null) && (array.length() > 0))
				{
					for (int i = 0; i < array.length(); i++)
					{
						JSONObject innerJson = array.optJSONObject(i);

						// System.err.println("INNER #" + i + ": " + innerJson);
						try
						{
							User user = User.fromJSON(innerJson);
							if (user != null)
							{
								allUsers.add(user);
							}
						}
						catch (JSONException e)
						{
							LOG.error("while creating inner JSON object", e);
						}
					}
				}
			}
		}

		return allUsers;
	}

	public List<User> performAndParse(final Object... args)
	{
		JSONObject json = perform(args);
		return parse(json);
	}
}
