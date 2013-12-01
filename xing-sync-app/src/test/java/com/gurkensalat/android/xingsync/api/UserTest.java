package com.gurkensalat.android.xingsync.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserTest
{
	private JSONObject json;

	@org.junit.Before
	public void populateJson() throws JSONException
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

		json = new JSONObject(sb.toString());
		Assert.assertNotNull("JSON object could not be created", json);
	}

	@Test
	public void parseMeCall()
	{
		User user = User.fromJSON(json);
		Assert.assertNotNull("User could not be created", user);
	}
}
