package com.tandogan.android.xingsync.api;

public class Address
{
	// "street":"Reeperbahn 1",
	// "zip_code":"20115",
	// "city":"Hamburg",
	// "province":"Hamburg",
	// "country":"DE",

	private Integer type;

	private String street;

	private String zip;

	private String city;

	private String province;

	private String country;

	public String getCity()
	{
		return city;
	}

	public String getCountry()
	{
		return country;
	}

	public String getProvince()
	{
		return province;
	}

	public String getStreet()
	{
		return street;
	}

	public Integer getType()
	{
		return type;
	}

	public String getZip()
	{
		return zip;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}

	public void setZip(String zip)
	{
		this.zip = zip;
	}

}
