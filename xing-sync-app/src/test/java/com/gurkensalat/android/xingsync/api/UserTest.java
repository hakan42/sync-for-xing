package com.gurkensalat.android.xingsync.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

		JSONObject actual = new JSONObject(sb.toString());
		assertNotNull("JSON object could not be created", actual);

		json = actual;
	}

	@Test
	public void parseMeCall() throws JSONException
	{
		User actual = User.fromJSON(json);
		assertNotNull("User could not be created", actual);
		assertEquals("Id not correctly parsed", "3382304", actual.getId());
		assertEquals("First Name not correctly parsed", "Hakan", actual.getFirstName());
		assertEquals("Last Name not correctly parsed", "Tandogan", actual.getLastName());
		assertEquals("Display Name not correctly parsed", "Hakan Tandogan", actual.getDisplayName());
	}
}
