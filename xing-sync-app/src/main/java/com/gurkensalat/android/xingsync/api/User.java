package com.gurkensalat.android.xingsync.api;

import org.json.JSONObject;

public class User
{
	private String id;

	private String firstName;

	private String lastName;

	private String displayName;

	public static User fromJSON(JSONObject json)
	{
		User u = new User();

		// TODO really parse the JSON
		u.setId("3382304");
		u.setFirstName("Hakan");
		u.setLastName("Tandogan");
		u.setDisplayName(u.getFirstName() + " " + u.getLastName());

		// TODO create unit test for JSON parsing

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
