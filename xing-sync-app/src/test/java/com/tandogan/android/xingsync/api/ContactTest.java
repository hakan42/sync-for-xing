package com.tandogan.android.xingsync.api;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import android.provider.ContactsContract;

@RunWith(JUnit4.class)
public class ContactTest
{
	private JSONObject json;

	@Before
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
		assertThat("JSON object could not be created", actual, is(notNullValue()));

		json = actual;
	}

	@Test
	public void parseMeCall() throws JSONException
	{
		Contact actual = Contact.fromJSON(json);
		assertThat("User could not be created", actual, is(notNullValue()));
		assertThat("Id not correctly parsed", actual.getId(), is("3382304"));
		assertThat("First Name not correctly parsed", actual.getFirstName(), is("Hakan"));
		assertThat("Last Name not correctly parsed", actual.getLastName(), is("Tandogan"));
		assertThat("Display Name not correctly parsed", actual.getDisplayName(), is("Hakan Tandogan"));
	}

	@Test
	public void data() throws JSONException
	{
		Contact actual = Contact.fromJSON(json);
		assertThat("User could not be created", actual, is(notNullValue()));

		actual.setData(ContactsContract.CommonDataKinds.Email.ADDRESS, "someone@somewhere.com");
		assertThat("Email Address could not be set", actual.getData(ContactsContract.CommonDataKinds.Email.ADDRESS),
		        is(equalTo("someone@somewhere.com")));
	}
}
