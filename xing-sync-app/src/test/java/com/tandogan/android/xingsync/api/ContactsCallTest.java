package com.tandogan.android.xingsync.api;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
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
	public void populateJson() throws JSONException, IOException
	{
		// JSON is fetched from logcat and reformatted with
		// http://jsonformatter.curiousconcept.com/

		String data = IOUtils.toString(getClass().getResourceAsStream("ContactsCallTest.json"), Charsets.UTF_8);

		JSONObject actual = new JSONObject(data);
		assertThat("JSON object could not be created", actual, is(notNullValue()));

		json = actual;
	}

	@Test
	public void parseContactsCall() throws JSONException
	{
		List<User> actual = testable.parse(json);

		assertThat("Users list could not be created", actual, is(notNullValue()));
		assertThat("Two users would have to be parsed", actual.size(), is(2));

		assertThat("Id #0 not correctly parsed", actual.get(0).getId(), is("3382304"));
		assertThat("First Name #0 not correctly parsed", actual.get(0).getFirstName(), is("Hakan"));
		assertThat("Last Name #0 not correctly parsed", actual.get(0).getLastName(), is("Tandogan"));

		assertThat("Id #1 not correctly parsed", actual.get(1).getId(), is("10244846"));
		assertThat("First Name #1 not correctly parsed", actual.get(1).getFirstName(), is("Frohwalt"));
		assertThat("Last Name #1 not correctly parsed", actual.get(1).getLastName(), is("Egerer"));
	}
}
