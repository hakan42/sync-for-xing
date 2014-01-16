package com.tandogan.android.xingsync.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Contact
{
	private String id;

	private String firstName;

	private String lastName;

	private String displayName;

	public static Contact fromJSON(JSONObject json) throws JSONException
	{
		Contact u = null;

		if (json != null)
		{
			if (json.has("users"))
			{
				JSONArray array = json.getJSONArray("users");
				if (array.length() > 0)
				{
					json = array.getJSONObject(0);
					System.err.println(json.getClass().getName());
				}
			}
		}

		if (json.has("id"))
		{
			u = new Contact();

			u.setId(json.getString("id"));

			// XING privacy protection feature :-)
			int underscorePosition = u.getId().indexOf("_");
			if (underscorePosition > 0)
			{
				u.setId(u.getId().substring(0, underscorePosition));
			}

			if (json.has("first_name"))
			{
				u.setFirstName(json.getString("first_name"));
			}

			if (json.has("last_name"))
			{
				u.setLastName(json.getString("last_name"));
			}

			if (json.has("display_name"))
			{
				u.setDisplayName(json.getString("display_name"));
			}
		}

		return u;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getId()
	{
		return id;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
}
