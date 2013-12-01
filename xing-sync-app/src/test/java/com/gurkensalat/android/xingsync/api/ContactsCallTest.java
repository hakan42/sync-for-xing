package com.gurkensalat.android.xingsync.api;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ContactsCallTest
{
	private ContactsCall testable;

	private JSONObject json;

	@Before
	public void setupMocks()
	{
		testable = new ContactsCall();
		// ContactsCall_.getInstance_(null);

		assertThat("API Call object could not be created", testable, is(notNullValue()));
	}

	@Before
	public void populateJson() throws JSONException
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

		JSONObject actual = new JSONObject(sb.toString());
		assertThat("JSON object could not be created", actual, is(notNullValue()));

		json = actual;
	}

	@Test
	public void parseContactsCall() throws JSONException
	{
		List<User> actual = testable.parse(json);
		assertThat("Users list could not be created", actual, is(notNullValue()));
	}
}
