package com.tandogan.android.xingsync.preferences;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import com.tandogan.android.xingsync.sync.AddAccountActivity;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface SyncPrefs
{
	/**
	 * @see oauth.signpost.OAuth.OAUTH_TOKEN
	 */
	@DefaultString("")
	String oauth_token();

	/**
	 * @see oauth.signpost.OAuth.OAUTH_TOKEN_SECRET
	 */
	@DefaultString("")
	String oauth_token_secret();

	/**
	 * @see AddAccountActivity
	 */
	@DefaultString("")
	String sync_user();

	/**
	 * @see AddAccountActivity
	 */
	@DefaultString("lastfm_session_key")
	String session_key();

	/**
	 * TODO make debugMockApiCalls Boolean.FALSE before beta deployment
	 */
	@DefaultBoolean(true)
	boolean debugMockApiCalls();

	/**
	 * TODO make saveApiCallResults Boolean.FALSE before beta deployment
	 */
	@DefaultBoolean(true)
	boolean saveApiCallResults();

	@DefaultString("id,first_name,last_name")
	String fieldsToFetch();

	@DefaultBoolean(false)
	boolean fetchEmploymentStatus();

	@DefaultBoolean(true)
	boolean fetchGender();

	@DefaultBoolean(false)
	boolean fetchBirthDate();

	@DefaultBoolean(false)
	boolean fetchPrivateAddress();

	@DefaultBoolean(false)
	boolean fetchBusinessAddress();

	@DefaultBoolean(false)
	boolean fetchWebProfiles();

	@DefaultBoolean(false)
	boolean fetchProfessionalExperience();

	@DefaultBoolean(false)
	boolean fetchPrimaryCompany();

	@DefaultBoolean(false)
	boolean fetchPermalink();
}
