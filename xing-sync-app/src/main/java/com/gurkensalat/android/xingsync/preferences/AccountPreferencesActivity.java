package com.gurkensalat.android.xingsync.preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.gurkensalat.android.xingsync.R;

@EActivity
public class AccountPreferencesActivity extends PreferenceActivity
{
	private static Logger LOG = LoggerFactory.getLogger(AccountPreferencesActivity.class);

	@Pref
	SyncPrefs_ syncPrefs;

	@Override
	public void onCreate(Bundle icicle)
	{
		LOG.info("onCreate() called");
		super.onCreate(icicle);
		addPreferencesFromResource(R.xml.preferences_resources);

		// TODO replease with PreferencesFragment code
		// http://developer.android.com/reference/android/preference/PreferenceActivity.html
		// http://developer.android.com/reference/android/preference/PreferenceFragment.html
	}

	@Override
	protected void onStart()
	{
		LOG.info("onStart() called");

		super.onStart();
		// preferences.registerOnSharedPreferenceChangeListener(backupListener);
	}

	@Override
	protected void onStop()
	{
		LOG.info("onStop() called");

		// preferences.unregisterOnSharedPreferenceChangeListener(backupListener);
		super.onStop();
		savePreferencesToAnnotation();
		createFieldList();
	}

	private void savePreferencesToAnnotation()
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		LOG.info("prefs is {}", prefs.toString());

		// TODO make debugMockApiCalls Boolean.FALSE before beta deployment
		boolean debugMockApiCalls = prefs.getBoolean(getResources().getString(R.string.prefs_debug_mock_api_calls_key),
		        Boolean.TRUE);
		syncPrefs.edit().debugMockApiCalls().put(debugMockApiCalls).apply();

		boolean fetchEmploymentStatus = prefs.getBoolean(
		        getResources().getString(R.string.prefs_advanced_fetch_employment_status_key), Boolean.TRUE);
		syncPrefs.edit().fetchEmploymentStatus().put(fetchEmploymentStatus).apply();

		boolean fetchGender = prefs.getBoolean(getResources().getString(R.string.prefs_advanced_fetch_gender_key), Boolean.TRUE);
		syncPrefs.edit().fetchGender().put(fetchGender).apply();

		boolean fetchBirthDate = prefs.getBoolean(getResources().getString(R.string.prefs_advanced_fetch_birth_date_key),
		        Boolean.TRUE);
		syncPrefs.edit().fetchBirthDate().put(fetchBirthDate).apply();

		boolean fetchPrivateAddress = prefs.getBoolean(getResources()
		        .getString(R.string.prefs_advanced_fetch_private_address_key), Boolean.TRUE);
		syncPrefs.edit().fetchPrivateAddress().put(fetchPrivateAddress).apply();

		boolean fetchBusinessAddress = prefs.getBoolean(
		        getResources().getString(R.string.prefs_advanced_fetch_business_address_key), Boolean.TRUE);
		syncPrefs.edit().fetchBusinessAddress().put(fetchBusinessAddress).apply();

		boolean fetchWebProfiles = prefs.getBoolean(getResources().getString(R.string.prefs_advanced_fetch_web_profiles_key),
		        Boolean.TRUE);
		syncPrefs.edit().fetchWebProfiles().put(fetchWebProfiles).apply();

		boolean fetchProfessionalExperience = prefs.getBoolean(
		        getResources().getString(R.string.prefs_advanced_fetch_professional_experience_key), Boolean.TRUE);
		syncPrefs.edit().fetchProfessionalExperience().put(fetchProfessionalExperience).apply();

		boolean fetchPrimaryCompany = prefs.getBoolean(getResources()
		        .getString(R.string.prefs_advanced_fetch_primary_company_key), Boolean.TRUE);
		syncPrefs.edit().fetchPrimaryCompany().put(fetchPrimaryCompany).apply();

		boolean fetchPermalink = prefs.getBoolean(getResources().getString(R.string.prefs_advanced_fetch_permalink_key),
		        Boolean.TRUE);
		syncPrefs.edit().fetchPermalink().put(fetchPermalink).apply();
	}

	public void createFieldList()
	{
		LOG.info("createFieldList() called, prefs is {}", syncPrefs);

		// syncPrefs must be a valid object.
		if (syncPrefs != null)
		{
			StringBuilder fieldList = new StringBuilder();

			fieldList.append("id");
			fieldList.append(",").append("first_name");
			fieldList.append(",").append("last_name");
			fieldList.append(",").append("display_name");

			if (syncPrefs.fetchEmploymentStatus().get())
			{
				fieldList.append(",").append("employment_status");
			}

			if (syncPrefs.fetchGender().get())
			{
				fieldList.append(",").append("gender");
			}

			if (syncPrefs.fetchBirthDate().get())
			{
				fieldList.append(",").append("birth_date");
			}

			if (syncPrefs.fetchPrivateAddress().get())
			{
				fieldList.append(",").append("private_address");
			}

			if (syncPrefs.fetchBusinessAddress().get())
			{
				fieldList.append(",").append("business_address");
			}

			if (syncPrefs.fetchWebProfiles().get())
			{
				fieldList.append(",").append("web_profiles");
			}

			if (syncPrefs.fetchProfessionalExperience().get())
			{
				fieldList.append(",").append("professional_experience");
			}

			// if (syncPrefs.fetchPrimaryCompany().get())
			// {
			// fieldList.append(",").append("birth_date");
			// }

			if (syncPrefs.fetchPermalink().get())
			{
				fieldList.append(",").append("permalink");
			}

			syncPrefs.edit().fieldsToFetch().put("Banane");
			// fieldList.toString());
		}
	}
}