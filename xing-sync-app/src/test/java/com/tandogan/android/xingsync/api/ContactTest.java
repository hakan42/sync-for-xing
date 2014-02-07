package com.tandogan.android.xingsync.api;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
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
	public void populateJson() throws JSONException, IOException
	{
		// JSON is fetched from logcat and reformatted with
		// http://jsonformatter.curiousconcept.com/

		String data = IOUtils.toString(getClass().getResourceAsStream("ContactTest.json"), Charsets.UTF_8);

		JSONObject actual = new JSONObject(data);
		assertThat("JSON object could not be created", actual, notNullValue());

		json = actual;
	}

	@Test
	public void parseMeCall() throws JSONException
	{
		Contact actual = Contact.fromJSON(json);
		assertThat("User could not be created", actual, notNullValue());
		assertThat("Id not correctly parsed", actual.getId(), equalTo("3382304"));
		assertThat("First Name not correctly parsed", actual.getFirstName(), equalTo("Hakan"));
		assertThat("Last Name not correctly parsed", actual.getLastName(), equalTo("Tandogan"));
		assertThat("Display Name not correctly parsed", actual.getDisplayName(), equalTo("Hakan Tandogan"));
		assertThat("Gender not correctly parsed", actual.getGender(), equalTo("m"));
		assertThat("Permalink not correctly parsed", actual.getPermalink(),
		        equalTo("https://www.xing.com/profile/Hakan_Tandogan"));

		assertThat("Birthdate not correctly parsed", actual.getBirthdate(), notNullValue());
		assertThat("Birthdate not correctly parsed", actual.getBirthdate().getDay(), equalTo(5));
		assertThat("Birthdate not correctly parsed", actual.getBirthdate().getMonth(), equalTo(12));
		assertThat("Birthdate not correctly parsed", actual.getBirthdate().getYear(), equalTo(1979));

		assertThat("Phone numbers not correctly parsed", actual.getPhone(), notNullValue());

		assertThat("Private phone numbers not correctly parsed",
		        actual.getPhone(ContactsContract.CommonDataKinds.Phone.TYPE_HOME), nullValue());
		assertThat("Private phone numbers not correctly parsed",
		        actual.getPhone(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE), nullValue());
		assertThat("Private phone numbers not correctly parsed",
		        actual.getPhone(ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME), nullValue());

		assertThat("Business phone numbers not correctly parsed",
		        actual.getPhone(ContactsContract.CommonDataKinds.Phone.TYPE_WORK), equalTo("49|40|56821147"));
		assertThat("Business phone numbers not correctly parsed",
		        actual.getPhone(ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE), equalTo("49|174|1234567"));
		assertThat("Business phone numbers not correctly parsed",
		        actual.getPhone(ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK), equalTo("49|40|56821148"));
	}

	@Test
	public void data() throws JSONException
	{
		Contact actual = Contact.fromJSON(json);
		assertThat("User could not be created", actual, notNullValue());

		actual.setData(ContactsContract.CommonDataKinds.Email.ADDRESS, "someone@somewhere.com");
		assertThat("Email Address could not be set", actual.getData(ContactsContract.CommonDataKinds.Email.ADDRESS),
		        (equalTo("someone@somewhere.com")));
	}
}
