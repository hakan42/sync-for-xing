package com.tandogan.android.xingsync.api;

import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.ContactsContract;

public class Contact
{
	private String id;

	public String MIME_TYPE = ContactsContract.Contacts.CONTENT_TYPE;

	private String firstName;

	private String lastName;

	private String displayName;

	private String gender;

	private String permalink;

	private Birthdate birthdate;

	private Map<String, String> data = new TreeMap<String, String>();

	private Map<String, String> phone = new TreeMap<String, String>();

	private Address privateAddress = new Address();

	private Address businessAddress = new Address();

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

			if (json.has("gender"))
			{
				u.setGender(json.getString("gender"));
			}

			if (json.has("permalink"))
			{
				u.setPermalink(json.getString("permalink"));
			}

			if (json.has("birth_date"))
			{
				JSONObject bd = json.getJSONObject("birth_date");
				if (bd != null)
				{
					Birthdate birthdate = new Birthdate();

					birthdate.setDay(bd.optInt("day"));
					birthdate.setMonth(bd.optInt("month"));
					birthdate.setYear(bd.optInt("year"));

					u.setBirthdate(birthdate);
				}
			}

			if (json.has("private_address"))
			{
				JSONObject address = json.getJSONObject("private_address");
				if (address != null)
				{
					if (!(address.isNull("phone")))
					{
						u.setPhone(ContactsContract.CommonDataKinds.Phone.TYPE_HOME, address.optString("phone"));
					}

					if (!(address.isNull("mobile_phone")))
					{
						u.setPhone(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE, address.optString("mobile_phone"));
					}

					if (!(address.isNull("fax")))
					{
						u.setPhone(ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME, address.optString("fax"));
					}

					Address a = u.getPrivateAddress();
					a.setType(ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME);

					if (!(address.isNull("street")))
					{
						a.setStreet(address.optString("street"));
					}

					if (!(address.isNull("zip_code")))
					{
						a.setZip(address.optString("zip_code"));
					}

					if (!(address.isNull("city")))
					{
						a.setCity(address.optString("city"));
					}

					if (!(address.isNull("province")))
					{
						a.setProvince(address.optString("province"));
					}

					if (!(address.isNull("country")))
					{
						a.setCountry(address.optString("country"));
					}
				}
			}

			if (json.has("business_address"))
			{
				JSONObject address = json.getJSONObject("business_address");
				if (address != null)
				{
					if (!(address.isNull("phone")))
					{
						u.setPhone(ContactsContract.CommonDataKinds.Phone.TYPE_WORK, address.optString("phone"));
					}

					if (!(address.isNull("mobile_phone")))
					{
						u.setPhone(ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE, address.optString("mobile_phone"));
					}

					if (!(address.isNull("fax")))
					{
						u.setPhone(ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK, address.optString("fax"));
					}

					Address a = u.getBusinessAddress();
					a.setType(ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK);

					if (!(address.isNull("street")))
					{
						a.setStreet(address.optString("street"));
					}

					if (!(address.isNull("zip_code")))
					{
						a.setZip(address.optString("zip_code"));
					}

					if (!(address.isNull("city")))
					{
						a.setCity(address.optString("city"));
					}

					if (!(address.isNull("province")))
					{
						a.setProvince(address.optString("province"));
					}

					if (!(address.isNull("country")))
					{
						a.setCountry(address.optString("country"));
					}
				}
			}
		}

		return u;
	}

	public Birthdate getBirthdate()
	{
		return birthdate;
	}

	public Address getBusinessAddress()
	{
		return businessAddress;
	}

	public Map<String, String> getData()
	{
		return data;
	}

	public String getData(String key)
	{
		if (data == null)
		{
			return null;
		}

		return data.get(key);
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getGender()
	{
		return gender;
	}

	public String getId()
	{
		return id;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getPermalink()
	{
		return permalink;
	}

	public Map<String, String> getPhone()
	{
		return phone;
	}

	public String getPhone(int type)
	{
		return phone.get(Integer.toString(type));
	}

	public Address getPrivateAddress()
	{
		return privateAddress;
	}

	public void setBirthdate(Birthdate birthdate)
	{
		this.birthdate = birthdate;
	}

	public void setBusinessAddress(Address businessAddress)
	{
		this.businessAddress = businessAddress;
	}

	public void setData(Map<String, String> data)
	{
		this.data = data;
	}

	public void setData(String key, String value)
	{
		if (data == null)
		{
			setData(new TreeMap<String, String>());
		}

		data.put(key, value);
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setPermalink(String permalink)
	{
		this.permalink = permalink;
	}

	public void setPhone(int type, String phone)
	{
		if (this.phone == null)
		{
			this.phone = new TreeMap<String, String>();
		}

		this.phone.put(Integer.toString(type), phone);
	}

	public void setPhone(Map<String, String> phone)
	{
		this.phone = phone;
	}

	public void setPrivateAddress(Address privateAddress)
	{
		this.privateAddress = privateAddress;
	}
}
